package com.ombremoon.enderring.common.object.entity.ai.behavior.misc;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.Constants;
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

    @SafeVarargs
    public TrueSequentialBehavior(Pair<ExtendedBehaviour<? super E>, Integer>... behaviours) {
        super(behaviours);
    }

    @SafeVarargs
    public TrueSequentialBehavior(ExtendedBehaviour<? super E>... behaviours) {
        super(behaviours);
    }


    @Override
    protected boolean shouldKeepRunning(E entity) {
        return this.runningBehaviour != null && this.runningBehaviour.getStatus() != Status.STOPPED;
    }

    @Override
    protected boolean timedOut(long gameTime) {
        if (this.runningBehaviour != null) {
            return super.timedOut(gameTime) && this.runningIndex >= this.behaviours.size();
        }
        return super.timedOut(gameTime);
    }

    @Override
    protected void tick(ServerLevel level, E owner, long gameTime) {
        this.runningBehaviour.tickOrStop(level, owner, gameTime);

        if (this.runningBehaviour.getStatus() == Status.STOPPED) {
//            Constants.LOG.info(String.valueOf(this.runningIndex));
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

        if (first != null && this.runningIndex == 0) {
//            Constants.LOG.info(String.valueOf(first.tryStart(level, entity, gameTime)));
        }
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
