package com.ombremoon.enderring.common.init.item;

import com.ombremoon.enderring.common.object.item.equipment.ModdedArmor;
import com.ombremoon.enderring.common.object.item.equipment.weapon.MeleeWeapon;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.SealItem;
import com.ombremoon.enderring.common.object.item.equipment.weapon.magic.StaffItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class EquipmentInit extends ItemInit {
    public static void init() {}

    //Daggers
    public static RegistryObject<Item> DAGGER = registerMeleeWeapon("dagger");
    public static RegistryObject<Item> BLACK_KNIFE = registerMeleeWeapon("black_knife");

    //Staves & Seals
    public static RegistryObject<Item> ASTROLOGER_STAFF = registerStaffItem("astrologer_staff");
    public static RegistryObject<Item> GLINTSTONE_STAFF = registerStaffItem("glintstone_staff");
    public static RegistryObject<Item> FINGER_SEAL = registerSealItem("finger_seal");

    //Armor Sets
    public static RegistryObject<Item> BLUE_CLOTH_COWL = registerArmorItem("blue_cloth_cowl", ArmorsInit.BLUE_CLOTH, ArmorItem.Type.HELMET);
    public static RegistryObject<Item> BLUE_CLOTH_VEST = registerArmorItem("blue_cloth_vest", ArmorsInit.BLUE_CLOTH, ArmorItem.Type.CHESTPLATE);
    public static RegistryObject<Item> BLUE_CLOTH_LEGGINGS = registerArmorItem("blue_cloth_leggings", ArmorsInit.BLUE_CLOTH, ArmorItem.Type.LEGGINGS);
    public static RegistryObject<Item> BLUE_CLOTH_GREAVES = registerArmorItem("blue_cloth_greaves", ArmorsInit.BLUE_CLOTH, ArmorItem.Type.BOOTS);

    //AMMUNITION
    public static RegistryObject<Item> BONE_ARROW;
    public static RegistryObject<Item> BONE_BOLT;

    public static RegistryObject<Item> registerStaffItem(String name) {
        return registerItem(name, () -> new StaffItem(getItemProperties()), EQUIPMENT_LIST, HANDHELD_LIST);
    }

    public static RegistryObject<Item> registerArmorItem(String name, ArmorMaterial armorMaterial, ArmorItem.Type type) {
        return registerItem(name, () -> new ModdedArmor(armorMaterial, type, getItemProperties()), EQUIPMENT_LIST, SIMPLE_ITEM_LIST);
    }

    public static RegistryObject<Item> registerMeleeWeapon(String name) {
        return registerItem(name, () -> new MeleeWeapon(getItemProperties()), EQUIPMENT_LIST, HANDHELD_LIST);
    }

    public static RegistryObject<Item> registerSealItem(String name) {
        return registerItem(name, () -> new SealItem(getItemProperties()), EQUIPMENT_LIST, HANDHELD_LIST);
    }
}
