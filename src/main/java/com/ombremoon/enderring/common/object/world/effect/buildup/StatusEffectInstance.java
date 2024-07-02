package com.ombremoon.enderring.common.object.world.effect.buildup;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.world.effect.buildup.BuildUpStatusEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class StatusEffectInstance extends MobEffectInstance {
    private final ScaledWeapon weapon;
    private final SpellType<?> spellType;
    private final BuildUpStatusEffect effect;

    public StatusEffectInstance(SpellType<?> spellType, BuildUpStatusEffect pEffect) {
        this(null, spellType, pEffect, 0);
    }

    public StatusEffectInstance(ScaledWeapon weapon, BuildUpStatusEffect pEffect) {
        this(weapon, null, pEffect, 0);
    }

    public StatusEffectInstance(ScaledWeapon weapon, SpellType<?> spellType, BuildUpStatusEffect pEffect) {
        this(weapon, spellType, pEffect, 1);
    }

    public StatusEffectInstance(ScaledWeapon weapon, SpellType<?> spellType, BuildUpStatusEffect pEffect, int pDuration) {
        this(weapon, spellType, pEffect, pDuration, 0);
    }

    public StatusEffectInstance(ScaledWeapon weapon, SpellType<?> spellType, BuildUpStatusEffect pEffect, int pDuration, int pAmplifier) {
        this(weapon, spellType, pEffect, pDuration, pAmplifier, false, true);
    }

    public StatusEffectInstance(ScaledWeapon weapon, SpellType<?> spellType, BuildUpStatusEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible) {
        this(weapon, spellType, pEffect, pDuration, pAmplifier, pAmbient, pVisible, true);
    }

    public StatusEffectInstance(ScaledWeapon weapon, SpellType<?> spellType, BuildUpStatusEffect pEffect, int pDuration, int pAmplifier, boolean pAmbient, boolean pVisible, boolean pShowIcon) {
        super(pEffect, pDuration, pAmplifier, pAmbient, pVisible, pShowIcon);
        this.weapon = weapon;
        this.spellType = spellType;
        this.effect = pEffect;
    }

    public ScaledWeapon getWeapon() {
        return this.weapon;
    }

    public SpellType<?> getSpellType() {
        return this.spellType;
    }

    @Override
    public void applyEffect(LivingEntity pEntity) {
        if (this.hasRemainingDuration()) {
            this.effect.applyStatusTick(pEntity, this.weapon, this.spellType);
        }
    }

    private boolean hasRemainingDuration() {
        return this.isInfiniteDuration() || this.getDuration() > 0;
    }
}
