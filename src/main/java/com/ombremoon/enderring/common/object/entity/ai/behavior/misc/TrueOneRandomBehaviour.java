package com.ombremoon.enderring.common.object.entity.ai.behavior.misc;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.Constants;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.GroupBehaviour;
import net.tslat.smartbrainlib.object.SBLShufflingList;
import org.jetbrains.annotations.Nullable;

public class TrueOneRandomBehaviour<E extends LivingEntity> extends ContinuousBehaviour<E> {
    public TrueOneRandomBehaviour(Pair<ExtendedBehaviour<? super E>, Integer>... behaviours) {
        super(behaviours);
    }

    public TrueOneRandomBehaviour(ExtendedBehaviour<? super E>... behaviours) {
        super(behaviours);
    }

    @Override
    protected @Nullable ExtendedBehaviour<? super E> pickBehaviour(ServerLevel level, E entity, long gameTime, SBLShufflingList<ExtendedBehaviour<? super E>> extendedBehaviours) {
        extendedBehaviours.shuffle();

        for (ExtendedBehaviour<? super E> behaviour : extendedBehaviours) {
            if (behaviour.tryStart(level, entity, gameTime)) {
                return behaviour;
            }
        }

        return null;
    }
}
