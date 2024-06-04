package com.ombremoon.enderring.common.object.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class ERMonster<T extends ERMonster<T>> extends ERMob<T> {
    protected ERMonster(EntityType<T> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }
}
