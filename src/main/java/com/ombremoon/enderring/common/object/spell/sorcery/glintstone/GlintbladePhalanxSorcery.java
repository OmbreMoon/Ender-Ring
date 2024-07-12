package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.Classifications;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.MultiProjectileSpell;
import com.ombremoon.enderring.common.object.entity.projectile.spell.GlintbladePhalanxEntity;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;

public class GlintbladePhalanxSorcery extends MultiProjectileSpell<GlintbladePhalanxSorcery, GlintbladePhalanxEntity> {

    @SuppressWarnings("unchecked")
    public static MultiProjectileSpell.Builder<GlintbladePhalanxSorcery> createGlintbladePhalanxSorcery() {
        return createMultiProjectileBuilder()
                .setClassification(Classifications.CARIAN)
                .setDuration(20)
                .setFPCost(18)
                .setRequirements(WeaponScaling.INT, 22)
                .setMotionValue(0.6F)
                .setLifetime(50)
                .setVelocity(1.8F)
                .setSpeedModifier(0.99F)
                .setInactiveTicks(40)
                .setCount(2);
    }

    public GlintbladePhalanxSorcery() {
        this(SpellInit.GLINTBLADE_PHALANX.get(), GlintbladePhalanxEntity::new, createGlintbladePhalanxSorcery());
    }

    public GlintbladePhalanxSorcery(SpellType<?> spellType, ProjectileFactory<GlintbladePhalanxSorcery, GlintbladePhalanxEntity> factory, Builder<GlintbladePhalanxSorcery> builder) {
        super(spellType, factory, builder);
    }

    @Override
    public boolean isInstantSpell() {
        return false;
    }

    @Override
    protected DamageInstance createDamageInstance() {
        return new DamageInstance(ModDamageTypes.MAGICAL, this.getScaledDamage());
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }
}
