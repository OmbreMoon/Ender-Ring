package com.ombremoon.enderring.common.object.entity.ai.behavior.attack;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.function.Function;

public class AnimatedRangedAttack<E extends Mob & RangedAttackMob> extends AnimatedCombatBehavior<E> {
    protected Function<E, Integer> attackIntervalSupplier = entity -> entity.level().getDifficulty() == Difficulty.HARD ? 20 : 40;
    protected float attackRadius;

    public AnimatedRangedAttack(int delayTicks) {
        super(delayTicks);

        attackRadius(16);
    }

    /**
     * Set the radius in blocks that the entity should be able to fire on targets.
     * @param radius The radius, in blocks
     * @return this
     */
    public AnimatedRangedAttack<E> attackRadius(float radius) {
        this.attackRadius = radius * radius;

        return this;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        return super.checkExtraStartConditions(level, entity) && entity.distanceToSqr(this.target) <= this.attackRadius;
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (this.target == null)
            return;

        if (!BrainUtils.canSee(entity, this.target) || entity.distanceToSqr(this.target) > this.attackRadius)
            return;

        entity.performRangedAttack(this.target, 1);
        super.doDelayedAction(entity);

    }
}
