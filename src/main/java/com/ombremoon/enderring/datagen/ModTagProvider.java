package com.ombremoon.enderring.datagen;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.TagInit;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.item.CrystalTearItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModTagProvider {

    public static class Items extends TagsProvider<Item> {

        public Items(PackOutput p_256596_, CompletableFuture<HolderLookup.Provider> p_256513_, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_256596_, Registries.ITEM, p_256513_, Constants.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            for (Item item : ForgeRegistries.ITEMS.getValues()) {
                if (item instanceof CrystalTearItem crystalTearItem) {
                    populateTag(TagInit.Items.CRYSTAL_TEAR, () -> crystalTearItem);
                }
            }
        }

        public void populateTag(TagKey<Item> tag, Supplier<Item>... items){
            for (Supplier<Item> item : items) {
                tag(tag).add(ForgeRegistries.ITEMS.getResourceKey(item.get()).get());
            }
        }
    }

    public static class Curios extends TagsProvider<Item> {
        public Curios(PackOutput p_256596_, CompletableFuture<HolderLookup.Provider> p_256513_, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_256596_, Registries.ITEM, p_256513_, "curios", existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            var talismans = tag(TagInit.Items.TALISMANS);
            for (RegistryObject<? extends Item> item : ItemInit.TALISMAN_LIST.keySet()) {
                populateTag(talismans, item.get());
            }

            var quickAccess = tag(TagInit.Items.QUICK_ACCESS);
            populateTag(quickAccess, EquipmentInit.CRIMSON_FLASK);
            populateTag(quickAccess, EquipmentInit.CERULEAN_FLASK);
            populateTag(quickAccess, EquipmentInit.TORRENT_WHISTLE);
            populateTag(quickAccess, EquipmentInit.WONDROUS_PHYSICK_FLASK);
            populateTag(quickAccess, EquipmentInit.SPIRIT_CALLING_BELL);
        }

        private void populateTag(TagAppender<Item> tag, RegistryObject<Item> item) {
            populateTag(tag, item.get());
        }

        private void populateTag(TagAppender<Item> tag, Item item) {
            tag.add(ForgeRegistries.ITEMS.getResourceKey(item).get());
        }
    }

    public static class Blocks extends TagsProvider<Block>{

        public Blocks(PackOutput pGenerator, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
            super(pGenerator, Registries.BLOCK, provider, Constants.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {

        }

        public  <T extends Block>void populateTag(TagKey<Block> tag, Supplier<?>... items){
            for (Supplier<?> item : items) {
                tag(tag).add(ForgeRegistries.BLOCKS.getResourceKey((Block)item.get()).get());
            }
        }
    }

/*    public static class ModPoiTypeTagsProvider extends PoiTypeTagsProvider {
        public ModPoiTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
            super(pOutput, pProvider, modId, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            tag(TagInit.PoiTypes.HEWG_ANVIL).addOptional(CommonClass.customLocation("hewg_anvil"));
        }
    }*/
}
