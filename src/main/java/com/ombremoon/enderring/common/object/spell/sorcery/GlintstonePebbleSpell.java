package com.ombremoon.enderring.common.object.spell.sorcery;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GlintstonePebbleSpell extends ProjectileSpell {

    public GlintstonePebbleSpell() {
        this(SpellInit.GLINTSTONE_PEBBLE.get(), MagicType.SORCERY, 10, 7, 10, 0, 0);
    }

    public GlintstonePebbleSpell(SpellType<?> spellType, MagicType magicType, int duration, int requiredFP, int requiredInt, int requiredFai, int requiredArc) {
        super(spellType, magicType, duration, requiredFP, requiredInt, requiredFai, requiredArc);
    }

    @Override
    public int getCastTime() {
        return DEFAULT_CAST_TIME;
    }

    @Override
    public void tickSpellEffect(Player player, Level level, BlockPos blockPos) {

    }

    @Override
    public void onSpellStart(SpellInstance spellInstance, Player player, Level level, BlockPos blockPos) {
        Constants.LOG.info("Spell: " + this.getSpellName().getString());
    }

    @Override
    public boolean isEffectTick(int duration) {
        Constants.LOG.info(String.valueOf(duration));
        return super.isEffectTick(duration);
    }
}
