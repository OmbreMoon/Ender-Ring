package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.common.object.world.effect.stacking.EffectType;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class ModifiedAttributeEffect extends StatusEffect {
    protected final Map<AttributeModifier, List<Supplier<Attribute>>> attributes;

    public ModifiedAttributeEffect(MobEffectCategory category, int color, Map<AttributeModifier, List<Supplier<Attribute>>> attributes, Map<Integer, String> translations, Map<String, Map<Integer, Double>> tiers, EffectType type) {
        super(type, color, translations, tiers, (a, b) -> false, category);
        this.attributes = attributes;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        for (AttributeModifier modifier : attributes.keySet()) {
            for (Supplier<Attribute> attribute : attributes.get(modifier)) {
                addAttributeModifier(pLivingEntity, attribute, modifier, getAttributeModifierValue(pAmplifier, modifier));
            }
        }
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, int pAmplifier, double pHealth) {
        applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        for (AttributeModifier modifier : attributes.keySet()) {
            for (Supplier<Attribute> attribute : attributes.get(modifier)) {
                removeModifier(pLivingEntity, attribute, modifier.getId());
            }
        }
        if (pLivingEntity.getHealth() >= pLivingEntity.getMaxHealth())
            pLivingEntity.setHealth(pLivingEntity.getMaxHealth());
    }

    private AttributeInstance getAttributeInstance(LivingEntity livingEntity, Supplier<Attribute> attribute) {
        return livingEntity.getAttributes().getInstance(attribute.get());
    }

    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        if (this.getTier(pModifier.getId().toString(), pAmplifier) == null) return pModifier.getAmount();

        return this.getTier(pModifier.getId().toString(), pAmplifier);
    }

    protected void addAttributeModifier(LivingEntity livingEntity, Supplier<Attribute> attribute, AttributeModifier modifier, double amount) {
        AttributeInstance attributeInstance = getAttributeInstance(livingEntity, attribute);
        AttributeModifier attributeModifier = new AttributeModifier(modifier.getId(),
                modifier.getName(), amount, modifier.getOperation());

        if (!attributeInstance.hasModifier(attributeModifier)) {
            attributeInstance.addPermanentModifier(attributeModifier);
        }
        EntityStatusUtil.updateDefense((Player) livingEntity, attribute.get());
        EntityStatusUtil.updateResistances((Player) livingEntity, attribute.get());
    }

    protected void removeModifier(LivingEntity livingEntity, Supplier<Attribute> attribute, UUID uuid) {
        AttributeInstance attributeInstance = getAttributeInstance(livingEntity, attribute);
        attributeInstance.removePermanentModifier(uuid);
    }

    public Map<AttributeModifier, List<Supplier<Attribute>>> getAttributeMap() {
        return this.attributes;
    }
}
