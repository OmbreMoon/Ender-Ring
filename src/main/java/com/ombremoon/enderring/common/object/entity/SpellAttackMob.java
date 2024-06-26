package com.ombremoon.enderring.common.object.entity;

import net.minecraft.world.entity.LivingEntity;

public interface SpellAttackMob {
    /**
     * Attack the specified entity using a spell attack.
     */
    void performSpellAttack(LivingEntity pTarget);
}
