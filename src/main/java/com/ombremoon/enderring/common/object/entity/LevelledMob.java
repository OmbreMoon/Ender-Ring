package com.ombremoon.enderring.common.object.entity;

import net.minecraft.world.damagesource.DamageSource;

public interface LevelledMob {

    default boolean isImmuneTo(DamageSource damageSource) {
        return false;
    }
}
