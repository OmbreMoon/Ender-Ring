package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityAttributeInit {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Constants.MOD_ID);

    public static final RegistryObject<Attribute> RUNE_LEVEL = registerAttribute("rune_level", 1.0D, 1.0D, 713.0D, true);
    public static final RegistryObject<Attribute> RUNES_HELD = registerAttribute("runes_held", 0.0D, 0.0D, 999999999.0D, true);
    public static final RegistryObject<Attribute> MAX_FP = registerAttribute("max_fp", 1.0D, 1.0D, 450.0D, true);

    public static final RegistryObject<Attribute> VIGOR = registerAttribute("vigor", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> MIND = registerAttribute("mind", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> ENDURANCE = registerAttribute("endurance", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> STRENGTH = registerAttribute("strength", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> DEXTERITY = registerAttribute("dexterity", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> INTELLIGENCE = registerAttribute("intelligence", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> FAITH = registerAttribute("faith", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> ARCANE = registerAttribute("arcane", 1.0D, 1.0D, 99.0D, true);

    public static final RegistryObject<Attribute> ROBUSTNESS = registerAttribute("robustness", 1.0D, 0.0D, 230.0D, true);

    public static final RegistryObject<Attribute> PHYS_DAMAGE = registerAttribute("physical_damage", 1.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> STRIKE_DAMAGE = registerAttribute("strike_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> SLASH_DAMAGE = registerAttribute("slash_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> PIERCE_DAMAGE = registerAttribute("pierce_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> MAGIC_DAMAGE = registerAttribute("magic_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> FIRE_DAMAGE = registerAttribute("fire_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> LIGHT_DAMAGE = registerAttribute("lightning_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> HOLY_DAMAGE = registerAttribute("holy_damage", 0.0D, 0.0D, 5120.0D, true);

    public static final RegistryObject<Attribute> PHYS_DEFENSE = registerAttribute("physical_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> STRIKE_DEFENSE = registerAttribute("strike_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> SLASH_DEFENSE = registerAttribute("slash_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> PIERCE_DEFENSE = registerAttribute("pierce_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> MAGIC_DEFENSE = registerAttribute("magic_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> FIRE_DEFENSE = registerAttribute("fire_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> LIGHT_DEFENSE = registerAttribute("lightning_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> HOLY_DEFENSE = registerAttribute("holy_defense", 1.0D, 0.0D, 500.0D, true);

    public static final RegistryObject<Attribute> PHYS_NEGATE = registerAttribute("physical_negation", 0.0D, -1.0D, 1.0D, true);
    public static final RegistryObject<Attribute> STRIKE_NEGATE = registerAttribute("strike_negation", 0.0D, -1.0D, 1.0D, true);
    public static final RegistryObject<Attribute> SLASH_NEGATE = registerAttribute("slash_negation", 0.0D, -1.0D, 1.0D, true);
    public static final RegistryObject<Attribute> PIERCE_NEGATE = registerAttribute("pierce_negation", 0.0D, -1.0D, 1.0D, true);
    public static final RegistryObject<Attribute> MAGIC_NEGATE = registerAttribute("magic_negation", 0.0D, -1.0D, 1.0D, true);
    public static final RegistryObject<Attribute> FIRE_NEGATE = registerAttribute("fire_negation", 0.0D, -1.0D, 1.0D, true);
    public static final RegistryObject<Attribute> LIGHT_NEGATE = registerAttribute("lightning_negation", 0.0D, -1.0D, 1.0D, true);
    public static final RegistryObject<Attribute> HOLY_NEGATE = registerAttribute("holy_negation", 0.0D, -1.0D, 1.0D, true);

    public static RegistryObject<Attribute> registerAttribute(String name, double defaultVal, double minVal, double maxVal, boolean syncable) {
        return ATTRIBUTES.register(name, () -> new RangedAttribute("attributes.name." + Constants.MOD_ID + "." + name, defaultVal, minVal, maxVal).setSyncable(syncable));
    }

    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
    }
}
