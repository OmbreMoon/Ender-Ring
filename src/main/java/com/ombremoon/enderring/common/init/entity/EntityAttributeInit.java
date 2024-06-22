package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.UUID;

public class EntityAttributeInit {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Constants.MOD_ID);
    public static final Object2ObjectOpenHashMap<Attribute, Attribute> ATTRIBUTE_MAP = new Object2ObjectOpenHashMap<>();
    public static final UUID IMMUNITY_ID = UUID.fromString("8d4a600b-351a-4c3f-9fa3-363a6ea1253a");
    public static final UUID ROBUSTNESS_ID = UUID.fromString("9f4e34a9-17bb-4d71-b269-3098883fc1bf");
    public static final UUID FOCUS_ID = UUID.fromString("68dd9d5d-1078-42af-93d1-c8abf0b5f681");
    public static final UUID VITALITY_ID = UUID.fromString("75445161-a8c7-43f5-9312-037e5c11e2cf");
    public static final UUID PHYS_ID = UUID.fromString("8c5d5ca3-d3af-49a5-82be-7bae01355578");
    public static final UUID STRIKE_ID = UUID.fromString("de9e68d0-f395-466b-9259-e0e62fe02e14");
    public static final UUID SLASH_ID = UUID.fromString("603a92de-3713-4e98-9955-81ffc590bca2");
    public static final UUID PIERCE_ID = UUID.fromString("78d92ed6-c26d-488a-bee6-a1042b482531");
    public static final UUID MAGIC_ID = UUID.fromString("93c9c75e-3aa4-47fd-80b8-5528e316df44");
    public static final UUID FIRE_ID = UUID.fromString("a7dfe4d8-9f0c-4527-bedc-bdef56b4dde0");
    public static final UUID LIGHTNING_ID = UUID.fromString("e3631fcc-d685-4f15-9674-9b101307a85a");
    public static final UUID HOLY_ID = UUID.fromString("70ee84bd-67ac-48b1-a574-ca9801a105a5");

    public static final RegistryObject<Attribute> RUNE_LEVEL = registerAttribute("rune_level", 1.0D, 1.0D, 713.0D, true);
    public static final RegistryObject<Attribute> MAX_FP = registerAttribute("max_fp", 1.0D, 1.0D, 450.0D, true);

    public static final RegistryObject<Attribute> VIGOR = registerAttribute("vigor", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> MIND = registerAttribute("mind", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> ENDURANCE = registerAttribute("endurance", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> STRENGTH = registerAttribute("strength", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> DEXTERITY = registerAttribute("dexterity", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> INTELLIGENCE = registerAttribute("intelligence", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> FAITH = registerAttribute("faith", 1.0D, 1.0D, 99.0D, true);
    public static final RegistryObject<Attribute> ARCANE = registerAttribute("arcane", 1.0D, 1.0D, 99.0D, true);

    public static final RegistryObject<Attribute> IMMUNITY = registerAttribute("immunity", 1.0D, 0.0D, 230.0D, true);
    public static final RegistryObject<Attribute> ROBUSTNESS = registerAttribute("robustness", 1.0D, 0.0D, 230.0D, true);
    public static final RegistryObject<Attribute> FOCUS = registerAttribute("focus", 1.0D, 0.0D, 230.0D, true);
    public static final RegistryObject<Attribute> VITALITY = registerAttribute("vitality", 1.0D, 0.0D, 230.0D, true);

    public static final RegistryObject<Attribute> PHYS_DAMAGE = registerAttribute("physical_damage", 1.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> STRIKE_DAMAGE = registerAttribute("strike_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> SLASH_DAMAGE = registerAttribute("slash_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> PIERCE_DAMAGE = registerAttribute("pierce_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> MAGIC_DAMAGE = registerAttribute("magic_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> FIRE_DAMAGE = registerAttribute("fire_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> LIGHT_DAMAGE = registerAttribute("lightning_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> HOLY_DAMAGE = registerAttribute("holy_damage", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> SORCERY_SCALING = registerAttribute("sorcery_scaling", 0.0D, 0.0D, 5120.0D, true);
    public static final RegistryObject<Attribute> INCANT_SCALING = registerAttribute("incant_scaling", 0.0D, 0.0D, 5120.0D, true);

    public static final RegistryObject<Attribute> PHYS_DEFENSE = registerAttribute("physical_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> MAGIC_DEFENSE = registerAttribute("magic_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> FIRE_DEFENSE = registerAttribute("fire_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> LIGHT_DEFENSE = registerAttribute("lightning_defense", 1.0D, 0.0D, 500.0D, true);
    public static final RegistryObject<Attribute> HOLY_DEFENSE = registerAttribute("holy_defense", 1.0D, 0.0D, 500.0D, true);

    public static final RegistryObject<Attribute> PHYS_NEGATE = registerAttribute("physical_negation", 0.0D, -100.0D, 100.0D, true);
    public static final RegistryObject<Attribute> STRIKE_NEGATE = registerAttribute("strike_negation", 0.0D, -100.0D, 100.0D, true);
    public static final RegistryObject<Attribute> SLASH_NEGATE = registerAttribute("slash_negation", 0.0D, -100.0D, 100.0D, true);
    public static final RegistryObject<Attribute> PIERCE_NEGATE = registerAttribute("pierce_negation", 0.0D, -100.0D, 100.0D, true);
    public static final RegistryObject<Attribute> MAGIC_NEGATE = registerAttribute("magic_negation", 0.0D, -100.0D, 100.0D, true);
    public static final RegistryObject<Attribute> FIRE_NEGATE = registerAttribute("fire_negation", 0.0D, -100.0D, 100.0D, true);
    public static final RegistryObject<Attribute> LIGHT_NEGATE = registerAttribute("lightning_negation", 0.0D, -100.0D, 100.0D, true);
    public static final RegistryObject<Attribute> HOLY_NEGATE = registerAttribute("holy_negation", 0.0D, -100.0D, 100.0D, true);

    public static RegistryObject<Attribute> registerAttribute(String name, double defaultVal, double minVal, double maxVal, boolean syncable) {
        return ATTRIBUTES.register(name, () -> new RangedAttribute("attributes.resourceLocation." + Constants.MOD_ID + "." + name, defaultVal, minVal, maxVal).setSyncable(syncable));
    }

    public static void register(IEventBus modEventBus) {
        ATTRIBUTES.register(modEventBus);
    }
}
