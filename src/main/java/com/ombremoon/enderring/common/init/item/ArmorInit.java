package com.ombremoon.enderring.common.init.item;

import com.ombremoon.enderring.Constants;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ArmorInit implements ArmorMaterial {
    BLUE_CLOTH("blue_cloth", 52, Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266655_) -> {
        p_266655_.put(ArmorItem.Type.HELMET, 3);
        p_266655_.put(ArmorItem.Type.CHESTPLATE, 5);
        p_266655_.put(ArmorItem.Type.LEGGINGS, 3);
        p_266655_.put(ArmorItem.Type.BOOTS, 2);
        // Enchantability, Toughness, Knockback Resistance
    }), 0, SoundEvents.ARMOR_EQUIP_NETHERITE, 5.0F, 0.2F, () -> {
        return Ingredient.of(Items.AMETHYST_SHARD);
    });

    public static final StringRepresentable.EnumCodec<ArmorMaterials> CODEC = net.minecraft.util.StringRepresentable.fromEnum(ArmorMaterials::values);
    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
        p_266653_.put(ArmorItem.Type.HELMET, 11);
        p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
        p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
        p_266653_.put(ArmorItem.Type.BOOTS, 13);
    });
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private ArmorInit(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionFunction, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Supplier<Ingredient> ingredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionFunctionForType = protectionFunction;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(ingredient);
    }


    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return HEALTH_FUNCTION_FOR_TYPE.get(pType) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionFunctionForType.get(pType);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return Constants.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
