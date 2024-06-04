package com.ombremoon.enderring.common.object.entity.ai.behavior;

import net.minecraft.world.entity.LivingEntity;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public abstract class AnimatedCombatBehavior<E extends LivingEntity> extends DelayedBehaviour<E> {
    private final LivingEntityPatch.AnimationPacketProvider packetProvider = SPPlayAnimation::new;
    protected final E livingEntity;
    protected final LivingEntityPatch<E> entityPatch;
    protected StaticAnimation animation;

    public AnimatedCombatBehavior(E livingEntity, int delayTicks) {
        super(delayTicks);
        this.livingEntity = livingEntity;

        this.entityPatch = EpicFightCapabilities.getEntityPatch(this.livingEntity, LivingEntityPatch.class);
        if (this.entityPatch == null) {
            throw new IllegalArgumentException("Entity " + this.livingEntity.getName().getString() + " is not patched with Epic Fight!");
        }
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

    @Override
    protected void start(E entity) {
        if (this.animation != null)
            this.entityPatch.playAnimationSynchronized(animation, 0.0F, this.packetProvider);
    }
}
