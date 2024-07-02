package com.ombremoon.enderring.common.init.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.item.*;
import com.ombremoon.enderring.common.object.item.equipment.FlaskItem;
import com.ombremoon.enderring.common.object.item.equipment.SpiritCallingBellItem;
import com.ombremoon.enderring.common.object.item.equipment.TorrentWhistleItem;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
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

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ItemInit {
    public static final List<RegistryObject<? extends Item>> GENERAL_LIST = new ArrayList<>();
    public static final List<RegistryObject<? extends Item>> SIMPLE_ITEM_LIST = new ArrayList<>();
    public static final List<RegistryObject<? extends Item>> HANDHELD_LIST = new ArrayList<>();
    public static final Map<RegistryObject<? extends Item>, Integer> TALISMAN_LIST = new HashMap<>();
    public static final List<RegistryObject<? extends Item>> EQUIPMENT_LIST = new ArrayList<>();
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final RegistryObject<Item> DEBUG = registerItem("debug", () -> new DebugItem(itemProperties()));

    public static final RegistryObject<Item> SPIRIT_CALLING_BELL = registerGeneralItem("spirit_calling_bell", () -> new SpiritCallingBellItem(itemProperties()));
    public static final RegistryObject<Item> TORRENT_WHISTLE = registerGeneralItem("spectral_steed_whistle", () -> new TorrentWhistleItem(itemProperties()));
    public static final RegistryObject<Item> CRIMSON_FLASK = registerGeneralItem("flask_of_crimson_tears", () -> new FlaskItem(FlaskItem.Type.HP, itemProperties()));
    public static final RegistryObject<Item> CERULEAN_FLASK = registerGeneralItem("flask_of_cerulean_tears", () -> new FlaskItem(FlaskItem.Type.FP, itemProperties()));
    public static final RegistryObject<Item> WONDROUS_PHYSICK_FLASK = registerGeneralItem("flask_of_wondrous_physick", () -> new FlaskItem(FlaskItem.Type.PHYSICK, itemProperties()));
    public static final RegistryObject<Item> TALISMAN_POUCH = registerGeneralItem("talisman_pouch", () -> new Item(itemProperties()) {
        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
            return increaseSlots(pLevel, pPlayer, pUsedHand, true);
        }
    });
    public static final RegistryObject<Item> MEMORY_STONE = registerGeneralItem("memory_stone", () -> new Item(itemProperties()) {
        @Override
        public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
            return increaseSlots(pLevel, pPlayer, pUsedHand, false);
        }
    });
    public static final RegistryObject<Item> SACRED_TEAR = registerSimpleItem("sacred_tear", itemProperties().stacksTo(16));


    //KEEPSAKES
    public static final RegistryObject<Item> CRIMSON_AMBER_MEDALLION = registerTalisman("crimson_amber_medallion", StatusEffectInit.CRIMSON_AMBER_MEDALLION, 2);
    public static final RegistryObject<Item> BLOCKS_BETWEEN_RUNE = registerGeneralItem("blocks_between_rune", () -> new RuneItem(itemProperties().stacksTo(1), 3000));
    public static final RegistryObject<Item> GOLDEN_SEED = registerSimpleItem("golden_seed", itemProperties().stacksTo(16));
    public static final RegistryObject<Item> CRACKED_POT = registerSimpleItem("cracked_pot", itemProperties().stacksTo(20));
    public static final RegistryObject<Item> STONESWORD_KEY = registerSimpleItem("stonesword_key");
    public static final RegistryObject<Item> BEWITCHING_BRANCH = registerGeneralItem("bewitching_branch", () -> new BewitchingBranchItem(itemProperties().stacksTo(16)));
    public static final RegistryObject<Item> RAW_PRAWN = registerSimpleItem("raw_prawn", itemProperties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build()));
    public static final RegistryObject<Item> BOILED_PRAWN = registerSimpleItem("boiled_prawn", itemProperties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.6F).alwaysEat().effect(() -> new MobEffectInstance(StatusEffectInit.PHYSICAL_DAMAGE_NEGATION.get(), 1200), 1.0F).build()));
    public static final RegistryObject<Item> SHABRIRIS_WOE = registerTalisman("shabriris_woe", StatusEffectInit.SHABRIRIS_WOE);

    //CONSUMABLES
    public static final RegistryObject<Item> HOLY_WATER = registerGeneralItem("holy_water", () -> new ThrowingPotItem(false, 5, itemProperties()));
    public static final RegistryObject<Item> ROPED_HOLY_WATER = registerGeneralItem("roped_holy_water", () -> new ThrowingPotItem(true, 5, itemProperties()));
    public static final RegistryObject<Item> INVIGORATING_CURED_MEAT = registerSimpleItem("invigorating_cured_meat", itemProperties().food(new FoodProperties.Builder().alwaysEat().effect(() -> new MobEffectInstance(StatusEffectInit.INVIGORATING_MEAT.get(), 1200), 1.0F).build()));
    public static final RegistryObject<Item> INVIGORATING_CURED_WHITE_MEAT = registerSimpleItem("invigorating_cured_white_meat", itemProperties().food(new FoodProperties.Builder().alwaysEat().effect(() -> new MobEffectInstance(StatusEffectInit.INVIGORATING_MEAT.get(), 2400, 1), 1.0F).build()));

    //COOKBOOKS
    public static final RegistryObject<Item> MISSIONARY_COOKBOOK_ONE = registerCookbook("missionary_cookbook_one");
    public static final RegistryObject<Item> NOMADIC_WARRIOR_COOKBOOK_ONE = registerCookbook("nomadic_warrior_cookbook_one");
    public static final RegistryObject<Item> NOMADIC_WARRIOR_COOKBOOK_TWO = registerCookbook("nomadic_warrior_cookbook_two");

    //CRAFTING MATERIALS
    public static final RegistryObject<Item> CRAB_EGGS = registerSimpleItem("crab_eggs");
    public static final RegistryObject<Item> LAND_OCTOPUS_OVARY = registerSimpleItem("land_octopus_ovary");
    public static final RegistryObject<Item> WHITE_FLESH_STRIP = registerSimpleItem("white_flesh_strip");
    public static final RegistryObject<Item> GOLDEN_ROWA = registerSimpleItem("golden_rowa");

    //CRYSTAL TEARS
    public static final RegistryObject<Item> CRIMSON_CRYSTAL = registerCrystalTear("crimson_crystal_tear", StatusEffectInit.CRIMSON_CRYSTAL, 1);
    public static final RegistryObject<Item> CERULEAN_CRYSTAL = registerCrystalTear("cerulean_crystal_tear", StatusEffectInit.CERULEAN_CRYSTAL, 1);
    public static final RegistryObject<Item> OPALINE_BUBBLE = registerCrystalTear("opaline_bubble_tear", StatusEffectInit.OPALINE_BUBBLE, 3600);
    public static final RegistryObject<Item> CERULEAN_HIDDEN = registerCrystalTear("cerulean_hidden_tear", StatusEffectInit.CERULEAN_HIDDEN, 300);

    //MISC
    public static final RegistryObject<Item> CLAY_POT = registerSimpleItem("clay_pot");
    public static final RegistryObject<Item> HARDENED_POT = registerSimpleItem("hardened_pot");

    //TALISMANS
    public static final RegistryObject<Item> GREEN_TURTLE_TALISMAN = registerTalisman("green_turtle_talisman", StatusEffectInit.GREEN_TURTLE);
    public static final RegistryObject<Item> GODFREY_ICON = registerTalisman("godfrey_icon", StatusEffectInit.GREEN_TURTLE);
    public static final RegistryObject<Item> RADAGONS_SCARSEAL = registerTalisman("radagons_scarseal", StatusEffectInit.RADAGONS_SCARSEAL, 1);
    public static final RegistryObject<Item> MARIKAS_SCARSEAL = registerTalisman("marikas_scarseal", StatusEffectInit.MARIKAS_SCARSEAL, 1);
    public static final RegistryObject<Item> SACRIFICIAL_TWIG = registerTalisman("sacrificial_twig", StatusEffectInit.SACRIFICIAL_TWIG);
    public static final RegistryObject<Item> DRAGONCREST_SHIELD_TALISMAN = registerTalisman("dragoncrest_shield_talisman", StatusEffectInit.DRAGONCREST_SHIELD_TALISMAN, 3);
    public static final RegistryObject<Item> CRIMSON_SEED_TALISMAN = registerTalisman("crimson_seed_talisman", StatusEffectInit.CRIMSON_SEED_TALISMAN);
    public static final RegistryObject<Item> CERULEAN_SEED_TALISMAN = registerTalisman("cerulean_seed_talisman", StatusEffectInit.CERULEAN_SEED_TALISMAN);
    public static final RegistryObject<Item> STARSCOURGE_HEIRLOOM = registerTalisman("starscourge_heirloom", StatusEffectInit.STARSCOURGE_HEIRLOOM);
    public static final RegistryObject<Item> PROSTHESIS_WEARER_HEIRLOOM = registerTalisman("prosthesis_wearer_heirloom", StatusEffectInit.PROSTHESIS_WEARER_HEIRLOOM);
    public static final RegistryObject<Item> STARGAZER_HEIRLOOM = registerTalisman("stargazer_heirloom", StatusEffectInit.STARGAZER_HEIRLOOM);
    public static final RegistryObject<Item> TWO_FINGERS_HEIRLOOM = registerTalisman("two_fingers_heirloom", StatusEffectInit.TWO_FINGERS_HEIRLOOM);
    public static final RegistryObject<Item> LONGTAIL_CAT_TALISMAN = registerTalisman("longtail_cat_talisman", StatusEffectInit.LONGTAIL_CAT_TALISMAN);
    public static final RegistryObject<Item> PRIMAL_GLINTSTONE_BLADE = registerTalisman("primal_glintstone_blade", StatusEffectInit.PRIMAL_GLINTSTONE_BLADE);
    public static final RegistryObject<Item> MAGIC_SCORPION_CHARM = registerTalisman("magic_scorpion_charm", StatusEffectInit.MAGIC_SCORPION_CHARM);
    public static final RegistryObject<Item> FIRE_SCORPION_CHARM = registerTalisman("fire_scorpion_charm", StatusEffectInit.FIRE_SCORPION_CHARM);
    public static final RegistryObject<Item> LIGHTNING_SCORPION_CHARM = registerTalisman("lightning_scorpion_charm", StatusEffectInit.LIGHTNING_SCORPION_CHARM);
    public static final RegistryObject<Item> SACRED_SCORPION_CHARM = registerTalisman("sacred_scorpion_charm", StatusEffectInit.SACRED_SCORPION_CHARM);
    public static final RegistryObject<Item> ERDTREES_FAVOR = registerTalisman("erdtrees_favor", StatusEffectInit.ERDTREES_FAVOR, 2);
    public static final RegistryObject<Item> RITUAL_SWORD_TALISMAN = registerTalisman("ritual_sword_talisman", StatusEffectInit.RITUAL_SWORD_TALISMAN);
    public static final RegistryObject<Item> RITUAL_SHIELD_TALISMAN = registerTalisman("ritual_shield_talisman", StatusEffectInit.RITUAL_SHIELD_TALISMAN);
    public static final RegistryObject<Item> RED_FEATHERED_BRANCHSWORD = registerTalisman("red_feathered_branchsword", StatusEffectInit.RED_FEATHERED_BRANCHSWORD);
    public static final RegistryObject<Item> BLUE_FEATHERED_BRANCHSWORD = registerTalisman("blue_feathered_branchsword", StatusEffectInit.BLUE_FEATHERED_BRANCHSWORD);
    public static final RegistryObject<Item> SPELLDRAKE_TALISMAN = registerTalisman("spelldrake_talisman", StatusEffectInit.SPELLDRAKE_TALISMAN,2);
    public static final RegistryObject<Item> FLAMEDRAKE_TALISMAN = registerTalisman("flamedrake_talisman", StatusEffectInit.FLAMEDRAKE_TALISMAN,2);
    public static final RegistryObject<Item> BOLTDRAKE_TALISMAN = registerTalisman("boltdrake_talisman", StatusEffectInit.BOLTDRAKE_TALISMAN, 2);
    public static final RegistryObject<Item> HALIGDRAKE_TALISMAN = registerTalisman("haligdrake_talisman", StatusEffectInit.HALIGDRAKE_TALISMAN, 2);
    public static final RegistryObject<Item> PEARLDRAKE_TALISMAN = registerTalisman("pearldrake_talisman", StatusEffectInit.PEARLDRAKE_TALISMAN, 2);
    public static final RegistryObject<Item> BLESSED_DEW_TALISMAN = registerTalisman("blessed_dew_talisman", StatusEffectInit.BLESSED_DEW_TALISMAN);
    public static final RegistryObject<Item> CERULEAN_AMBER_MEDALLION = registerTalisman("cerulean_amber_medallion", StatusEffectInit.CERULEAN_AMBER_MEDALLION, 2);
    public static final RegistryObject<Item> VIRIDIAN_AMBER_MEDALLION = registerTalisman("viridian_amber_medallion", StatusEffectInit.VIRIDIAN_AMBER_MEDALLION, 2);
    public static final RegistryObject<Item> IMMUNIZING_HORN_CHARM = registerTalisman("immunizing_horn_charm", StatusEffectInit.IMMUNIZING_HORN_CHARM, 1);
    public static final RegistryObject<Item> STALWART_HORN_CHARM = registerTalisman("stalwart_horn_charm", StatusEffectInit.STALWART_HORN_CHARM, 1);
    public static final RegistryObject<Item> CLARIFYING_HORN_CHARM = registerTalisman("clarifying_horn_charm", StatusEffectInit.CLARIFYING_HORN_CHARM, 1);
    public static final RegistryObject<Item> MOTTLED_NECKLACE = registerTalisman("mottled_necklace", StatusEffectInit.MOTTLED_NECKLACE, 1);
    public static final RegistryObject<Item> PRINCE_OF_DEATHS_PUSTULE = registerTalisman("prince_of_deaths_pustule", StatusEffectInit.PRINCE_OF_DEATHS_PUSTULE, 1);
    public static final RegistryObject<Item> TWINBLADE_TALISMAN = registerTalisman("twinblade_talisman", StatusEffectInit.TWINBLADE_TALISMAN);
    public static final RegistryObject<Item> LANCE_TALISMAN = registerTalisman("lance_talisman", StatusEffectInit.LANCE_TALISMAN);
    public static final RegistryObject<Item> CLAW_TALISMAN = registerTalisman("claw_talisman", StatusEffectInit.CLAW_TALISMAN);
    public static final RegistryObject<Item> FLOCKS_CANVAS_TALISMAN = registerTalisman("flocks_canvas_talisman", StatusEffectInit.FLOCKS_CANVAS_TALISMAN);
    public static final RegistryObject<Item> TAKERS_CAMEO = registerTalisman("takers_cameo", StatusEffectInit.TAKERS_CAMEO);
    public static final RegistryObject<Item> ANCESTRAL_SPIRITS_HORN = registerTalisman("ancestral_spirits_horn", StatusEffectInit.ANCESTRAL_SPIRITS_HORN);

    public static final RegistryObject<CreativeModeTab> TAB = registerCreativeModeTab(Constants.MOD_ID, ItemInit.DEBUG, GENERAL_LIST);
    public static final RegistryObject<CreativeModeTab> TALISMAN = registerTalismanTab("talismans", ItemInit.CRIMSON_AMBER_MEDALLION);
    public static final RegistryObject<CreativeModeTab> WEAPON = registerCreativeModeTab("equipment", ItemInit.DEBUG, EQUIPMENT_LIST, item -> item instanceof AbstractWeapon, ((item, output) -> registerScaledWeapons(output, item)));


    public static RegistryObject<Item> registerCrystalTear(String name, Supplier<MobEffect> mobEffect, int duration) {
        return registerGeneralItem(name, () -> new CrystalTearItem(mobEffect, duration, itemProperties()));
    }

    public static RegistryObject<Item> registerCookbook(String name) {
        return registerGeneralItem(name, () -> new CookbookItem(itemProperties()));
    }

    public static RegistryObject<Item> registerTalisman(String name, Supplier<MobEffect> mobEffect) {
        return registerTalisman(name, mobEffect, 0);
    }

    public static RegistryObject<Item> registerTalisman(String name, Supplier<MobEffect> mobEffect, int maxUpgrade) {
        RegistryObject<Item> talismanObj = registerItem(name, () -> new TalismanItem(mobEffect, itemProperties()));
        TALISMAN_LIST.put(talismanObj, maxUpgrade);
        SIMPLE_ITEM_LIST.add(talismanObj);
        return talismanObj;
    }

    protected static RegistryObject<Item> registerSimpleItem(String name) {
        return registerSimpleItem(name, itemProperties());
    }

    protected static RegistryObject<Item> registerSimpleItem(String name, Item.Properties properties) {
        return registerItem(name, () -> new Item(properties), GENERAL_LIST, SIMPLE_ITEM_LIST);
    }

    protected static RegistryObject<Item> registerGeneralItem(String name, Supplier<Item> item) {
        return registerItem(name, item, GENERAL_LIST, SIMPLE_ITEM_LIST);
    }

    protected static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item, Collection<RegistryObject<? extends Item>> tabList, Collection<RegistryObject<? extends Item>> modelList) {
        var registryObject = registerItem(name, item);
        tabList.add(registryObject);
        modelList.add(registryObject);
        return registryObject;
    }

    protected static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    protected static <T extends Item> RegistryObject<CreativeModeTab> registerCreativeModeTab(String name, RegistryObject<T> item, List<RegistryObject<? extends Item>> registryObjects) {
        return registerCreativeModeTab(name, item, registryObjects, item1 -> false, (item1, output) -> {});
    }

    protected static RegistryObject<CreativeModeTab> registerTalismanTab(String name, RegistryObject<Item> item) {
        return CREATIVE_MODE_TABS.register(name, () -> CreativeModeTab.builder()
                .icon(() -> new ItemStack(item.get()))
                .title(Component.translatable("itemgroup." + name + ".tab"))
                .displayItems(
                        ((itemDisplayParameters, output) -> {
                            for (Map.Entry<RegistryObject<? extends Item>, Integer> entry : TALISMAN_LIST.entrySet()) {
                                for (int i = 0; i <= entry.getValue(); i++) {
                                    ItemStack stack = new ItemStack(entry.getKey().get());
                                    stack.getOrCreateTag().putInt("tier", i);
                                    output.accept(stack);
                                }
                            }
                        })
                ).build());
    }

    protected static <T extends Item> RegistryObject<CreativeModeTab> registerCreativeModeTab(String name, RegistryObject<? extends Item> item, List<RegistryObject<? extends Item>> registryObjects, Predicate<Item> itemPredicate, BiConsumer<Item, CreativeModeTab.Output> itemConsumer) {
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
        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.put("Weapon", weapon.getWeapon().serializeNBT());
        nbt.putInt("WeaponLevel", 0);
        output.accept(itemStack);
    }

    private static InteractionResultHolder<ItemStack> increaseSlots(Level level, Player player, InteractionHand usedHand, boolean isTalisman) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        if (!level.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            if (isTalisman) {
                EntityStatusUtil.increaseTalismanPouches(serverPlayer);
            } else {
                EntityStatusUtil.increaseMemoryStones(serverPlayer);
            }
        }

        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }

        player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide);
    }

    public static Item.Properties itemProperties() {
        return new Item.Properties();
    }

    public static void register(IEventBus modEventBus) {
        EquipmentInit.init();
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}
