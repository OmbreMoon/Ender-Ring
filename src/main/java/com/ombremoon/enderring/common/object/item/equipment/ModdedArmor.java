package com.ombremoon.enderring.common.object.item.equipment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;

public class ModdedArmor extends Item implements Equipable {
    private final ArmorMaterial armorMaterial;
    private final EquipmentSlot equipmentSlot;

    public ModdedArmor(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties pProperties) {
        super(pProperties.stacksTo(1));
        this.armorMaterial = armorMaterial;
        this.equipmentSlot = equipmentSlot;
    }

    public ArmorMaterial getArmorMaterial() {
        return this.armorMaterial;
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return this.equipmentSlot;
    }
}
