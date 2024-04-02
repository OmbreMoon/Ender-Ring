package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.world.effect.CrimsonAmberEffect;
import com.ombremoon.enderring.common.object.world.effect.ShabririWoeEffect;
import com.ombremoon.enderring.common.object.world.effect.StatusEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class StatusEffectInit {
    public static DeferredRegister<MobEffect> STATUS_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MOD_ID);

    public static RegistryObject<MobEffect> PHYSICAL_DAMAGE_NEGATION = registerSimpleEffect("physical_damage_negation", MobEffectCategory.BENEFICIAL, 234227227);
    public static RegistryObject<MobEffect> CRIMSON_AMBER_MEDALLION = registerStatusEffect("crimson_amber_medallion", new CrimsonAmberEffect(MobEffectCategory.BENEFICIAL, 234227227).addAttributeModifier(Attributes.MAX_HEALTH, "90f5e679-991d-4f90-b60f-6c8868ea78de", 1.0F, AttributeModifier.Operation.MULTIPLY_BASE));
    public static RegistryObject<MobEffect> SHABRIRIS_WOE = registerStatusEffect("shabriris_woe", new ShabririWoeEffect(MobEffectCategory.NEUTRAL, 234227227));

    private static RegistryObject<MobEffect> registerStatusEffect(String name, MobEffect statusEffect) {
        return STATUS_EFFECTS.register(name, () -> statusEffect);
    }

    private static RegistryObject<MobEffect> registerSimpleEffect(String name, MobEffectCategory category, int color) {
        return STATUS_EFFECTS.register(name, () -> new StatusEffect(category, color));
    }

    public static void register(IEventBus modEventBus) {
        STATUS_EFFECTS.register(modEventBus);
    }
}
