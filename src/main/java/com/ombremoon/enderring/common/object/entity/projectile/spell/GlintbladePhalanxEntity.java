package com.ombremoon.enderring.common.object.entity.projectile.spell;

import com.google.common.collect.ImmutableList;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.init.entity.ProjectileInit;
import com.ombremoon.enderring.common.object.spell.sorcery.glintstone.GlintbladePhalanxSorcery;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GlintbladePhalanxEntity extends SpellProjectileEntity<GlintbladePhalanxSorcery> {

    public GlintbladePhalanxEntity(Level pLevel, ScaledWeapon weapon, GlintbladePhalanxSorcery spell) {
        super(ProjectileInit.GLINTSTONE_PHALANX.get(), pLevel, weapon, spell);
    }

    public GlintbladePhalanxEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Path getPath() {
        return Path.HOMING;
    }

    @Override
    public List<Vec3> initPosition(LivingEntity owner) {
        Vec3 vec3 = this.getDefaultSpawnPos(owner);
        return ImmutableList.of(new Vec3(vec3.x + 1, vec3.y, vec3.z), new Vec3(vec3.x - 1, vec3.y, vec3.z));
    }

    @Override
    public List<Vec3> initRotation(LivingEntity owner) {
        LivingEntity target = this.getTargetEntity();
        if (target != null) {
            return ImmutableList.of(this.getTargetVector(target), this.getTargetVector(target));
        }
        return ImmutableList.of(this.getDefaultRotation(owner), this.getDefaultRotation(owner));
    }
}
