package com.ombremoon.enderring.common.magic;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class SpellInstance {
    private static final int INFINITE_DURATION = -1;
    private final SpellType<?> spellType;
    private int duration;

    public SpellInstance(SpellType<?> spellType) {
        this(spellType, spellType.getSpell().getDuration());
    }

    public SpellInstance(SpellType<?> spellType, int duration) {
        this.spellType = spellType;
        this.duration = duration;
    }

    public boolean updateSpell(SpellInstance spellInstance) {
        return false;
    }

    public boolean isInfiniteDuration() {
        return this.duration == INFINITE_DURATION;
    }

    public int mapDuration(Int2IntFunction pMapper) {
        return !this.isInfiniteDuration() && this.duration != 0 ? pMapper.applyAsInt(this.duration) : this.duration;
    }

    public AbstractSpell getSpell() {
        return this.spellType.getSpell();
    }

    public int getDuration() {
        return this.duration;
    }

    public boolean tickSpellEffect(Player player, Level level, BlockPos blockPos, Runnable onExpired) {
        if (this.hasRemainingDuration()) {
            int i = this.isInfiniteDuration() ? player.tickCount : this.duration;
            if (this.getSpell().isEffectTick(i)) {
                this.activateSpellEffect(player, level, blockPos);
            }

            this.tickDownDuration();
            if (this.duration == 0) {
                onExpired.run();
            }
        }
        return this.hasRemainingDuration();
    }

    private boolean hasRemainingDuration() {
        return this.isInfiniteDuration() || this.duration > 0;
    }

    private void tickDownDuration() {
        this.duration = this.mapDuration(currentDuration -> {
            return currentDuration - 1;
        });
    }

    public void activateSpellEffect(Player player, Level level, BlockPos blockPos) {
        if (this.hasRemainingDuration()) {
            this.getSpell().activateSpellEffect(player, level, blockPos);
        }
    }

    public String getDescriptionId() {
        return this.spellType.getSpell().getDescriptionId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SpellInstance)) {
            return false;
        } else {
            SpellInstance spellInstance = (SpellInstance) obj;
            return this.duration == spellInstance.duration && this.spellType.equals(spellInstance.spellType);
        }
    }
}
