package com.ombremoon.enderring.common.object.spell.incantation;

import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.magic.SimpleAnimationSpell;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.magic.SpellType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;


//TODO: FIX CONFIG
public abstract class HealSpell extends SimpleAnimationSpell {

    public HealSpell(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
    }

    @Override
    public void tickSpellEffect(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {
        float incantScaling = spellInstance.getMagicScaling();
        playerPatch.getOriginal().heal(incantScaling * this.motionValue / ConfigHandler.STAT_SCALE.get());
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }
}
