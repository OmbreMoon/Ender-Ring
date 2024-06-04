package com.ombremoon.enderring.common.object.entity.ai.behavior.misc;

import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.ERMobPatch;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class AnimatedIdle<E extends ERMob<E>> extends Idle<E> {
    private final LivingEntityPatch.AnimationPacketProvider packetProvider = SPPlayAnimation::new;
    protected final E livingEntity;
    protected final LivingEntityPatch<E> entityPatch;
    protected final StaticAnimation animation;

    public AnimatedIdle(E livingEntity, StaticAnimation animation) {
        this.livingEntity = livingEntity;
        this.animation = animation;

        this.entityPatch = EpicFightCapabilities.getEntityPatch(this.livingEntity, LivingEntityPatch.class);
        if (this.entityPatch == null) {
            throw new IllegalArgumentException("Entity " + this.livingEntity.getName().getString() + " is not patched with Epic Fight!");
        }
    }

    @Override
    protected void start(E entity) {
        this.entityPatch.playAnimationSynchronized(this.animation, 0.0F, packetProvider);
    }
}
