package com.ombremoon.enderring.datagen;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.item.equipment.ModdedArmor;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.function.Supplier;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemInit.GENERAL_LIST.stream().map(RegistryObject::get).filter(item -> !Arrays.stream(EXCLUSION_LIST).toList().contains(item)).forEach(this::simpleGeneratedModel);
        ItemInit.EQUIPMENT_LIST.stream().map(RegistryObject::get).filter(item -> !(item instanceof ModdedArmor && ((ModdedArmor) item).getEquipmentSlot() == EquipmentSlot.HEAD)).forEach(this::tempItem);
        ItemInit.TALISMAN_LIST.stream().map(RegistryObject::get).forEach(this::simpleGeneratedModel);
        Arrays.stream(EXCLUSION_LIST).toList().forEach(this::tempItem);

    }

    protected ItemModelBuilder simpleGeneratedModel(Item item) {
        return simpleModel(item, mcLoc("item/generated"));
    }

    protected ItemModelBuilder simpleHandHeldModel(Item item) {
        return simpleModel(item, mcLoc("item/handheld"));
    }

    protected ItemModelBuilder simpleModel(Item item, ResourceLocation parent) {
        String name = getName(item);
        return singleTexture(name, parent, "layer0", modLoc("item/" + name));
    }

    protected ItemModelBuilder tempItem(Item item) {
        return withExistingParent(ForgeRegistries.ITEMS.getKey(item).getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                CommonClass.customLocation("item/" + "temp_texture"));
    }

    protected String getName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    protected String getName(Block item) {
        return ForgeRegistries.BLOCKS.getKey(item).getPath();
    }

    private final Item[] EXCLUSION_LIST = {
            ItemInit.DEBUG.get(),
            ItemInit.SPIRIT_CALLING_BELL.get(),
            ItemInit.TORRENT_WHISTLE.get()
    };

}
