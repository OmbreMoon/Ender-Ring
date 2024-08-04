package com.ombremoon.enderring.common.object.entity.ai.behavior.attack;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.entity.ai.behavior.AggroBasedDelayedBehavior;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.function.Function;

public abstract class AnimatedCombatBehavior<E extends Mob> extends AggroBasedDelayedBehavior<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));
    private final LivingEntityPatch.AnimationPacketProvider packetProvider = SPPlayAnimation::new;
    protected LivingEntityPatch<E> entityPatch;
    protected StaticAnimation animation;
    protected Function<E, Integer> attackIntervalSupplier = entity -> 20;
    protected Pair<Integer, Integer> attackRange = Pair.of(0, 6);
    @Nullable
    protected LivingEntity target = null;

    public AnimatedCombatBehavior(int delayTicks) {
        super(delayTicks);
    }

    /**
     * Sets the animation for the behavior.
     * @param animation The animation
     * @return this
     */
    public final AnimatedCombatBehavior<E> behaviorAnim(StaticAnimation animation) {
        this.animation = animation;
        return this;
    }

    /**
     * Set the time between attacks.
     * @param supplier The tick value provider
     * @return this
     */
    public AnimatedCombatBehavior<E> attackInterval(Function<E, Integer> supplier) {
        this.attackIntervalSupplier = supplier;
        return this;
    }

    /**
     * Set the range in blocks that the entity should be able to attack targets.
     * @param minRange
     * @param maxRange
     * @return this
     */
    public AnimatedCombatBehavior<E> attackRange(int minRange, int maxRange) {
        if (minRange >= maxRange) throw new IllegalArgumentException("Min range must be smaller than max range");
        this.attackRange = Pair.of(minRange, maxRange);

        return this;
    }

    public AnimatedCombatBehavior<E> attackRange(int range) {
        return attackRange(0, range);
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        this.entityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (this.entityPatch == null)
            throw new IllegalArgumentException("Entity " + entity.getName().getString() + " is not patched with Epic Fight!");


        this.target = BrainUtils.getTargetOfEntity(entity);
        return BrainUtils.canSee(entity, this.target) && this.isWithinRange(entity, this.target);
    }

    @Override
    protected void start(E entity) {
        super.start(entity);
        BehaviorUtils.lookAtEntity(entity, this.target);
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));
        if (this.animation != null) {
            this.entityPatch.playAnimationSynchronized(animation, 0.0F, this.packetProvider);
        }
    }

    protected boolean isWithinRange(LivingEntity entity, LivingEntity target) {
        float dist = (float) entity.distanceToSqr(target);
        var range = this.attackRange;
        return dist > range.getFirst() * range.getFirst() && dist < range.getSecond() * range.getSecond();
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return true;
    }

    @Override
    protected void tick(E entity) {
        if (this.target == null)
            return;

        if (!entity.getSensing().hasLineOfSight(this.target) || !this.isWithinRange(entity, this.target))
            doStop((ServerLevel) entity.level(), entity, entity.level().getGameTime());
    }
}
