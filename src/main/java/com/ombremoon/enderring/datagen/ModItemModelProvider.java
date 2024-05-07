package com.ombremoon.enderring.datagen;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.item.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class ModItemModelProvider extends ItemModelProvider {
    private final Predicate<Item> exclusionPredicate = item -> !Arrays.stream(EXCLUSION_LIST).toList().contains(item);
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        ItemInit.GENERAL_LIST.stream().map(RegistryObject::get).filter(exclusionPredicate).forEach(this::simpleGeneratedModel);
        registerItemModels(ItemInit.GENERAL_LIST);
        registerItemModels(ItemInit.TALISMAN_LIST);
        ItemInit.EQUIPMENT_LIST.stream().map(RegistryObject::get).forEach(this::tempItem);
//        ItemInit.TALISMAN_LIST.stream().map(RegistryObject::get).filter(exclusionPredicate).forEach(this::simpleGeneratedModel);
        Arrays.stream(EXCLUSION_LIST).toList().forEach(this::tempItem);

    }

    private void registerItemModels(Collection<RegistryObject<Item>> registryObjects) {
        registryObjects.stream().map(RegistryObject::get).filter(exclusionPredicate).forEach(this::simpleGeneratedModel);
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

    static final Item[] EXCLUSION_LIST = {
            ItemInit.DEBUG.get(),
            ItemInit.SPIRIT_CALLING_BELL.get(),
            ItemInit.TORRENT_WHISTLE.get(),
            ItemInit.SACRED_TEAR.get(),
            ItemInit.CERULEAN_FLASK.get(),
            ItemInit.WONDROUS_PHYSICK_FLASK.get(),
            ItemInit.TALISMAN_POUCH.get(),
            ItemInit.CRIMSON_CRYSTAL.get(),
            ItemInit.CERULEAN_CRYSTAL.get(),
            ItemInit.CERULEAN_HIDDEN.get(),
            ItemInit.BLOCKS_BETWEEN_RUNE.get(),
            ItemInit.BEWITCHING_BRANCH.get(),
            ItemInit.LAND_OCTOPUS_OVARY.get(),
            ItemInit.WHITE_FLESH_STRIP.get(),
            ItemInit.GOLDEN_ROWA.get(),
            ItemInit.INVIGORATING_CURED_MEAT.get(),
            ItemInit.INVIGORATING_CURED_WHITE_MEAT.get(),
            ItemInit.CLAY_POT.get(),
            ItemInit.HARDENED_POT.get(),
            ItemInit.HOLY_WATER.get(),
            ItemInit.ROPED_HOLY_WATER.get(),
            ItemInit.MISSIONARY_COOKBOOK_ONE.get(),
            ItemInit.NOMADIC_WARRIOR_COOKBOOK_ONE.get(),
            ItemInit.NOMADIC_WARRIOR_COOKBOOK_TWO.get(),
            ItemInit.GREEN_TURTLE_TALISMAN.get(),
            ItemInit.GODFREY_ICON.get(),
            ItemInit.RADAGONS_SORESEAL.get(),
            ItemInit.SACRIFICIAL_TWIG.get()
    };

}

