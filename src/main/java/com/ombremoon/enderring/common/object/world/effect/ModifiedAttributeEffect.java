package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;
import java.util.function.Supplier;

public class ModifiedAttributeEffect extends StatusEffect {
    private final Supplier<Attribute> attribute1;
    private final Supplier<Attribute> attribute2;
    private final Supplier<Attribute> attribute3;
    private final Supplier<Attribute> attribute4;
    private final AttributeModifier attributeModifier;

    public ModifiedAttributeEffect(MobEffectCategory pCategory, int pColor, Supplier<Attribute> attribute1, Supplier<Attribute> attribute2, Supplier<Attribute> attribute3, Supplier<Attribute> attribute4, AttributeModifier attributeModifier) {
        super(pCategory, pColor);
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
        this.attributeModifier = attributeModifier;
    }

    public ModifiedAttributeEffect(MobEffectCategory pCategory, int pColor, Supplier<Attribute> attribute1, Supplier<Attribute> attribute2, Supplier<Attribute> attribute3, AttributeModifier attributeModifier) {
        this(pCategory, pColor, attribute1, attribute2, attribute3, null, attributeModifier);
    }

    public ModifiedAttributeEffect(MobEffectCategory pCategory, int pColor, Supplier<Attribute> attribute1, Supplier<Attribute> attribute2, AttributeModifier attributeModifier) {
        this(pCategory, pColor, attribute1, attribute2, null, null, attributeModifier);
    }

    public ModifiedAttributeEffect(MobEffectCategory pCategory, int pColor, Supplier<Attribute> attribute1, AttributeModifier attributeModifier) {
        this(pCategory, pColor, attribute1, null, null, null, attributeModifier);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        addAttributeModifier(pLivingEntity, attribute1, attributeModifier.getId(), attributeModifier.getName(), this.getAttributeModifierValue(pAmplifier, attributeModifier), attributeModifier.getOperation());
        if (attribute2.get() != null) addAttributeModifier(pLivingEntity, attribute2, attributeModifier.getId(), attributeModifier.getName(), this.getAttributeModifierValue(pAmplifier, attributeModifier), attributeModifier.getOperation());
        if (attribute3.get() != null) addAttributeModifier(pLivingEntity, attribute3, attributeModifier.getId(), attributeModifier.getName(), this.getAttributeModifierValue(pAmplifier, attributeModifier), attributeModifier.getOperation());
        if (attribute4.get() != null) addAttributeModifier(pLivingEntity, attribute4, attributeModifier.getId(), attributeModifier.getName(), this.getAttributeModifierValue(pAmplifier, attributeModifier), attributeModifier.getOperation());
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        removeModifier(pLivingEntity, this.attribute1, this.attributeModifier.getId());
        if (attribute2.get() != null) removeModifier(pLivingEntity, this.attribute2, this.attributeModifier.getId());
        if (attribute3.get() != null) removeModifier(pLivingEntity, this.attribute3, this.attributeModifier.getId());
        if (attribute4.get() != null) removeModifier(pLivingEntity, this.attribute4, this.attributeModifier.getId());
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
        EntityStatusUtil.updateDefense((Player) livingEntity, attribute.get());
        EntityStatusUtil.updateResistances((Player) livingEntity, attribute.get());
    }

    protected void removeModifier(LivingEntity livingEntity, Supplier<Attribute> attribute, UUID uuid) {
        AttributeInstance attributeInstance = getAttributeInstance(livingEntity, attribute);
        attributeInstance.removePermanentModifier(uuid);
    }
}
