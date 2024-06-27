package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import org.checkerframework.checker.units.qual.A;
import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class ModifiedAttributeEffect extends StatusEffect {
    protected final List<Supplier<Attribute>> attributes;
    protected final AttributeModifier modifier;

    public ModifiedAttributeEffect(MobEffectCategory category, int color, List<Supplier<Attribute>> attributes, AttributeModifier modifier, Map<Integer, String> translations, Map<Integer, Float> tiers) {
        super(category, color, translations, tiers);
        this.attributes = attributes;
        this.modifier = modifier;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        for (Supplier<Attribute> attribute : attributes) {
            addAttributeModifier(pLivingEntity,
                    attribute,
                    modifier.getId(),
                    modifier.getName(),
                    getAttributeModifierValue(pAmplifier, modifier),
                    modifier.getOperation());
        }
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        for (Supplier<Attribute> attribute : attributes) {
            removeModifier(pLivingEntity,
                    attribute,
                    modifier.getId());
        }
    }

    private AttributeInstance getAttributeInstance(LivingEntity livingEntity, Supplier<Attribute> attribute) {
        return livingEntity.getAttributes().getInstance(attribute.get());
    }

    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        if (this.getTier(pAmplifier) == null) return pModifier.getAmount();

        return this.getTier(pAmplifier);
    }

    protected void addAttributeModifier(LivingEntity livingEntity, Supplier<Attribute> attribute, UUID uuid, String name, double amount, AttributeModifier.Operation operation) {
        AttributeInstance attributeInstance = getAttributeInstance(livingEntity, attribute);
        AttributeModifier attributeModifier = new AttributeModifier(uuid, name, amount, operation);
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
}
