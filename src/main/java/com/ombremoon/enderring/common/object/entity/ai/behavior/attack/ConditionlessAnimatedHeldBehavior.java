package com.ombremoon.enderring.common.object.entity.ai.behavior.attack;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.HeldBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.function.Function;

public class ConditionlessAnimatedHeldBehavior<E extends Mob> extends HeldBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));
    private final LivingEntityPatch.AnimationPacketProvider packetProvider = SPPlayAnimation::new;
    protected LivingEntityPatch<E> entityPatch;
    protected StaticAnimation animation;
    protected Function<E, Integer> attackIntervalSupplier = entity -> 20;
    protected boolean requireTarget = false;
    @Nullable
    protected LivingEntity target = null;

    /**
     * Sets the animation for the behavior.
     * @param animation The animation
     * @return this
     */
    public final ConditionlessAnimatedHeldBehavior<E> behaviorAnim(StaticAnimation animation) {
        this.animation = animation;
        return this;
    }

    /**
     * Set that the attack requires that the entity have an attack target set to activate.
     * @return this
     */
    public ConditionlessAnimatedHeldBehavior<E> requiresTarget() {
        this.requireTarget = true;
        return this;
    }

    /**
     * Set the time between attacks.
     * @param supplier The tick value provider
     * @return this
     */
    public ConditionlessAnimatedHeldBehavior<E> attackInterval(Function<E, Integer> supplier) {
        this.attackIntervalSupplier = supplier;
        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {this.entityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (this.entityPatch == null) {
            throw new IllegalArgumentException("Entity " + entity.getName().getString() + " is not patched with Epic Fight!");
        }
        if (!this.requireTarget)
            return true;

        this.target = BrainUtils.getTargetOfEntity(entity);

        return this.target != null;
    }

    @Override
    protected void start(E entity) {
        if (this.requireTarget)
            BehaviorUtils.lookAtEntity(entity, this.target);

        if (this.animation != null)
            this.entityPatch.playAnimationSynchronized(animation, 0.0F, this.packetProvider);
    }

    @Override
    protected void stop(ServerLevel level, E entity, long gameTime) {
        super.stop(level, entity, gameTime);
        this.target = null;
    }
}
