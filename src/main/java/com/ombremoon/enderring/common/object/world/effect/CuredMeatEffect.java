package com.ombremoon.enderring.common.object.world.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public class CuredMeatEffect extends ModifiedAttributeEffect {
    public CuredMeatEffect(MobEffectCategory pCategory, int pColor, Supplier<Attribute> attribute, AttributeModifier attributeModifier) {
        super(pCategory, pColor, attribute, attributeModifier);
    }

    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        return pAmplifier == 1 ? 75 : pModifier.getAmount();
    }
}
