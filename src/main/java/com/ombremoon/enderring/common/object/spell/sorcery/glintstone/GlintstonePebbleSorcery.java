package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class GlintstonePebbleSorcery extends ProjectileSpell {
    private boolean inWater;

    public static ProjectileSpell.Builder createGlinstonePebbleBuilder() {
        return createProjectileBuilder()
                .setMagicType(MagicType.SORCERY)
                .setDuration(10)
                .setFPCost(7)
                .setRequirements(WeaponScaling.INT, 10);
    }

    public GlintstonePebbleSorcery(ProjectileSpell.Builder builder) {
        this(SpellInit.GLINTSTONE_PEBBLE.get(), builder);
    }

    public GlintstonePebbleSorcery(SpellType<?> spellType, ProjectileSpell.Builder builder) {
        super(spellType, builder);
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }

    @Override
    public void tickSpellEffect(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {
        if (!this.inWater) {
            Constants.LOG.info(String.valueOf(spellInstance.getDuration()));
        }
    }

    @Override
    public void onSpellStart(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {
        Constants.LOG.info("Spell: " + this.getSpellName().getString());
        if (playerPatch.getOriginal().isInWater()) {
            this.setInWater(true);
        }
    }

    public void setInWater(boolean inWater) {
        this.inWater = inWater;
    }

    @Override
    public boolean isEffectTick(int duration) {
        return super.isEffectTick(duration);
    }
}
