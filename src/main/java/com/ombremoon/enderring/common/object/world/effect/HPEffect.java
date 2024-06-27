package com.ombremoon.enderring.common.object.world.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.w3c.dom.Attr;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class HPEffect extends ModifiedAttributeEffect {
    private final boolean maxHp;

    public HPEffect(MobEffectCategory category, int color, Map<AttributeModifier, List<Supplier<Attribute>>> attributes, Map<Integer, String> translations, Map<String, Map<Integer, Double>> tiers, boolean maxHp) {
        super(category, color, attributes, translations, tiers);
        this.maxHp = maxHp;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        float currentHp = pLivingEntity.getHealth();
        float maximumHp = pLivingEntity.getMaxHealth();
        boolean triggerEffect = (maxHp && currentHp == maximumHp)
                || (!maxHp && currentHp <= maximumHp * 0.2F);

        if (triggerEffect) {
            for (AttributeModifier modifier : attributes.keySet()) {
                for (Supplier<Attribute> attribute : attributes.get(modifier)) {
                    addAttributeModifier(pLivingEntity, attribute, modifier, getAttributeModifierValue(pAmplifier, modifier));
                }
            }
        } else {
            for (AttributeModifier modifier : attributes.keySet()) {
                for (Supplier<Attribute> attribute : attributes.get(modifier)) {
                    removeModifier(pLivingEntity, attribute, modifier.getId());
                }
            }
        }

    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 10 == 0;
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }
}
