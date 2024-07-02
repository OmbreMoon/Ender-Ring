package com.ombremoon.enderring.common.object.world.effect.buildup;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class BloodLossEffect extends BuildUpStatusEffect {
    public BloodLossEffect(int pColor) {
        super((i, j) -> true, pColor);
    }

    @Override
    public void applyStatusTick(LivingEntity pLivingEntity, ScaledWeapon weapon, SpellType<?> spellType) {
        super.applyStatusTick(pLivingEntity, weapon, spellType);
        pLivingEntity.hurt(DamageUtil.moddedDamageSource(pLivingEntity.level(), ModDamageTypes.BLOOD_LOSS), pLivingEntity.getMaxHealth() * 0.15F + 6.7F);

    }

    @Override
    public void applyInstantaneousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity, ScaledWeapon weapon, SpellType<?> spellType, int pAmplifier, double pHealth) {
        pLivingEntity.hurt(DamageUtil.moddedDamageSource(pLivingEntity.level(), ModDamageTypes.BLOOD_LOSS), pLivingEntity.getMaxHealth() * 0.15F + 6.7F);
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }
}
