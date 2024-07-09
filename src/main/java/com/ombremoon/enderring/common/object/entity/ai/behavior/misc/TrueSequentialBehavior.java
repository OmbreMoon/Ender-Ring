/*
package com.ombremoon.enderring.common.object.entity.ai.behavior.misc;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.GroupBehaviour;
import net.tslat.smartbrainlib.object.SBLShufflingList;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class TrueSequentialBehavior<E extends LivingEntity> extends GroupBehaviour<E> {
    private Predicate<ExtendedBehaviour<? super E>> earlyResetPredicate = behaviour -> false;
    private int runningIndex = 0;

    public TrueSequentialBehavior(Pair<ExtendedBehaviour<? super E>, Integer>... behaviours) {
        super(behaviours);
    }

    public TrueSequentialBehavior(ExtendedBehaviour<? super E>... behaviours) {
        super(behaviours);
    }


    @Override
    protected boolean shouldKeepRunning(E entity) {
        return this.runningBehaviour != null && this.runningBehaviour.getStatus() != Status.STOPPED;
    }

    @Override
    protected boolean timedOut(long gameTime) {
        return this.runningBehaviour == null || (this.runningBehaviour.timedOut(gameTime) && this.runningIndex >= this.behaviours.size());
    }

    @Override
    protected void tick(ServerLevel level, E owner, long gameTime) {
        this.runningBehaviour.tickOrStop(level, owner, gameTime);

        if (this.runningBehaviour.getStatus() == Status.STOPPED) {
            if (pickBehaviour(level, owner, gameTime, this.behaviours) != null)
                return;

            doStop(level, owner, gameTime);
        }
    }

    @Nullable
    @Override
    protected ExtendedBehaviour<? super E> pickBehaviour(ServerLevel level, E entity, long gameTime, SBLShufflingList<ExtendedBehaviour<? super E>> extendedBehaviours) {
        if (this.runningIndex >= extendedBehaviours.size())
            return null;

        ExtendedBehaviour<? super E> first = extendedBehaviours.get(this.runningIndex);

        if (first != null && first.tryStart(level, entity, gameTime)) {
            this.runningBehaviour = first;
            this.runningIndex++;

            return this.runningBehaviour;
        }

        return null;
    }

    @Override
    protected void stop(ServerLevel level, E entity, long gameTime) {
        super.stop(level, entity, gameTime);

        this.runningIndex = 0;
    }
}
*/
