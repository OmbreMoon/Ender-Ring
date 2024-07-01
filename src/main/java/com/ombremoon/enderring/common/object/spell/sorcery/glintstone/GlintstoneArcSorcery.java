package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.Classifications;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.ProjectileSpell;
import com.ombremoon.enderring.common.object.entity.projectile.spell.GlintstoneArcEntity;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import yesman.epicfight.gameasset.Animations;

public class GlintstoneArcSorcery extends ProjectileSpell<GlintstoneArcSorcery, GlintstoneArcEntity> {

    @SuppressWarnings("unchecked")
    public static ProjectileSpell.Builder<GlintstoneArcSorcery> createGlintstoneArcSorcery() {
        return createProjectileBuilder()
                .setClassification(Classifications.GLINTSTONE)
                .setDuration(20)
                .setFPCost(10)
                .setRequirements(WeaponScaling.INT, 13)
                .setMotionValue(1.48F)
                .setChargedMotionValue(1.52F)
                .shootFromCatalyst()
                .canCharge()
                .setLifetime(140)
                .setVelocity(1.15F)
//                .setGravity(0.05F)
                .setSpeedModifier(0.99F)
                .canClip()
                .setInactiveTicks(100);
    }

    public GlintstoneArcSorcery() {
        this(SpellInit.GLINTSTONE_ARC.get(), GlintstoneArcEntity::new, createGlintstoneArcSorcery());
    }

    public GlintstoneArcSorcery(SpellType<?> spellType, ProjectileFactory<GlintstoneArcSorcery, GlintstoneArcEntity> factory, ProjectileSpell.Builder<GlintstoneArcSorcery> builder) {
        super(spellType, factory, builder);
    }

    @Override
    protected DamageInstance createDamageInstance() {
        return new DamageInstance(ModDamageTypes.MAGICAL, this.getScaledDamage());
    }

    @Override
    public int getCastTime() {
        return 32;
    }
}
