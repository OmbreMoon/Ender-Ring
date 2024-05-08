package com.ombremoon.enderring.common.init;

import com.ombremoon.enderring.CommonClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagInit {
    public static class Items {
        public static final TagKey<Item> CRYSTAL_TEAR = tag("crystal_tear");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(CommonClass.customLocation(name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

    public static class Blocks {

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(CommonClass.customLocation(name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }
}