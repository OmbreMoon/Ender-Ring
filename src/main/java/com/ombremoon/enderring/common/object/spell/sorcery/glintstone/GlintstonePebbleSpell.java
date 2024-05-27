package com.ombremoon.enderring.common.object.spell.sorcery.glintstone;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.*;
import com.ombremoon.enderring.compat.epicfight.gameassets.AnimationInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class GlintstonePebbleSpell extends ProjectileSpell {

//    public GlintstonePebbleSpell() {
//        this(SpellInit.GLINTSTONE_PEBBLE.get(), MagicType.SORCERY, 10, 7, 10, 0, 0);
//    }

    public GlintstonePebbleSpell(ProjectileSpell.Builder builder) {
        this(SpellInit.GLINTSTONE_PEBBLE.get(), builder);
    }

    public GlintstonePebbleSpell(SpellType<?> spellType, ProjectileSpell.Builder builder) {
        super(spellType, builder);
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }

    @Override
    public void tickSpellEffect(ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {

    }

    @Override
    public void onSpellStart(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {
        Constants.LOG.info("Spell: " + this.getSpellName().getString());
//        playerPatch.playAnimationSynchronized(AnimationInit.SPELL_HEAL, 0.0F);
    }

    @Override
    public boolean isEffectTick(int duration) {
        Constants.LOG.info(String.valueOf(duration));
        return super.isEffectTick(duration);
    }
}
