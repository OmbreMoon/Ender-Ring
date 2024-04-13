package com.ombremoon.enderring.common.object.spell.sorcery;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.magic.SpellType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class GlintstonePebbleSpell extends AbstractSorcery {
    public GlintstonePebbleSpell() {
        this(SpellInit.GLINTSTONE_PEBBLE.get(), 10, 7, 10);
    }

    public GlintstonePebbleSpell(SpellType<?> spellType, int duration, int requiredFP, int requiredInt) {
        super(spellType, duration, requiredFP, requiredInt);
    }

    @Override
    public void activateSpellEffect(Player player, Level level, BlockPos blockPos) {

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
