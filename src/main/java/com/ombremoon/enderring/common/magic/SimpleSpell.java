package com.ombremoon.enderring.common.magic;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class SimpleSpell extends AbstractSpell {
    private final Consumer<Player> spellConsumer;

    public SimpleSpell(MagicType magicType, int duration, int requiredFP, int requiredInt, int requiredFai, int requiredArc, Consumer<Player> spellConsumer) {
        this(null, magicType, duration, requiredFP, requiredInt, requiredFai, requiredArc, spellConsumer);
    }

    public SimpleSpell(SpellType<?> spellType, MagicType magicType, int duration, int requiredFP, int requiredInt, int requiredFai, int requiredArc, Consumer<Player> spellConsumer) {
        super(spellType, magicType, duration, requiredFP, requiredInt, requiredFai, requiredArc);
        this.spellConsumer = spellConsumer;
    }

    @Override
    public void tickSpellEffect(Player player, Level level, BlockPos blockPos) {

    }

    @Override
    public void onSpellStart(SpellInstance spellInstance, Player player, Level level, BlockPos blockPos) {
        this.spellConsumer.accept(player);
    }

    public SimpleSpell setCastTime(int castTime) {
        this.castTime = castTime;
        return this;
    }
}
