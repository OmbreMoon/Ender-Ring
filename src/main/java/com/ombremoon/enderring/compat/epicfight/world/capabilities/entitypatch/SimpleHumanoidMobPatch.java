package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch;

import com.ombremoon.enderring.common.object.entity.HumanoidMob;
import io.netty.buffer.ByteBuf;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Faction;

public abstract class SimpleHumanoidMobPatch<E extends HumanoidMob<?>> extends ExtendedHumanoidMobPatch<E> {
    public SimpleHumanoidMobPatch() {
        super(Faction.NEUTRAL);
    }
}
