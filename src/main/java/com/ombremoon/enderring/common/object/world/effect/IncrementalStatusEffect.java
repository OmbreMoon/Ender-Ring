package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.common.ScaledWeapon;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class IncrementalStatusEffect extends StatusEffect {
    public IncrementalStatusEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public void applyStatusTick(LivingEntity pLivingEntity, ScaledWeapon weapon) {

    }
}
