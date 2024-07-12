package com.ombremoon.enderring.datagen.custom;

import com.google.gson.JsonObject;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.data.ArmorResistance;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class ArmorResistanceDataProvider implements DataProvider {
    protected final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> completableFuture;
    private final Map<ResourceLocation, ArmorResistance> armorMap = new HashMap<>();

    protected ArmorResistanceDataProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "armor_resistances");
        this.completableFuture = completableFuture;
    }

    protected abstract void registerArmors();

    //TODO: ADD EXCEPTION IF RESOURCE LOCATION DOES NOT LEAD TO ITEM
    protected void addArmor(ResourceLocation resourceLocation, ArmorResistance armorResistance) {
        this.armorMap.put(resourceLocation, armorResistance);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        return this.completableFuture.thenCompose(provider -> {
            this.armorMap.clear();
            this.registerArmors();
            return CompletableFuture.allOf(this.armorMap.entrySet().stream().map(entry -> {
                ResourceLocation location = entry.getKey();
                ArmorResistance weapon = entry.getValue();
                Path path = this.pathProvider.json(location);
                JsonObject jsonObject = weapon.toJsonObject();
                return DataProvider.saveStable(pOutput, jsonObject, path);
            }).toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return "Armor Resistances: " + Constants.MOD_ID;
    }
}
