package com.ombremoon.enderring.common.object.world.effect.talisman;

import com.ombremoon.enderring.common.object.world.effect.MainAttributeEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.HashMap;
import java.util.function.Supplier;

public class MarikasScarsealEffect extends MainAttributeEffect {
    public static final String MARIKAS_SORESEAL = "item.enderring.marikas_scarsealplus1";

    public MarikasScarsealEffect(MobEffectCategory pCategory, int pColor, Supplier<Attribute> attribute1, Supplier<Attribute> attribute2, Supplier<Attribute> attribute3, Supplier<Attribute> attribute4, AttributeModifier attributeModifier) {
        super(pCategory, pColor, attribute1, attribute2, attribute3, attribute4, attributeModifier);
        this.translationKeys = new HashMap<>();
        this.translationKeys.put(0, getDescriptionId());
        this.translationKeys.put(1, MARIKAS_SORESEAL);
    }
}
