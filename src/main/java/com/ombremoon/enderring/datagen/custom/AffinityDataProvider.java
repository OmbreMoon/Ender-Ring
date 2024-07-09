package com.ombremoon.enderring.datagen.custom;

import com.google.gson.JsonObject;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AffinityDataProvider implements DataProvider {
    private final Map<ResourceLocation, ScaledWeapon> weaponMap = new HashMap<>();
    protected final PackOutput.PathProvider pathProvider;
    private final CompletableFuture<HolderLookup.Provider> completableFuture;

    protected AffinityDataProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, String folder) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "affinities/" + folder);
        this.completableFuture = completableFuture;
    }

    protected abstract void registerWeapons();

    protected void addWeapon(ResourceLocation resourceLocation, ScaledWeapon weapon) {
        if (this.weaponMap.containsKey(resourceLocation)) throw new IllegalArgumentException(String.format("Attempted to duplicate weapon: %s", resourceLocation));
        if (!ForgeRegistries.ITEMS.containsKey(resourceLocation)) throw new IllegalArgumentException(String.format("Attempted to register weapon for unknown item: %s", resourceLocation));
        this.weaponMap.put(resourceLocation, weapon);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        return this.completableFuture.thenCompose(provider -> {
            this.weaponMap.clear();
            this.registerWeapons();
            return CompletableFuture.allOf(this.weaponMap.entrySet().stream().map(entry -> {
                ResourceLocation location = entry.getKey();
                ScaledWeapon weapon = entry.getValue();
                Path path = this.pathProvider.json(location);
                JsonObject jsonObject = weapon.toJsonObject();
                return DataProvider.saveStable(pOutput, jsonObject, path);
            }).toArray(CompletableFuture[]::new));
        });
    }

    public abstract static class StandardDataProvider extends AffinityDataProvider {
        protected StandardDataProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(packOutput, completableFuture, "standard");
        }

        @Override
        public String getName() {
            return "Standard Affinities: " + Constants.MOD_ID;
        }
    }

    public abstract static class FireDataProvider extends AffinityDataProvider {
        protected FireDataProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(packOutput, completableFuture, "fire");
        }

        @Override
        public String getName() {
            return "Fire Affinities: " + Constants.MOD_ID;
        }
    }

}
