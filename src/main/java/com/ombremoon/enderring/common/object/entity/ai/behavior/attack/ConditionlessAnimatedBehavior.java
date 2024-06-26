package com.ombremoon.enderring.common.object.entity.ai.behavior.attack;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ConditionlessAnimatedBehavior<E extends Mob> extends AnimatedCombatBehavior<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));
    protected Function<E, Integer> attackIntervalSupplier = entity -> 20;
    protected boolean requireTarget = false;
    protected Consumer<E> effect = entity -> {};
    @Nullable
    protected LivingEntity target = null;

    public ConditionlessAnimatedBehavior(int delayTicks) {
        super(delayTicks);
    }

    /**
     * Set that the attack requires that the entity have an attack target set to activate.
     * @return this
     */
    public ConditionlessAnimatedBehavior<E> requiresTarget() {
        this.requireTarget = true;
        return this;
    }

    /**
     * Set the callback for the actual attack when the delay time has elapsed
     * @param consumer The callback
     * @return this
     */
    public ConditionlessAnimatedBehavior<E> attack(Consumer<E> consumer) {
        this.effect = consumer;
        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
        if (!this.requireTarget)
            return true;

        this.target = BrainUtils.getTargetOfEntity(entity);

        return this.target != null;
    }

    @Override
    protected void start(E entity) {
        if (this.requireTarget)
            BehaviorUtils.lookAtEntity(entity, this.target);
    }

    @Override
    protected void doDelayedAction(E entity) {
        if (this.requireTarget && this.target == null)
            return;

        this.effect.accept(entity);
        super.doDelayedAction(entity);
    }
}
