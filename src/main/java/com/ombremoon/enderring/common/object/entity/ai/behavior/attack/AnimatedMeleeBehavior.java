package com.ombremoon.enderring.common.object.entity.ai.behavior.attack;

import net.minecraft.world.entity.Mob;

public class AnimatedMeleeBehavior<E extends Mob> extends AnimatedCombatBehavior<E> {

    public AnimatedMeleeBehavior() {
        this(1);
    }

    public AnimatedMeleeBehavior(int delayTicks) {
        super(delayTicks);
        attackRange(3);
        runFor(entity -> Math.max(delayTicks, 40));
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (this.target == null)
            return;

        super.doDelayedAction(entity);
    }
}
