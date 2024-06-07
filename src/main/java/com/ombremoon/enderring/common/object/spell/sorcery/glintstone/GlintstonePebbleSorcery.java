package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.ProjectileSpell;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class GlintstonePebbleSorcery extends ProjectileSpell {

    public static ProjectileSpell.Builder createGlinstonePebbleBuilder() {
        return createProjectileBuilder()
                .setMagicType(MagicType.SORCERY)
                .setDuration(10)
                .setFPCost(7)
                .setRequirements(WeaponScaling.INT, 10);
    }

    public GlintstonePebbleSorcery() {
        this(SpellInit.GLINTSTONE_PEBBLE.get(), createGlinstonePebbleBuilder());
    }

    public GlintstonePebbleSorcery(SpellType<?> spellType, ProjectileSpell.Builder builder) {
        super(spellType, builder);
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }

    @Override
    protected void onSpellTick(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellTick(playerPatch, level, blockPos, weapon);
    }

    @Override
    protected void onSpellStart(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellStart(playerPatch, level, blockPos, weapon);Constants.LOG.info("Spell: " + this.getSpellName().getString());
    }

    @Override
    public boolean shouldTickEffect(int duration) {
        return super.shouldTickEffect(duration);
    }
}
