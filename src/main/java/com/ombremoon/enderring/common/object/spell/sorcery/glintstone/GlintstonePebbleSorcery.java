package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.Classifications;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.ProjectileSpell;
import com.ombremoon.enderring.common.object.entity.projectile.spell.GlintstonePebbleEntity;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import yesman.epicfight.gameasset.Animations;

public class GlintstonePebbleSorcery extends ProjectileSpell<GlintstonePebbleSorcery, GlintstonePebbleEntity> {

    @SuppressWarnings("unchecked")
    public static ProjectileSpell.Builder<GlintstonePebbleSorcery> createGlinstonePebbleBuilder() {
        return createProjectileBuilder()
                .setClassification(Classifications.GLINTSTONE)
                .setDuration(10)
                .setFPCost(7)
                .setRequirements(WeaponScaling.INT, 10)
                .setMotionValue(1.52F)
                .setAnimation(() -> Animations.BIPED_DEATH);
    }

    public GlintstonePebbleSorcery() {
        this(SpellInit.GLINTSTONE_PEBBLE.get(), GlintstonePebbleEntity::new, createGlinstonePebbleBuilder());
    }

    public GlintstonePebbleSorcery(SpellType<?> spellType, ProjectileFactory<GlintstonePebbleSorcery, GlintstonePebbleEntity> factory, ProjectileSpell.Builder<GlintstonePebbleSorcery> builder) {
        super(spellType, factory, builder);
    }

    @Override
    public DamageInstance createDamageInstance() {
        return new DamageInstance(ModDamageTypes.MAGICAL, this.getScaledDamage());
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }

}
