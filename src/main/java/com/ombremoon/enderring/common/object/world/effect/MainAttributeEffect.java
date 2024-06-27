package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class MainAttributeEffect extends ModifiedAttributeEffect {

    public MainAttributeEffect(MobEffectCategory category, int color, Map<AttributeModifier, List<Supplier<Attribute>>> attributes, @Nullable Map<Integer, String> translations, Map<String, Map<Integer, Double>> tiers) {
        super(category, color, attributes, translations, tiers);
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
