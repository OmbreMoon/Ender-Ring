package com.ombremoon.enderring.common.object.world.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public class ModifiedAttributeEffect extends StatusEffect {
    private final Supplier<Attribute> attribute;
    private final AttributeModifier attributeModifier;

    public ModifiedAttributeEffect(MobEffectCategory pCategory, int pColor, Supplier<Attribute> attribute, AttributeModifier attributeModifier) {
        super(pCategory, pColor);
        this.attribute = attribute;
        this.attributeModifier = attributeModifier;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        addAttributeModifier(pLivingEntity, attribute, attributeModifier.getId(), attributeModifier.getName(), this.getAttributeModifierValue(pAmplifier, attributeModifier), attributeModifier.getOperation());
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        removeModifier(pLivingEntity, this.attribute, this.attributeModifier.getId());
    }

    private AttributeInstance getAttributeInstance(LivingEntity livingEntity, Supplier<Attribute> attribute) {
        return livingEntity.getAttributes().getInstance(attribute.get());
    }

    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        return pModifier.getAmount();
    }

    protected void addAttributeModifier(LivingEntity livingEntity, Supplier<Attribute> attribute, UUID uuid, String name, double amount, AttributeModifier.Operation operation) {
        AttributeInstance attributeInstance = getAttributeInstance(livingEntity, attribute);
        AttributeModifier attributeModifier = new AttributeModifier(uuid, name, amount, operation);
        if (!attributeInstance.hasModifier(attributeModifier)) {
            attributeInstance.addPermanentModifier(attributeModifier);
        }
    }

    protected void removeModifier(LivingEntity livingEntity, Supplier<Attribute> attribute, UUID uuid) {
        AttributeInstance attributeInstance = getAttributeInstance(livingEntity, attribute);
        attributeInstance.removePermanentModifier(uuid);
    }
}
