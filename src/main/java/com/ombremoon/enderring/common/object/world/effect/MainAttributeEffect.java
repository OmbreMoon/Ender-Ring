package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class MainAttributeEffect extends ModifiedAttributeEffect {

    public MainAttributeEffect(MobEffectCategory pCategory, int pColor, Supplier<Attribute> attribute1, Supplier<Attribute> attribute2, Supplier<Attribute> attribute3, Supplier<Attribute> attribute4, AttributeModifier attributeModifier) {
        super(pCategory, pColor, attribute1, attribute2, attribute3, attribute4, attributeModifier);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, int pAmplifier, double pHealth) {
        boolean flag = pLivingEntity.getHealth() < pLivingEntity.getMaxHealth();
        ModNetworking.updateMainAttributes(!flag);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
        boolean flag = pLivingEntity.getHealth() < pLivingEntity.getMaxHealth();
        ModNetworking.updateMainAttributes(!flag);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
