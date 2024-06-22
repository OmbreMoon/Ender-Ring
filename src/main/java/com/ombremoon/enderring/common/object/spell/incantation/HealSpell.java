package com.ombremoon.enderring.common.object.spell.incantation;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.AnimatedSpell;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public abstract class HealSpell extends AnimatedSpell {

    public HealSpell(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
    }

    @Override
    public void onSpellTick(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellTick(playerPatch, level, blockPos, weapon);
        playerPatch.getOriginal().heal(this.getScaledDamage());
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }
}
