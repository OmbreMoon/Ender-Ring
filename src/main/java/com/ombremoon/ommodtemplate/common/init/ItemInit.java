package com.ombremoon.ommodtemplate.common.init;

import com.ombremoon.ommodtemplate.Constants;
import com.ombremoon.ommodtemplate.common.object.item.DebugItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ItemInit {
    public static final List<RegistryObject<Item>> SIMPLE_ITEM_LIST = new ArrayList<>();
    public static final List<RegistryObject<Item>> HANDHELD_LIST = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static RegistryObject<Item> DEBUG = registerItem("debug", () -> new DebugItem(getItemProperties()));

    protected static RegistryObject<Item> registerSimpleItem(String name) {
        return registerSimpleItem(name, getItemProperties());
    }

    protected static RegistryObject<Item> registerSimpleItem(String name, Item.Properties properties) {
        RegistryObject<Item> registryObject = ITEMS.register(name, () -> new Item(properties));
        SIMPLE_ITEM_LIST.add(registryObject);
        return registryObject;
    }
    protected static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item, Collection<RegistryObject<T>> collection) {
        var registryObject = registerItem(name, item);
        collection.add(registryObject);
        return registryObject;
    }

    protected static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    protected static RegistryObject<CreativeModeTab> registerCreativeModeTab(String name, RegistryObject<Item> item, List<RegistryObject<Item>> registryObjects) {
        return registerCreativeModeTab(name, item, registryObjects, resourceLocation -> true);
    }

    protected static RegistryObject<CreativeModeTab> registerCreativeModeTab(String name, RegistryObject<Item> item, List<RegistryObject<Item>> registryObjects, Predicate<Item> itemPredicate) {
        return CREATIVE_MODE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(item.get()))
                .title(Component.translatable("itemGroup." + name + ".tab"))
                .displayItems(
                        (itemDisplayParameters, output) -> {
                            registryObjects.forEach((registryObject) -> {
                                if (itemPredicate.test(registryObject.get())) {
                                    output.accept(new ItemStack(registryObject.get()));
                                }
                            });
                        })
                .build());
    }

    public static Item.Properties getItemProperties() {
        return new Item.Properties();
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
