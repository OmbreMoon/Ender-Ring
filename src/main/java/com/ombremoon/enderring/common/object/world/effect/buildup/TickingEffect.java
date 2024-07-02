package com.ombremoon.enderring.common.object.world.effect.buildup;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

public class TickingEffect extends BuildUpStatusEffect {
    public TickingEffect(int pColor) {
        super((a, b) -> a % 20 == 0, pColor);
    }

    @Override
    public void applyStatusTick(LivingEntity pLivingEntity, ScaledWeapon weapon, SpellType<?> spellType) {
        float f = 0;
        ResourceKey<DamageType> damageType = null;
        /*if (this == StatusEffectInit.POISON.get()) {
            damageType = ModDamageTypes.POISON;
            if (weapon != null) {
                if (weapon.getBaseStats().getItem(Constants.MOD_ID).is(EquipmentInit.GUARDIAN_SWORDSPEAR.get())) {
                    Constants.LOG.info("Womp womp");
                } else {
                    f = pLivingEntity.getMaxHealth() * 0.07F + 0.5F;
                }
            }
        } else if (this == StatusEffectInit.SCARLET_ROT.get()) {
            damageType = ModDamageTypes.SCARLET_ROT;
            if (weapon != null) {
                if (weapon.getBaseStats().getItem(Constants.MOD_ID).is(EquipmentInit.GUARDIAN_SWORDSPEAR.get())) {
                    Constants.LOG.info("Pomp Pomp");
                } else {
                    f = pLivingEntity.getMaxHealth() * 0.18F + 1.0F;
                }
            }
        }*/
        pLivingEntity.hurt(DamageUtil.moddedDamageSource(pLivingEntity.level(), damageType), f);
    }
}
