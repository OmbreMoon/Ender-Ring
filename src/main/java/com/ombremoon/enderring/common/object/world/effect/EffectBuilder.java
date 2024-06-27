package com.ombremoon.enderring.common.object.world.effect;

import com.mojang.logging.LogUtils;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Supplier;

public class EffectBuilder {
    private final Logger log = LogUtils.getLogger();

    private MobEffectCategory category;
    private int color;
    private Map<Integer, Float> tiers;
    private List<Supplier<Attribute>> attributes;
    private AttributeModifier modifier;
    private Map<Integer, String> translations;

    public EffectBuilder(MobEffectCategory category) {
        this.category = category;
        this.color = 234227227;
    }

    public EffectBuilder addTier(int tier, float modifier) {
        if (this.tiers == null) this.tiers = new HashMap<>();
        this.tiers.put(tier, modifier);
        return this;
    }

    public EffectBuilder singleAttribute(Supplier<Attribute> attribute, String uuid, String modifierName, float amount, AttributeModifier.Operation operation) {
        this.attributes = new ArrayList<>();
        this.attributes.add(attribute);
        this.modifier = new AttributeModifier(UUID.fromString(uuid), modifierName, amount, operation);
        return this;
    }

    public EffectBuilder addAttribute(Supplier<Attribute> attribute) {
        if (this.attributes == null) this.attributes = new ArrayList<>();
        this.attributes.add(attribute);
        return this;
    }

    public EffectBuilder setModifier(AttributeModifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public EffectBuilder setColor(int color) {
        this.color = color;
        return this;
    }

    public EffectBuilder addTranslation(int amp, String translation) {
        if (this.translations == null) this.translations = new HashMap<>();
        this.translations.put(amp, translation);
        return this;
    }

    public StatusEffect build() {
        if (this.attributes != null && modifier == null) {
            log.error("Can not modify attributes without a modifier.");
            return new StatusEffect(category, color, translations, tiers);
        } else if (this.attributes == null && modifier != null) {
            log.error("Can not set a modifier without adding attributes to modify.");
            return new StatusEffect(category, color, translations, tiers);
        } else if (this.attributes == null) {
            return new StatusEffect(category, color, translations, tiers);
        } else if (this.attributes.contains(EntityAttributeInit.VIGOR)
                || this.attributes.contains(EntityAttributeInit.ENDURANCE)
                || this.attributes.contains(EntityAttributeInit.MIND)) {
            return new MainAttributeEffect(category, color, attributes, modifier, translations, tiers);
        } else {
            return new ModifiedAttributeEffect(category, color, attributes, modifier, translations, tiers);
        }
    }

    public HPTalisman buildHpTalisman(boolean maxHpEffect) {
        return new HPTalisman(category, color, attributes, modifier, translations, tiers, maxHpEffect);
    }

}
