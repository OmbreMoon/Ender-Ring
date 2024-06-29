package com.ombremoon.enderring.common.object.entity.ai.behavior.misc;

import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.GroupBehaviour;
import net.tslat.smartbrainlib.object.SBLShufflingList;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Group behaviour that runs all child behaviours in order, one after another.<br>
 * Unlike SequentialBehavior, this will also run all child behaviours of any group behavior. <br>
 * Restarts from the first behaviour upon reaching the end of the list
 * @param <E> The entity
 */
public final class TrueSequentialBehaviour<E extends LivingEntity> extends GroupBehaviour<E> {
    private Predicate<ExtendedBehaviour<? super E>> earlyResetPredicate = behaviour -> false;
    private ExtendedBehaviour<? super E> lastRun = null;
    private ExtendedBehaviour<? super E> lastRunInGroup = null;

    public TrueSequentialBehaviour(Pair<ExtendedBehaviour<? super E>, Integer>... behaviours) {
        super(behaviours);
    }

    public TrueSequentialBehaviour(ExtendedBehaviour<? super E>... behaviours) {
        super(behaviours);
    }

    /**
     * Adds an early short-circuit predicate to reset back to the start of the child behaviours at any time
     */
    public TrueSequentialBehaviour<E> resetIf(Predicate<ExtendedBehaviour<? super E>> predicate) {
        this.earlyResetPredicate = predicate;
        return this;
    }

    @Nullable
    @Override
    protected ExtendedBehaviour<? super E> pickBehaviour(ServerLevel level, E entity, long gameTime, SBLShufflingList<ExtendedBehaviour<? super E>> extendedBehaviours) {
        boolean pickNext = this.lastRun == null;
        boolean pickNextInGroup = this.lastRunInGroup == null;

        if (this.lastRun != null && this.earlyResetPredicate.test(this.lastRun)) {
            pickNext = true;
            this.lastRun = null;
        }

        if (this.lastRunInGroup != null && this.earlyResetPredicate.test(this.lastRunInGroup)) {
            pickNextInGroup = true;
            this.lastRunInGroup = null;
        }

        for (ExtendedBehaviour<? super E> behaviour : extendedBehaviours) {
            if (pickNext) {
                if (behaviour instanceof GroupBehaviour<? super E> groupBehaviour) {
                    List<ExtendedBehaviour<? super E>> list = new ArrayList<>();
                    groupBehaviour.getBehaviours().forEachRemaining(list::add);
                    for (ExtendedBehaviour<? super E> behaviours : list) {
                        if (pickNextInGroup) {
                            if (behaviours.tryStart(level, entity, gameTime)) {
                                this.lastRunInGroup = behaviours;

                                return behaviours;
                            }

                            return null;
                        }

                        if  (behaviours == this.lastRunInGroup)
                            pickNextInGroup = true;
                    }

                    this.lastRunInGroup = null;
                    this.lastRun = behaviour;
                } else if (behaviour.tryStart(level, entity, gameTime)) {
                    this.lastRun = behaviour;

                    return behaviour;
                }

                return null;
            }

            if (behaviour == this.lastRun)
                pickNext = true;
        }

        this.lastRun = null;

        return null;
    }
}
