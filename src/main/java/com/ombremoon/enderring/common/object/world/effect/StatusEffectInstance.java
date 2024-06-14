package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.common.ScaledWeapon;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class StatusEffectInstance extends MobEffectInstance {
    private final ScaledWeapon weapon;
    private final IncrementalStatusEffect effect;

    public StatusEffectInstance(ScaledWeapon weapon, IncrementalStatusEffect pEffect) {
        this(weapon, pEffect, 0);
    }

    public StatusEffectInstance(ScaledWeapon weapon, IncrementalStatusEffect pEffect, int pDuration) {
        this(weapon, pEffect, pDuration, 0);
    }

    public StatusEffectInstance(ScaledWeapon weapon, IncrementalStatusEffect pEffect, int pDuration, int pAmplifier) {
        this(weapon, pEffect, pDuration, pAmplifier, false, true);
    }

    public StatusEffectInstance(ScaledWeapon weapon, IncrementalStatusEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible) {
        this(weapon, pEffect, pDuration, pAmplifier, pAmbient, pVisible, true);
    }

    public StatusEffectInstance(ScaledWeapon weapon, IncrementalStatusEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon) {
        super(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon);
        this.weapon = weapon;
        this.effect = pEffect;
    }

    public ScaledWeapon getWeapon() {
        return this.weapon;
    }

    @Override
    public void applyEffect(LivingEntity pEntity) {
        if (this.hasRemainingDuration()) {
            this.effect.applyStatusTick(pEntity, this.weapon);
        }
    }

    private boolean hasRemainingDuration() {
        return this.isInfiniteDuration() || this.getDuration() > 0;
    }
}
