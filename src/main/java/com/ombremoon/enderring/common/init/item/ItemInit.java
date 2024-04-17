package com.ombremoon.enderring.common.init.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.item.*;
import com.ombremoon.enderring.common.object.item.equipment.FlaskItem;
import com.ombremoon.enderring.common.object.item.equipment.SpiritCallingBellItem;
import com.ombremoon.enderring.common.object.item.equipment.TorrentWhistleItem;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
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

    public static final RegistryObject<Item> DEBUG = registerItem("debug", () -> new DebugItem(getItemProperties()));

    public static final RegistryObject<Item> SPIRIT_CALLING_BELL = registerGeneralItem("spirit_calling_bell", () -> new SpiritCallingBellItem(getItemProperties()));
    public static final RegistryObject<Item> TORRENT_WHISTLE = registerGeneralItem("spectral_steed_whistle", () -> new TorrentWhistleItem(getItemProperties()));
    public static final RegistryObject<Item> CRIMSON_FLASK = registerGeneralItem("flask_of_crimson_tears", () -> new FlaskItem(FlaskItem.Type.HP, getItemProperties()));
    public static final RegistryObject<Item> CERULEAN_FLASK = registerGeneralItem("flask_of_cerulean_tears", () -> new FlaskItem(FlaskItem.Type.FP, getItemProperties()));
    public static final RegistryObject<Item> WONDROUS_PHYSICK_FLASK = registerGeneralItem("flask_of_wondrous_physick", () -> new FlaskItem(FlaskItem.Type.PHYSICK, getItemProperties()));
    public static final RegistryObject<Item> TALISMAN_POUCH = registerGeneralItem("talisman_pouch", () -> new Item(getItemProperties()) {
        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
            return increaseSlots(pLevel, pPlayer, pUsedHand, true);
        }
    });
    public static final RegistryObject<Item> MEMORY_STONE = registerGeneralItem("memory_stone", () -> new Item(getItemProperties()) {
        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
            return increaseSlots(pLevel, pPlayer, pUsedHand, false);
        }
    });

    //KEEPSAKES
    public static final RegistryObject<Item> CRIMSON_AMBER_MEDALLION = registerTalisman("crimson_amber_medallion", StatusEffectInit.CRIMSON_AMBER_MEDALLION);
    public static final RegistryObject<Item> BLOCKS_BETWEEN_RUNE = registerGeneralItem("blocks_between_rune", () -> new RuneItem(getItemProperties().stacksTo(1), 3000));
    public static final RegistryObject<Item> GOLDEN_SEED = registerSimpleItem("golden_seed", getItemProperties().stacksTo(16));
    public static final RegistryObject<Item> CRACKED_POT = registerSimpleItem("cracked_pot", getItemProperties().stacksTo(20));
    public static final RegistryObject<Item> STONESWORD_KEY = registerSimpleItem("stonesword_key");
    public static final RegistryObject<Item> BEWITCHING_BRANCH = registerGeneralItem("bewitching_branch", () -> new BewitchingBranchItem(getItemProperties().stacksTo(16)));
    public static final RegistryObject<Item> RAW_PRAWN = registerSimpleItem("raw_prawn", getItemProperties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build()));
    public static final RegistryObject<Item> BOILED_PRAWN = registerSimpleItem("boiled_prawn", getItemProperties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).alwaysEat().effect(() -> new MobEffectInstance(StatusEffectInit.PHYSICAL_DAMAGE_NEGATION.get(), 1200), 1.0F).build()));
    public static final RegistryObject<Item> SHABRIRIS_WOE = registerTalisman("shabriris_woe", StatusEffectInit.SHABRIRIS_WOE);

    //CONSUMABLES
    public static final RegistryObject<Item> HOLY_WATER = registerGeneralItem("holy_water", () -> new ThrowingPotItem(false, getItemProperties()));
    public static final RegistryObject<Item> ROPED_HOLY_WATER = registerGeneralItem("roped_holy_water", () -> new ThrowingPotItem(true, getItemProperties()));
    public static final RegistryObject<Item> INVIGORATING_CURED_MEAT = registerSimpleItem("invigorating_cured_meat", getItemProperties().food(new FoodProperties.Builder().alwaysEat().effect(() -> new MobEffectInstance(StatusEffectInit.INVIGORATING_MEAT.get(), 1200), 1.0F).build()));
    public static final RegistryObject<Item> INVIGORATING_CURED_WHITE_MEAT = registerSimpleItem("invigorating_cured_white_meat", getItemProperties().food(new FoodProperties.Builder().alwaysEat().effect(() -> new MobEffectInstance(StatusEffectInit.INVIGORATING_MEAT.get(), 2400, 1), 1.0F).build()));

    //COOKBOOKS
    public static final RegistryObject<Item> MISSIONARY_COOKBOOK_ONE = registerCookbook("missionary_cookbook_one");
    public static final RegistryObject<Item> NOMADIC_WARRIOR_COOKBOOK_ONE = registerCookbook("nomadic_warrior_cookbook_one");
    public static final RegistryObject<Item> NOMADIC_WARRIOR_COOKBOOK_TWO = registerCookbook("nomadic_warrior_cookbook_two");

    //CRAFTING MATERIALS
    public static final RegistryObject<Item> CRAB_EGGS = registerSimpleItem("crab_eggs");
    public static final RegistryObject<Item> LAND_OCTOPUS_OVARY = registerSimpleItem("land_octopus_ovary");
    public static final RegistryObject<Item> WHITE_FLESH_STRIP = registerSimpleItem("white_flesh_strip");

    //CRYSTAL TEARS
    public static final RegistryObject<Item> CRIMSON_CRYSTAL = registerCrystalTear("crimson_crystal_tear", StatusEffectInit.CRIMSON_CRYSTAL, 1);
    public static final RegistryObject<Item> CERULEAN_CRYSTAL = registerCrystalTear("cerulean_crystal_tear", StatusEffectInit.CERULEAN_CRYSTAL, 1);
    public static final RegistryObject<Item> OPALINE_BUBBLE = registerCrystalTear("opaline_bubble_tear", StatusEffectInit.OPALINE_BUBBLE, 3600);
    public static final RegistryObject<Item> CERULEAN_HIDDEN = registerCrystalTear("cerulean_hidden_tear", StatusEffectInit.CERULEAN_HIDDEN, 300);

    //MISC
    public static final RegistryObject<Item> CLAY_POT = registerSimpleItem("clay_pot");
    public static final RegistryObject<Item> HARDENED_POT = registerSimpleItem("hardened_pot");

    public static final RegistryObject<CreativeModeTab> TAB = registerCreativeModeTab(Constants.MOD_ID, ItemInit.DEBUG, GENERAL_LIST);
    public static final RegistryObject<CreativeModeTab> TALISMAN = registerCreativeModeTab("talismans", ItemInit.CRIMSON_AMBER_MEDALLION, TALISMAN_LIST);
    public static final RegistryObject<CreativeModeTab> WEAPON = registerCreativeModeTab("equipment", ItemInit.DEBUG, EQUIPMENT_LIST, item -> item instanceof AbstractWeapon, ((item, output) -> registerScaledWeapons(output, item)));

    public static RegistryObject<Item> registerCrystalTear(String name, Supplier<MobEffect> mobEffect, int duration) {
        return registerGeneralItem(name, () -> new CrystalTearItem(mobEffect, duration, getItemProperties()));
    }

    public static RegistryObject<Item> registerCookbook(String name) {
        return registerGeneralItem(name, () -> new CookbookItem(getItemProperties()));
    }

    public static RegistryObject<Item> registerTalisman(String name, Supplier<MobEffect> mobEffect) {
        return registerTalisman(name, mobEffect, 0);
    }

    public static RegistryObject<Item> registerTalisman(String name, Supplier<MobEffect> mobEffect, int amplifier) {
        return registerItem(name, () -> new TalismanItem(() -> new MobEffectInstance(mobEffect.get(), -1, amplifier), getItemProperties()), TALISMAN_LIST, SIMPLE_ITEM_LIST);
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
//        weapon.setWeapon(new ScaledWeaponManager.Wrapper(null));
        ItemStack itemStack = new ItemStack(weapon);
//        itemStack.getOrCreateTag().put("Weapon", weapon.getWeapon().serializeNBT());
        output.accept(itemStack);
    }

    private static InteractionResultHolder<ItemStack> increaseSlots(Level level, Player player, InteractionHand usedHand, boolean isTalisman) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide) {
            if (isTalisman) {
                PlayerStatusUtil.increaseTalismanPouches(player);
            } else {
                PlayerStatusUtil.increaseMemoryStones(player);
            }
        }

        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
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
