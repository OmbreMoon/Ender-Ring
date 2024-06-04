package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.world.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.UUID;
import java.util.function.Supplier;

public class StatusEffectInit {
    public static DeferredRegister<MobEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MOD_ID);

    public static RegistryObject<MobEffect> PHYSICAL_DAMAGE_NEGATION = registerSimpleEffect("physical_damage_negation", MobEffectCategory.BENEFICIAL, 234227227);
    public static RegistryObject<MobEffect> CRIMSON_AMBER_MEDALLION = registerStatusEffect("crimson_amber_medallion", new CrimsonAmberEffect(MobEffectCategory.BENEFICIAL, 234227227).addAttributeModifier(Attributes.MAX_HEALTH, "90f5e679-991d-4f90-b60f-6c8868ea78de", 1.0F, AttributeModifier.Operation.MULTIPLY_BASE));
    public static RegistryObject<MobEffect> SHABRIRIS_WOE = registerStatusEffect("shabriris_woe", new ShabririWoeEffect(MobEffectCategory.NEUTRAL, 234227227));
    public static RegistryObject<MobEffect> INVIGORATING_MEAT = registerStatusEffect("invigorating_cured_meat", new CuredMeatEffect(MobEffectCategory.BENEFICIAL, 234227227, EntityAttributeInit.ROBUSTNESS, new AttributeModifier(UUID.fromString("3b51a5e3-f445-494f-a8c5-9d8ccbe4462c"), "Cured Meat", 100, AttributeModifier.Operation.ADDITION)));

    //CRYSTAL TEARS
    public static RegistryObject<MobEffect> CRIMSON_CRYSTAL = registerSimpleEffect("crimson_crystal_tear", MobEffectCategory.BENEFICIAL, 234227227);
    public static RegistryObject<MobEffect> CERULEAN_CRYSTAL = registerSimpleEffect("cerulean_crystal_tear", MobEffectCategory.BENEFICIAL, 234227227);
    public static RegistryObject<MobEffect> OPALINE_BUBBLE = registerSimpleEffect("opaline_bubble_tear", MobEffectCategory.BENEFICIAL, 234227227);
    public static RegistryObject<MobEffect> CERULEAN_HIDDEN = registerSimpleEffect("cerulean_hidden_tear", MobEffectCategory.BENEFICIAL, 234227227);

    //TALISMANS
    public static RegistryObject<MobEffect> GREEN_TURTLE = registerAttributeEffect("green_turtle_talisman", MobEffectCategory.BENEFICIAL, 234227227, EpicFightAttributes.STAMINA_REGEN, "d2a006de-1f50-409e-ba98-55eccdecdee2", 0.2F, AttributeModifier.Operation.ADDITION);
    public static RegistryObject<MobEffect> RADAGONS_SORESEAL = registerStatusEffect("radagons_soreseal", new RadagonSoresealEffect(MobEffectCategory.NEUTRAL, 234227227, EntityAttributeInit.VIGOR, EntityAttributeInit.ENDURANCE, EntityAttributeInit.STRENGTH, EntityAttributeInit.DEXTERITY, new AttributeModifier(UUID.fromString("92ee04e4-66be-41d2-88e2-9bb7043ab9d2"), "Radagon's Soreseal", 5.0F, AttributeModifier.Operation.ADDITION)));
    public static RegistryObject<MobEffect> SACRIFICIAL_TWIG = registerSimpleEffect("sacrificial_twig", MobEffectCategory.BENEFICIAL, 234227227);

    private static RegistryObject<MobEffect> registerStatusEffect(String name, MobEffect statusEffect) {
        return STATUS_EFFECTS.register(name, () -> statusEffect);
    }

    private static RegistryObject<MobEffect> registerAttributeEffect(String name, MobEffectCategory category, int color, Supplier<Attribute> attribute, Supplier<Attribute> attribute1, Supplier<Attribute> attribute2, Supplier<Attribute> attribute3, String uuid, float amount, AttributeModifier.Operation operation) {
        return STATUS_EFFECTS.register(name, () -> new StatusEffect(category, color).addAttributeModifier(attribute.get(), uuid, amount, operation).addAttributeModifier(attribute1.get(), uuid, amount, operation).addAttributeModifier(attribute2.get(), uuid, amount, operation).addAttributeModifier(attribute3.get(), uuid, amount, operation));
    }

    private static RegistryObject<MobEffect> registerAttributeEffect(String name, MobEffectCategory category, int color, Supplier<Attribute> attribute, Supplier<Attribute> attribute1, Supplier<Attribute> attribute2, String uuid, float amount, AttributeModifier.Operation operation) {
        return STATUS_EFFECTS.register(name, () -> new StatusEffect(category, color).addAttributeModifier(attribute.get(), uuid, amount, operation).addAttributeModifier(attribute1.get(), uuid, amount, operation).addAttributeModifier(attribute2.get(), uuid, amount, operation));
    }

    private static RegistryObject<MobEffect> registerAttributeEffect(String name, MobEffectCategory category, int color, Supplier<Attribute> attribute, Supplier<Attribute> attribute1, String uuid, float amount, AttributeModifier.Operation operation) {
        return STATUS_EFFECTS.register(name, () -> new StatusEffect(category, color).addAttributeModifier(attribute.get(), uuid, amount, operation).addAttributeModifier(attribute1.get(), uuid, amount, operation));
    }

    private static RegistryObject<MobEffect> registerAttributeEffect(String name, MobEffectCategory category, int color, Supplier<Attribute> attribute, String uuid, float amount, AttributeModifier.Operation operation) {
        return STATUS_EFFECTS.register(name, () -> new StatusEffect(category, color).addAttributeModifier(attribute.get(), uuid, amount, operation));
    }

    private static RegistryObject<MobEffect> registerSimpleEffect(String name, MobEffectCategory category, int color) {
        return STATUS_EFFECTS.register(name, () -> new StatusEffect(category, color));
    }

    public static void register(IEventBus modEventBus) {
        STATUS_EFFECTS.register(modEventBus);
    }
}
