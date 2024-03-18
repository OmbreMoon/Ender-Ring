package com.ombremoon.ommodtemplate.common.init;

import com.ombremoon.ommodtemplate.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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



    private static RegistryObject<Block> registerBlock(String name, Supplier<Block> supplier) {
        return registerBlock(name, supplier, true);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, boolean addToList) {
        var toReturn = BLOCKS.register(name, block);
        if (addToList)
            BLOCK_LIST.add(toReturn);

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
