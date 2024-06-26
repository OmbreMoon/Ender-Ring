package com.ombremoon.enderring.common.object.world.effect.talisman;

import com.ombremoon.enderring.common.object.world.effect.StatusEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class CrimsonAmberEffect extends StatusEffect {
    public CrimsonAmberEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor, null);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        if (pLivingEntity.getHealth() > pLivingEntity.getMaxHealth()) {
            pLivingEntity.setHealth(pLivingEntity.getMaxHealth());
        }
    }

    @Override
    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        float f;
        switch (pAmplifier) {
            case 0 -> f = .06F;
            case 1 -> f = .07F;
            case 2 -> f = .08F;
            default -> f = (float) pModifier.getAmount();
        }
        return f;
    }
}
