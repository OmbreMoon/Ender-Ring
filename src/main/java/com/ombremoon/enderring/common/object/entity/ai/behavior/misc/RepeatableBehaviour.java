package com.ombremoon.enderring.common.object.entity.ai.behavior.misc;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.GroupBehaviour;
import net.tslat.smartbrainlib.object.SBLShufflingList;
import net.tslat.smartbrainlib.util.RandomUtil;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class RepeatableBehaviour<E extends LivingEntity> extends GroupBehaviour<E> {
    protected Predicate<ExtendedBehaviour<? super E>> repeatPredicate = extendedBehaviour -> false;
    private int repeatCount = 0;
    private int maxRepeat = 0;

    public RepeatableBehaviour(Pair<ExtendedBehaviour<? super E>, Integer>... behaviours) {
        super(behaviours);
    }

    public RepeatableBehaviour(ExtendedBehaviour<? super E>... behaviours) {
        super(behaviours);
    }

    /**
     * Adds a callback predicate to repeat the behaviour
     */
    public RepeatableBehaviour<E> repeatIf(Predicate<ExtendedBehaviour<? super E>> predicate) {
        this.repeatPredicate = predicate;

        return this;
    }

    /**
     * Sets the amount of times the behaviour should repeat
     */
    public RepeatableBehaviour<E> repeat(int maxRepeat) {
        this.maxRepeat = maxRepeat;

        return this;
    }

    /**
     * Sets the amount of times the behaviour should repeat between two numbers
     */
    public RepeatableBehaviour<E> repeat(int minRepeat, int maxRepeat) {
        return repeat(RandomUtil.randomNumberBetween(minRepeat, maxRepeat));
    }

    @Override
    protected @Nullable ExtendedBehaviour<? super E> pickBehaviour(ServerLevel level, E entity, long gameTime, SBLShufflingList<ExtendedBehaviour<? super E>> extendedBehaviours) {
        for (ExtendedBehaviour<? super E> behaviour : extendedBehaviours) {
            if (behaviour.tryStart(level, entity, gameTime)) {
                if (this.repeatCount <= this.maxRepeat) {
                    this.repeatCount++;
                    return behaviour;
                }
                return null;
            }
            return null;
        }

        return null;
    }
}
