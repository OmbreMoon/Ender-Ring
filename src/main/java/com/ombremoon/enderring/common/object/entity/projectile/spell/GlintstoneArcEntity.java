package com.ombremoon.enderring.common.object.entity.projectile.spell;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class GlintstoneArcEntity extends SpellProjectileEntity implements TraceableEntity {
    public GlintstoneArcEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {

    }
}
