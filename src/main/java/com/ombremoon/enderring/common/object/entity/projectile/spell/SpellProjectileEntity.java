package com.ombremoon.enderring.common.object.entity.projectile.spell;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public abstract class SpellProjectileEntity extends Projectile implements TraceableEntity {
    protected SpellProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    enum Path {
        STRAIGHT,
        SPLINE
    }
}
