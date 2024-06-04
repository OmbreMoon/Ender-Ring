package com.ombremoon.enderring.common.object.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class ERBoss<T extends ERMonster<T>> extends ERMonster<T> {
    protected ERBoss(EntityType<T> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
}
