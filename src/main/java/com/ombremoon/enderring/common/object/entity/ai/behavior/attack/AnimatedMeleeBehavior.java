package com.ombremoon.enderring.common.object.entity.ai.behavior.attack;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;

public class AnimatedMeleeBehavior<E extends Mob> extends AnimatedCombatBehavior<E> {
    public AnimatedMeleeBehavior(int delayTicks) {
        super(delayTicks);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return super.checkExtraStartConditions(level, entity) && entity.isWithinMeleeAttackRange(this.target);
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (this.target == null)
            return;

        if (!entity.getSensing().hasLineOfSight(this.target) || !entity.isWithinMeleeAttackRange(this.target))
            return;

        super.doDelayedAction(entity);
    }
}
