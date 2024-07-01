package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.object.world.effect.stacking.EffectType;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class IncrementalStatusEffect extends StatusEffect {
    public IncrementalStatusEffect(int pColor) {
        super(EffectType.BUILD_UP, pColor, null, null, null, MobEffectCategory.HARMFUL);
    }

    public void applyStatusTick(LivingEntity pLivingEntity, ScaledWeapon weapon) {

    }
}
