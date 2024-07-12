package com.ombremoon.enderring.common.object.entity.projectile.spell;

import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.init.entity.ProjectileInit;
import com.ombremoon.enderring.common.object.spell.sorcery.glintstone.GlintstonePebbleSorcery;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public class GlintstonePebbleEntity extends SpellProjectileEntity<GlintstonePebbleSorcery> {
    public GlintstonePebbleEntity(Level pLevel, ScaledWeapon weapon, GlintstonePebbleSorcery spell) {
        super(ProjectileInit.GLINTSTONE_PEBBLE.get(), pLevel, weapon, spell);
    }

    public GlintstonePebbleEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Path getPath() {
        return Path.SPLINE;
    }
}
