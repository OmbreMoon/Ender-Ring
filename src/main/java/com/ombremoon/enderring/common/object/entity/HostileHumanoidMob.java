package com.ombremoon.enderring.common.object.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class HostileHumanoidMob<T extends HostileHumanoidMob<T>> extends HumanoidMob<T> {
    protected HostileHumanoidMob(EntityType<? extends HumanoidMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return true;
    }
}
