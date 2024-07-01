package com.ombremoon.enderring.event.custom;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.Event;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class SpellEvent extends Event {
    private final AbstractSpell abstractSpell;
    private final LivingEntityPatch<?> livingEntityPatch;
    private final Level level;
    private final BlockPos blockPos;
    private final ScaledWeapon scaledWeapon;

    public SpellEvent(AbstractSpell abstractSpell, LivingEntityPatch<?> livingEntityPatch, Level level, BlockPos blockPos, ScaledWeapon scaledWeapon) {
        this.abstractSpell = abstractSpell;
        this.livingEntityPatch = livingEntityPatch;
        this.level = level;
        this.blockPos = blockPos;
        this.scaledWeapon = scaledWeapon;
    }

    public AbstractSpell getAbstractSpell() {
        return this.abstractSpell;
    }

    public LivingEntityPatch<?> getLivingEntityPatch() {
        return this.livingEntityPatch;
    }

    public Level getLevel() {
        return this.level;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public ScaledWeapon getScaledWeapon() {
        return this.scaledWeapon;
    }

    public static class Start extends SpellEvent {

        public Start(AbstractSpell abstractSpell, LivingEntityPatch<?> livingEntityPatch, Level level, BlockPos blockPos, ScaledWeapon scaledWeapon) {
            super(abstractSpell, livingEntityPatch, level, blockPos, scaledWeapon);
        }
    }

    public static class Tick extends SpellEvent {
        private final int ticks;

        public Tick(AbstractSpell abstractSpell, LivingEntityPatch<?> livingEntityPatch, Level level, BlockPos blockPos, ScaledWeapon scaledWeapon, int ticks) {
            super(abstractSpell, livingEntityPatch, level, blockPos, scaledWeapon);
            this.ticks = ticks;
        }

        public int getSpellTick() {
            return this.ticks;
        }
    }

    public static class Stop extends SpellEvent {

        public Stop(AbstractSpell abstractSpell, LivingEntityPatch<?> livingEntityPatch, Level level, BlockPos blockPos, ScaledWeapon scaledWeapon) {
            super(abstractSpell, livingEntityPatch, level, blockPos, scaledWeapon);
        }
    }
}
