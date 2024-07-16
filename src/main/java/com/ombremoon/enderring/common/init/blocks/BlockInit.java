package com.ombremoon.enderring.common.init.blocks;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.block.GoldenRowaBlock;
import com.ombremoon.enderring.common.object.block.GraceSiteBlock;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockInit {
    public static final List<RegistryObject<? extends Block>> BLOCK_LIST = new ArrayList<>();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    public static final RegistryObject<Block> GRACE_SITE = registerBlock("grace_site", () -> new GraceSiteBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).noOcclusion().lightLevel(blockstate -> {
        return 15;
    })), false);
    public static final RegistryObject<Block> TARNISHED_SUNFLOWER = registerBlock("tarnished_golden_sunflower", () -> new DoublePlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> GOLDEN_ROWA = registerBlock("golden_rowa_block", () -> new GoldenRowaBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> supplier) {
        return registerBlock(name, supplier, true, true);
    }

    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> supplier, boolean registerItem) {
        return registerBlock(name, supplier, true, registerItem);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, boolean addToList, boolean registerItem) {
        var toReturn = BLOCKS.register(name, block);
        if (addToList)
            BLOCK_LIST.add(toReturn);

        if (registerItem)
            registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    protected static RegistryObject<CreativeModeTab> registerCreativeModeTab(String name, RegistryObject<Block> block, List<RegistryObject<? extends Block>> registryObjects) {
        return ItemInit.CREATIVE_MODE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(block.get()))
                .title(Component.translatable("itemGroup." + name + ".tab"))
                .displayItems(
                        (itemDisplayParameters, output) -> {
                            registryObjects.forEach((registryObject) -> output.accept(new ItemStack(registryObject.get())));
                        })
                .build());
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
