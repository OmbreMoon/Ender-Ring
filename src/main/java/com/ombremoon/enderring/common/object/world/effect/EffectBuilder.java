package com.ombremoon.enderring.common.object.world.effect;

import com.mojang.logging.LogUtils;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.world.effect.stacking.EffectType;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class EffectBuilder {
    private final Logger log = LogUtils.getLogger();

    private MobEffectCategory category;
    private EffectType type;
    private int color;
    private Map<String, Map<Integer, Double>> tiers;
    private Map<Integer, String> translations;
    private Map<AttributeModifier, List<Supplier<Attribute>>> attributes;
    private BiFunction<Integer, Integer, Boolean> applyTick;

    public EffectBuilder(EffectType type) {
        this.category = MobEffectCategory.BENEFICIAL;
        this.color = 234227227;
        this.applyTick = (a, b) -> true;
    }

    public EffectBuilder addTier(String uuid, int tier, double modifier) {
        if (this.tiers == null) this.tiers = new HashMap<>();
        if (this.tiers.get(uuid) == null) {
            Map<Integer, Double> tierMap = new HashMap<>();
            tierMap.put(tier, modifier);
            this.tiers.put(uuid, tierMap);
        } else this.tiers.get(uuid).put(tier, modifier);
        return this;
    }

    public EffectBuilder setApplyTick(BiFunction<Integer, Integer, Boolean> apply) {
        if (this.attributes != null) LogUtils.getLogger().warn("Can not set apply ticks on an modified attribute effect.");
        this.applyTick = apply;
        return this;
    }

    public EffectBuilder setCategory(MobEffectCategory category) {
        this.category = category;
        return this;
    }

    @SafeVarargs
    public final EffectBuilder addAttribute(AttributeModifier modifier, @NotNull Supplier<Attribute>... attributesArr) {
        if (attributesArr.length == 0) throw new RuntimeException("Attributes must be specified in the effect builder.");
        if (this.attributes == null) this.attributes = new HashMap<>();
        List<Supplier<Attribute>> attributeList = new ArrayList<>(Arrays.asList(attributesArr));
        this.attributes.put(modifier, attributeList);
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
        if (this.attributes == null) {
            return new StatusEffect(type, color, translations, tiers, applyTick, category);
        } else {
            for (List<Supplier<Attribute>> attributesList : this.attributes.values()) {
                if (attributesList.contains(EntityAttributeInit.VIGOR)
                    || attributesList.contains(EntityAttributeInit.ENDURANCE)
                    || attributesList.contains(EntityAttributeInit.MIND))
                    return new MainAttributeEffect(category, color, attributes, translations, tiers, type);
            }
            return new ModifiedAttributeEffect(category, color, attributes, translations, tiers, type);
        }
    }

    public HPEffect buildHpEffect(boolean maxHpEffect) {
        return new HPEffect(category, color, attributes, translations, tiers, maxHpEffect, type);
    }

}
