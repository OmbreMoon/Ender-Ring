package com.ombremoon.enderring.common.init.item;

import com.ombremoon.enderring.common.object.item.MidasGauntletItem;
import com.ombremoon.enderring.common.object.item.equipment.ModdedArmor;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.ranged.AbstractArrowItem;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.AbstractCatalystItem;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.KatanaWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.*;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.SealItem;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.StaffItem;
import com.ombremoon.enderring.common.object.item.equipment.weapon.ranged.BowWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.ranged.CrossbowWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.ranged.RangedWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.shield.AbstractShield;
import com.ombremoon.enderring.common.object.item.equipment.weapon.shield.GreatshieldWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.shield.MediumShieldWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.shield.SmallShieldWeapon;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EquipmentInit extends ItemInit {
    public static void init() {}

    //Daggers
    public static final RegistryObject<DaggerWeapon> DAGGER = registerDagger("dagger");
    public static final RegistryObject<DaggerWeapon> GREAT_KNIFE = registerDagger("great_knife");
    public static final RegistryObject<DaggerWeapon> BLACK_KNIFE = registerDagger("black_knife");

    //Straight Swords
    public static final RegistryObject<StraightSwordWeapon> SHORT_SWORD = registerStraightSword("short_sword");
    public static final RegistryObject<StraightSwordWeapon> LONGSWORD = registerStraightSword("longsword");
    public static final RegistryObject<StraightSwordWeapon> BROADSWORD = registerStraightSword("broadsword");

    //Thrusting Swords
    public static final RegistryObject<ThrustingSwordWeapon> ESTOC = registerThrustingSword("estoc");

    //Curved Swords
    public static final RegistryObject<CurvedSwordWeapon> SCIMITAR = registerCurvedSword("scimitar");

    //Axes
    public static final RegistryObject<AxeWeapon> BATTLE_AXE = registerAxe("battle_axe");

    //Hammers
    public static final RegistryObject<HammerWeapon> CLUB = registerHammer("club");

    //Spears
    public static final RegistryObject<SpearWeapon> SHORT_SPEAR = registerSpear("short_spear");

    //Halberds
    public static final RegistryObject<HalberdWeapon> HALBERD = registerHalberd("halberd");
    public static final RegistryObject<HalberdWeapon> GUARDIAN_SWORDSPEAR = registerHalberd("guardian_swordspear");

    //Katanas
    public static final RegistryObject<KatanaWeapon> UCHIGATANA = registerKatana("uchigatana");

    //Bows & Crossbows
    public static final RegistryObject<BowWeapon> SHORTBOW = registerBow("shortbow");
    public static final RegistryObject<BowWeapon> LONGBOW = registerBow("longbow");

    //Catalysts
    public static final RegistryObject<StaffItem> ASTROLOGER_STAFF = registerStaff("astrologers_staff");
    public static final RegistryObject<StaffItem> GLINTSTONE_STAFF = registerStaff("glintstone_staff");
    public static final RegistryObject<SealItem> FINGER_SEAL = registerSeal("finger_seal");

    //Shields
    public static final RegistryObject<AbstractShield> LARGE_LEATHER_SHIELD = registerMediumShield("large_leather_shield");
    public static final RegistryObject<AbstractShield> BUCKLER = registerSmallShield("buckler");
    public static final RegistryObject<AbstractShield> SCRIPTURE_WOODEN_SHIELD = registerSmallShield("scripture_wooden_shield");
    public static final RegistryObject<AbstractShield> RIVETED_WOODEN_SHIELD = registerSmallShield("riveted_wooden_shield");
    public static final RegistryObject<AbstractShield> RIFT_SHIELD = registerSmallShield("rift_shield");
    public static final RegistryObject<AbstractShield> BLUE_CREST_HEATER_SHIELD = registerMediumShield("blue_crest_heater_shield");
    public static final RegistryObject<AbstractShield> HEATER_SHIELD = registerMediumShield("heater_shield");
    public static final RegistryObject<AbstractShield> RICKETY_SHIELD = registerSmallShield("rickety_shield");
    public static final RegistryObject<AbstractShield> RED_THORN_ROUNDSHIELD = registerSmallShield("red_thorn_roundshield");

    //Armor Sets
    public static final RegistryObject<ModdedArmor> BLUE_CLOTH_COWL = registerArmor("blue_cloth_cowl", ArmorInit.BLUE_CLOTH, ArmorItem.Type.HELMET);
    public static final RegistryObject<ModdedArmor> BLUE_CLOTH_VEST = registerArmor("blue_cloth_vest", ArmorInit.BLUE_CLOTH, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<ModdedArmor> BLUE_CLOTH_LEGGINGS = registerArmor("blue_cloth_leggings", ArmorInit.BLUE_CLOTH, ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<ModdedArmor> BLUE_CLOTH_GREAVES = registerArmor("blue_cloth_greaves", ArmorInit.BLUE_CLOTH, ArmorItem.Type.BOOTS);

    //AMMUNITION
    public static final RegistryObject<Item> BONE_ARROW = registerBoneArrow("bone_arrow");
    public static final RegistryObject<Item> BONE_BOLT = registerBoneBolt("bone_bolt");


    //PIERCE TEST
    public static final RegistryObject<Item> MIDAS_GAUNTLET = registerItem("midas_gauntlet", () -> new MidasGauntletItem(itemProperties()));



    protected static RegistryObject<Item> registerBoneArrow(String name) {
        return registerItem(name, () -> new AbstractArrowItem(itemProperties()), EQUIPMENT_LIST, SIMPLE_ITEM_LIST);
    }

    protected static RegistryObject<Item> registerBoneBolt(String name) {
        return registerItem(name, () -> new AbstractArrowItem(itemProperties()), EQUIPMENT_LIST, SIMPLE_ITEM_LIST);
    }

    protected static RegistryObject<SealItem> registerSeal(String name) {
        return registerCatalyst(name, () -> new SealItem(itemProperties()));
    }

    protected static RegistryObject<StaffItem> registerStaff(String name) {
        return registerCatalyst(name, () -> new StaffItem(itemProperties()));
    }

    protected static RegistryObject<AbstractShield> registerSmallShield(String name) {
        return registerShield(name, () -> new SmallShieldWeapon(itemProperties()));
    }

    protected static RegistryObject<AbstractShield> registerMediumShield(String name) {
        return registerShield(name, () -> new MediumShieldWeapon(itemProperties()));
    }

    protected static RegistryObject<AbstractShield> registerGreatshield(String name) {
        return registerShield(name, () -> new GreatshieldWeapon(itemProperties()));
    }

    protected static RegistryObject<CrossbowWeapon> registerCrossbow(String name) {
        return registerRangedWeapon(name, () -> new CrossbowWeapon(itemProperties()));
    }

    protected static RegistryObject<BowWeapon> registerBow(String name) {
        return registerRangedWeapon(name, () -> new BowWeapon(itemProperties()));
    }

    protected static RegistryObject<HalberdWeapon> registerHalberd(String name) {
        return registerMeleeWeapon(name, () -> new HalberdWeapon(itemProperties()));
    }

    protected static RegistryObject<SpearWeapon> registerSpear(String name) {
        return registerMeleeWeapon(name, () -> new SpearWeapon(itemProperties()));
    }

    protected static RegistryObject<HammerWeapon> registerHammer(String name) {
        return registerMeleeWeapon(name, () -> new HammerWeapon(itemProperties()));
    }

    protected static RegistryObject<AxeWeapon> registerAxe(String name) {
        return registerMeleeWeapon(name, () -> new AxeWeapon(itemProperties()));
    }

    protected static RegistryObject<KatanaWeapon> registerKatana(String name) {
        return registerMeleeWeapon(name, () -> new KatanaWeapon(itemProperties()));
    }

    protected static RegistryObject<CurvedSwordWeapon> registerCurvedSword(String name) {
        return registerMeleeWeapon(name, () -> new CurvedSwordWeapon(itemProperties()));
    }

    protected static RegistryObject<ThrustingSwordWeapon> registerThrustingSword(String name) {
        return registerMeleeWeapon(name, () -> new ThrustingSwordWeapon(itemProperties()));
    }

    protected static RegistryObject<StraightSwordWeapon> registerStraightSword(String name) {
        return registerMeleeWeapon(name, () -> new StraightSwordWeapon(itemProperties()));
    }

    protected static RegistryObject<DaggerWeapon> registerDagger(String name) {
        return registerMeleeWeapon(name, () -> new DaggerWeapon(itemProperties()));
    }

    protected static RegistryObject<ModdedArmor> registerArmor(String name, ArmorMaterial armorMaterial, ArmorItem.Type type) {
        return registerArmor(name, () -> new ModdedArmor(armorMaterial, type, itemProperties()));
    }

    private static <T extends ModdedArmor> RegistryObject<T> registerArmor(String name, Supplier<T> armor) {
        return registerItem(name, armor, EQUIPMENT_LIST, SIMPLE_ITEM_LIST);
    }

    private static <T extends AbstractCatalystItem> RegistryObject<T> registerCatalyst(String name, Supplier<T> catalyst) {
        return registerMeleeWeapon(name, catalyst);
    }

    private static <T extends AbstractShield> RegistryObject<T> registerShield(String name, Supplier<T> shield) {
        return registerMeleeWeapon(name, shield);
    }

    private static <T extends RangedWeapon> RegistryObject<T> registerRangedWeapon(String name, Supplier<T> rangedWeapon) {
        return registerWeapon(name, rangedWeapon);
    }

    private static <T extends MeleeWeapon> RegistryObject<T> registerMeleeWeapon(String name, Supplier<T> meleeWeapon) {
        return registerWeapon(name, meleeWeapon);
    }

    private static <T extends AbstractWeapon> RegistryObject<T> registerWeapon(String name, Supplier<T> abstractWeapon) {
        var registryObject = registerItem(name, abstractWeapon);
        EQUIPMENT_LIST.add(registryObject);
        HANDHELD_LIST.add(registryObject);
        return registryObject;
    }
}
