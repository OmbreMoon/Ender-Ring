package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.ProjectileSpell;
import yesman.epicfight.gameasset.Animations;

public class GlintstonePebbleSorcery extends ProjectileSpell {

    public static ProjectileSpell.Builder createGlinstonePebbleBuilder() {
        return createProjectileBuilder()
                .setMagicType(MagicType.SORCERY)
                .setDuration(10)
                .setFPCost(7)
                .setRequirements(WeaponScaling.INT, 10)
                .setAnimation(() -> Animations.BIPED_DEATH);
    }

    public GlintstonePebbleSorcery() {
        this(SpellInit.GLINTSTONE_PEBBLE.get(), createGlinstonePebbleBuilder());
    }

    public GlintstonePebbleSorcery(SpellType<?> spellType, ProjectileSpell.Builder<GlintstonePebbleSorcery> builder) {
        super(spellType, builder);
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }

}
