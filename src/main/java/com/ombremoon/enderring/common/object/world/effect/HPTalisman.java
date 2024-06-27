package com.ombremoon.enderring.common.object.world.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HPTalisman extends ModifiedAttributeEffect {
    private final boolean maxHp;

    public HPTalisman(MobEffectCategory category, int color, List<Supplier<Attribute>> attributes, AttributeModifier modifier, Map<Integer, String> translations, Map<Integer, Float> tiers, boolean maxHp) {
        super(category, color, attributes, modifier, translations, tiers);
        this.maxHp = maxHp;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        boolean triggerEffect = (maxHp && pLivingEntity.getHealth() == pLivingEntity.getMaxHealth())
                || (!maxHp && pLivingEntity.getHealth() <= pLivingEntity.getMaxHealth() * 0.2F);
        if (triggerEffect) {
            for (Supplier<Attribute> attribute : attributes) {
                addAttributeModifier(pLivingEntity,
                        attribute,
                        modifier.getId(),
                        modifier.getName(),
                        getAttributeModifierValue(pAmplifier, modifier),
                        modifier.getOperation());
            }
        } else {
            for (Supplier<Attribute> attribute : attributes) {
                removeModifier(pLivingEntity, attribute, modifier.getId());
            }
        }

    }
}
