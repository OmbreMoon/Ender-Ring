package com.ombremoon.enderring.common.init.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.item.DebugItem;
import com.ombremoon.enderring.common.object.item.RuneItem;
import com.ombremoon.enderring.common.object.item.TalismanItem;
import com.ombremoon.enderring.common.object.item.TorrentWhistleItem;
import com.ombremoon.enderring.common.object.item.magic.SpiritCallingBellItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
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
    public static final List<RegistryObject<Item>> GENERAL_LIST = new ArrayList<>();
    public static final List<RegistryObject<Item>> SIMPLE_ITEM_LIST = new ArrayList<>();
    public static final List<RegistryObject<Item>> HANDHELD_LIST = new ArrayList<>();
    public static final List<RegistryObject<Item>> TALISMAN_LIST = new ArrayList<>();
    public static final List<RegistryObject<Item>> EQUIPMENT_LIST = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static RegistryObject<Item> DEBUG = registerItem("debug", () -> new DebugItem(getItemProperties()), GENERAL_LIST);

    public static RegistryObject<Item> SPIRIT_CALLING_BELL = registerGeneralItem("spirit_calling_bell", () -> new SpiritCallingBellItem(getItemProperties()));
    public static RegistryObject<Item> TORRENT_WHISTLE = registerGeneralItem("spectral_steed_whistle", () -> new TorrentWhistleItem(getItemProperties()));

    //KEEPSAKES
    public static RegistryObject<Item> CRIMSON_AMBER_MEDALLION = registerTalisman("crimson_amber_medallion", StatusEffectInit.CRIMSON_AMBER_MEDALLION);
    public static RegistryObject<Item> BLOCKS_BETWEEN_RUNE = registerGeneralItem("blocks_between_rune", () -> new RuneItem(getItemProperties().stacksTo(1), 3000));
    public static RegistryObject<Item> GOLDEN_SEED;
    public static RegistryObject<Item> CRACKED_POT = registerSimpleItem("cracked_pot", getItemProperties().stacksTo(20));
    public static RegistryObject<Item> STONESWORD_KEY = registerSimpleItem("stonesword_key");
    public static RegistryObject<Item> BEWITCHING_BRANCH;
    public static RegistryObject<Item> RAW_PRAWN = registerSimpleItem("raw_prawn", getItemProperties().food(new FoodProperties.Builder().build()));
    public static RegistryObject<Item> BOILED_PRAWN = registerSimpleItem("boiled_prawn", getItemProperties().food(new FoodProperties.Builder().alwaysEat().effect(() -> new MobEffectInstance(StatusEffectInit.PHYSICAL_DAMAGE_NEGATION.get(), 1200), 1.0F).build()));
    public static RegistryObject<Item> SHABRIRIS_WOE = registerTalisman("shabriris_woe", StatusEffectInit.SHABRIRI_WOE);

    public static final RegistryObject<CreativeModeTab> TAB = registerCreativeModeTab(Constants.MOD_ID, ItemInit.DEBUG, GENERAL_LIST);
    public static final RegistryObject<CreativeModeTab> TALISMAN = registerCreativeModeTab("talismans", ItemInit.CRIMSON_AMBER_MEDALLION, TALISMAN_LIST);

    public static RegistryObject<Item> registerTalisman(String name, Supplier<MobEffect> mobEffect) {
        return registerTalisman(name, mobEffect, 0);
    }

    public static RegistryObject<Item> registerTalisman(String name, Supplier<MobEffect> mobEffect, int amplifier) {
        RegistryObject<Item> registryObject = ITEMS.register(name, () -> new TalismanItem(() -> new MobEffectInstance(mobEffect.get(), -1, amplifier), getItemProperties()));
        TALISMAN_LIST.add(registryObject);
        return registryObject;
    }

    protected static RegistryObject<Item> registerSimpleItem(String name) {
        return registerSimpleItem(name, getItemProperties());
    }

    protected static RegistryObject<Item> registerSimpleItem(String name, Item.Properties properties) {
        var registryObject = ITEMS.register(name, () -> new Item(properties));
        SIMPLE_ITEM_LIST.add(registryObject);
        GENERAL_LIST.add(registryObject);
        return registryObject;
    }

    protected static RegistryObject<Item> registerGeneralItem(String name, Supplier<Item> item) {
        return registerItem(name, item, GENERAL_LIST);
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
        EquipmentInit.init();
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
