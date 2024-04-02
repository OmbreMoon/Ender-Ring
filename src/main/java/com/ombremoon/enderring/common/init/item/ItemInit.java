package com.ombremoon.enderring.common.init.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.item.*;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
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
import java.util.function.BiConsumer;
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

    public static RegistryObject<Item> DEBUG = registerItem("debug", () -> new DebugItem(getItemProperties()));

    public static RegistryObject<Item> SPIRIT_CALLING_BELL = registerGeneralItem("spirit_calling_bell", () -> new SpiritCallingBellItem(getItemProperties()));
    public static RegistryObject<Item> TORRENT_WHISTLE = registerGeneralItem("spectral_steed_whistle", () -> new TorrentWhistleItem(getItemProperties()));

    //KEEPSAKES
    public static RegistryObject<Item> CRIMSON_AMBER_MEDALLION = registerTalisman("crimson_amber_medallion", StatusEffectInit.CRIMSON_AMBER_MEDALLION);
    public static RegistryObject<Item> BLOCKS_BETWEEN_RUNE = registerGeneralItem("blocks_between_rune", () -> new RuneItem(getItemProperties().stacksTo(1), 3000));
    public static RegistryObject<Item> GOLDEN_SEED = registerSimpleItem("golden_seed", getItemProperties().stacksTo(16));
    public static RegistryObject<Item> CRACKED_POT = registerSimpleItem("cracked_pot", getItemProperties().stacksTo(20));
    public static RegistryObject<Item> STONESWORD_KEY = registerSimpleItem("stonesword_key");
    public static RegistryObject<Item> BEWITCHING_BRANCH = registerGeneralItem("bewitching_branch", () -> new BewitchingBranchItem(getItemProperties().stacksTo(16)));
    public static RegistryObject<Item> RAW_PRAWN = registerSimpleItem("raw_prawn", getItemProperties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build()));
    public static RegistryObject<Item> BOILED_PRAWN = registerSimpleItem("boiled_prawn", getItemProperties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).alwaysEat().effect(() -> new MobEffectInstance(StatusEffectInit.PHYSICAL_DAMAGE_NEGATION.get(), 1200), 1.0F).build()));
    public static RegistryObject<Item> SHABRIRIS_WOE = registerTalisman("shabriris_woe", StatusEffectInit.SHABRIRIS_WOE);

    public static final RegistryObject<CreativeModeTab> TAB = registerCreativeModeTab(Constants.MOD_ID, ItemInit.DEBUG, GENERAL_LIST);
    public static final RegistryObject<CreativeModeTab> TALISMAN = registerCreativeModeTab("talismans", ItemInit.CRIMSON_AMBER_MEDALLION, TALISMAN_LIST);
    public static final RegistryObject<CreativeModeTab> WEAPON = registerCreativeModeTab("equipment", ItemInit.DEBUG, EQUIPMENT_LIST, item -> item instanceof AbstractWeapon, ((item, output) -> registerScaledWeapons(output, item)));

    public static RegistryObject<Item> registerTalisman(String name, Supplier<MobEffect> mobEffect) {
        return registerTalisman(name, mobEffect, 0);
    }

    public static RegistryObject<Item> registerTalisman(String name, Supplier<MobEffect> mobEffect, int amplifier) {
        RegistryObject<Item> registryObject = registerItem(name, () -> new TalismanItem(() -> new MobEffectInstance(mobEffect.get(), -1, amplifier), getItemProperties()), TALISMAN_LIST, SIMPLE_ITEM_LIST);
        return registryObject;
    }

    protected static RegistryObject<Item> registerSimpleItem(String name) {
        return registerSimpleItem(name, getItemProperties());
    }

    protected static RegistryObject<Item> registerSimpleItem(String name, Item.Properties properties) {
        return registerItem(name, () -> new Item(properties), GENERAL_LIST, SIMPLE_ITEM_LIST);
    }

    protected static RegistryObject<Item> registerGeneralItem(String name, Supplier<Item> item) {
        return registerItem(name, item, GENERAL_LIST, SIMPLE_ITEM_LIST);
    }

    protected static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item, Collection<RegistryObject<T>> tabList, Collection<RegistryObject<T>> modelList) {
        var registryObject = registerItem(name, item);
        tabList.add(registryObject);
        modelList.add(registryObject);
        return registryObject;
    }

    protected static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    protected static RegistryObject<CreativeModeTab> registerCreativeModeTab(String name, RegistryObject<Item> item, List<RegistryObject<Item>> registryObjects) {
        return registerCreativeModeTab(name, item, registryObjects, item1 -> false, (item1, output) -> {});
    }

    protected static RegistryObject<CreativeModeTab> registerCreativeModeTab(String name, RegistryObject<Item> item, List<RegistryObject<Item>> registryObjects, Predicate<Item> itemPredicate, BiConsumer<Item, CreativeModeTab.Output> itemConsumer) {
        return CREATIVE_MODE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(item.get()))
                .title(Component.translatable("itemGroup." + name + ".tab"))
                .displayItems(
                        (itemDisplayParameters, output) -> {
                            registryObjects.forEach((registryObject) -> {
                                if (itemPredicate.test(registryObject.get())) {
                                    itemConsumer.accept(registryObject.get(), output);
                                    return;
                                }
                                output.accept(new ItemStack(registryObject.get()));
                            });
                        })
                .build());
    }

    protected static void registerScaledWeapons(CreativeModeTab.Output output, Item item) {
        AbstractWeapon weapon = (AbstractWeapon) item;
        ItemStack itemStack = new ItemStack(weapon);
        itemStack.getOrCreateTag().put("Weapon", weapon.getWeapon().serializeNBT());
        output.accept(itemStack);
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
