package com.ombremoon.enderring.common.object.entity.projectile.spell;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.init.entity.ProjectileInit;
import com.ombremoon.enderring.common.object.entity.projectile.PathConsumer;
import com.ombremoon.enderring.common.object.spell.sorcery.glintstone.GlintstoneArcSorcery;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class GlintstoneArcEntity extends SpellProjectileEntity<GlintstoneArcSorcery> {

    public GlintstoneArcEntity(Level pLevel, ScaledWeapon weapon, GlintstoneArcSorcery spell) {
        super(ProjectileInit.GLINTSTONE_ARC.get(), pLevel, weapon, spell);
    }

    public GlintstoneArcEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected boolean stopOnFirstEnemyHit() {
        return false;
    }

    @Override
    public Path getPath() {
        return Path.STRAIGHT;
    }
}
