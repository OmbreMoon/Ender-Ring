package com.ombremoon.enderring.common.object.world.effect.buildup;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.world.effect.StatusEffect;
import com.ombremoon.enderring.common.object.world.effect.stacking.EffectType;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class BuildUpStatusEffect extends StatusEffect {
    public BuildUpStatusEffect(BiFunction<Integer, Integer, Boolean> applyTick, int pColor) {
        super(EffectType.BUILD_UP, pColor, null, null, applyTick, MobEffectCategory.HARMFUL);
    }

    public void applyStatusTick(LivingEntity pLivingEntity, ScaledWeapon weapon, SpellType<?> spellType) {

    }

    public void applyInstantaneousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, ScaledWeapon weapon, SpellType<?> spellType, int pAmplifier, double pHealth) {

    }
}
