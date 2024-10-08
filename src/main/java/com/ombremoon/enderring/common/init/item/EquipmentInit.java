package com.ombremoon.enderring.common.init.item;

import com.ombremoon.enderring.common.magic.Classification;
import com.ombremoon.enderring.common.magic.Classifications;
import com.ombremoon.enderring.common.object.item.MidasGauntletItem;
import com.ombremoon.enderring.common.object.item.equipment.ERArmor;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.ranged.AbstractArrowItem;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.CatalystWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.KatanaWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.*;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.SealWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.StaffWeapon;
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

    //Great swords
    public static final RegistryObject<GreatSwordWeapon> LORDSWORNS_GREATSWORD = registerGreakSword("lordsworns_greatsword");

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
    public static final RegistryObject<StaffWeapon> ASTROLOGER_STAFF = registerStaff("astrologers_staff");
    public static final RegistryObject<StaffWeapon> GLINTSTONE_STAFF = registerStaff("glintstone_staff");
    public static final RegistryObject<SealWeapon> FINGER_SEAL = registerSeal("finger_seal");
    public static final RegistryObject<SealWeapon> GIANT_SEAL = registerSeal("giants_seal", 0.2F, Classifications.FIRE_MONK, Classifications.FIRE_GIANT);

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
    public static final RegistryObject<ERArmor> BLUE_CLOTH_COWL = registerArmor("blue_cloth_cowl", ArmorInit.BLUE_CLOTH, ArmorItem.Type.HELMET);
    public static final RegistryObject<ERArmor> BLUE_CLOTH_VEST = registerArmor("blue_cloth_vest", ArmorInit.BLUE_CLOTH, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<ERArmor> BLUE_CLOTH_LEGGINGS = registerArmor("blue_cloth_leggings", ArmorInit.BLUE_CLOTH, ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<ERArmor> BLUE_CLOTH_GREAVES = registerArmor("blue_cloth_greaves", ArmorInit.BLUE_CLOTH, ArmorItem.Type.BOOTS);

    // Astrologer Set
    public static final RegistryObject<ModdedArmor> ASTROLOGER_HOOD = registerArmor("astrologer_hood", ArmorInit.ASTROLOGER, ArmorItem.Type.HELMET);
    public static final RegistryObject<ModdedArmor> ASTROLOGER_ROBE = registerArmor("astrologer_robe", ArmorInit.ASTROLOGER, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<ModdedArmor> ASTROLOGER_GLOVES = registerArmor("astrologer_gloves", ArmorInit.ASTROLOGER, ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<ModdedArmor> ASTROLOGER_TROUSERS = registerArmor("astrologer_trousers", ArmorInit.ASTROLOGER, ArmorItem.Type.BOOTS);

    // Bandit Set
    public static final RegistryObject<ModdedArmor> BANDIT_MASK = registerArmor("bandit_mask", ArmorInit.BANDIT, ArmorItem.Type.HELMET);
    public static final RegistryObject<ModdedArmor> BANDIT_GARBS = registerArmor("bandit_garbs", ArmorInit.BANDIT, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<ModdedArmor> BANDIT_BRACERS = registerArmor("bandit_bracers", ArmorInit.BANDIT, ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<ModdedArmor> BANDIT_BOOTS = registerArmor("bandit_boots", ArmorInit.BANDIT, ArmorItem.Type.BOOTS);

    // Confessor Set
    public static final RegistryObject<ModdedArmor> CONFESSOR_HOOD = registerArmor("confessor_hood", ArmorInit.CONFESSOR, ArmorItem.Type.HELMET);
    public static final RegistryObject<ModdedArmor> CONFESSOR_ARMOR = registerArmor("confessor_armor", ArmorInit.CONFESSOR, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<ModdedArmor> CONFESSOR_GAUNTLETS = registerArmor("confessor_gauntlets", ArmorInit.CONFESSOR, ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<ModdedArmor> CONFESSOR_BOOTS = registerArmor("confessor_boots", ArmorInit.CONFESSOR, ArmorItem.Type.BOOTS);

    // Hero Set
    public static final RegistryObject<ModdedArmor> HERO_HELM = registerArmor("hero_helm", ArmorInit.HERO, ArmorItem.Type.HELMET);
    public static final RegistryObject<ModdedArmor> HERO_ARMOR = registerArmor("hero_armor", ArmorInit.HERO, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<ModdedArmor> HERO_GAUNTLETS = registerArmor("hero_gauntlets", ArmorInit.HERO, ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<ModdedArmor> HERO_BOOTS = registerArmor("hero_boots", ArmorInit.HERO, ArmorItem.Type.BOOTS);

    // Prisoner Set
    public static final RegistryObject<ModdedArmor> PRISONER_IRON_MASK = registerArmor("prisoner_iron_mask", ArmorInit.PRISONER, ArmorItem.Type.HELMET);
    public static final RegistryObject<ModdedArmor> PRISONER_CLOTHING = registerArmor("prisoner_clothing", ArmorInit.PRISONER, ArmorItem.Type.CHESTPLATE);

    // Prophet Set
    public static final RegistryObject<ModdedArmor> PROPHET_BLINDFOLD = registerArmor("prophet_blindfold", ArmorInit.PROPHET, ArmorItem.Type.HELMET);
    public static final RegistryObject<ModdedArmor> PROPHET_ROBE = registerArmor("prophet_robe", ArmorInit.PROPHET, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<ModdedArmor> PROPHET_TROUSERS = registerArmor("prophet_trousers", ArmorInit.PROPHET, ArmorItem.Type.LEGGINGS);

    // Land of Reeds Set
    public static final RegistryObject<ModdedArmor> LAND_OF_REEDS_HELM = registerArmor("land_of_reeds_helm", ArmorInit.LAND_OF_REEDS, ArmorItem.Type.HELMET);
    public static final RegistryObject<ModdedArmor> LAND_OF_REEDS_ARMOR = registerArmor("land_of_reeds_armor", ArmorInit.LAND_OF_REEDS, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<ModdedArmor> LAND_OF_REEDS_GAUNTLETS = registerArmor("land_of_reeds_gauntlets", ArmorInit.LAND_OF_REEDS, ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<ModdedArmor> LAND_OF_REEDS_LEGGINGS = registerArmor("land_of_reeds_leggings", ArmorInit.LAND_OF_REEDS, ArmorItem.Type.BOOTS);

    // Vagabond Set
    public static final RegistryObject<ModdedArmor> VAGABOND_KNIGHT_HELM = registerArmor("vagabond_knight_helm", ArmorInit.VAGABOND, ArmorItem.Type.HELMET);
    public static final RegistryObject<ModdedArmor> VAGABOND_KNIGHT_ARMOR = registerArmor("vagabond_knight_armor", ArmorInit.VAGABOND, ArmorItem.Type.CHESTPLATE);
    public static final RegistryObject<ModdedArmor> VAGABOND_KNIGHT_GAUNTLETS = registerArmor("vagabond_knight_gauntlets", ArmorInit.VAGABOND, ArmorItem.Type.LEGGINGS);
    public static final RegistryObject<ModdedArmor> VAGABOND_KNIGHT_GREAVES = registerArmor("vagabond_knight_greaves", ArmorInit.VAGABOND, ArmorItem.Type.BOOTS);

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

    protected static RegistryObject<SealWeapon> registerSeal(String name) {
        return registerSeal(name, 0.0F, (Classification) null);
    }

    protected static RegistryObject<SealWeapon> registerSeal(String name, float spellBoost, Classification... classifications) {
        return registerCatalyst(name, () -> new SealWeapon(itemProperties(), spellBoost, classifications));
    }

    protected static RegistryObject<StaffWeapon> registerStaff(String name) {
        return registerStaff(name, 0.0F, (Classification) null);
    }

    protected static RegistryObject<StaffWeapon> registerStaff(String name, float spellBoost, Classification... classifications) {
        return registerCatalyst(name, () -> new StaffWeapon(itemProperties(), spellBoost, classifications));
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

    protected static RegistryObject<GreatSwordWeapon> registerGreakSword(String name) {
        return registerMeleeWeapon(name, () -> new GreatSwordWeapon(itemProperties()));
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

    protected static RegistryObject<ERArmor> registerArmor(String name, ArmorMaterial armorMaterial, ArmorItem.Type type) {
        return registerArmor(name, () -> new ERArmor(armorMaterial, type, itemProperties()));
    }

    private static <T extends ERArmor> RegistryObject<T> registerArmor(String name, Supplier<T> armor) {
        return registerItem(name, armor, EQUIPMENT_LIST, SIMPLE_ITEM_LIST);
    }

    private static <T extends CatalystWeapon> RegistryObject<T> registerCatalyst(String name, Supplier<T> catalyst) {
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
