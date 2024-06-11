package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch;

import com.ombremoon.enderring.common.object.entity.ERMob;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.damagesource.StunType;

public class HostileHumanoidMobPatch<E extends ERMob<E>> extends ERHumanoidMobPatch<E> {

    @Override
    public void initAnimator(ClientAnimator clientAnimator) {

    }

    @Override
    public void updateMotion(boolean b) {

    }

    @Override
    public StaticAnimation getHitAnimation(StunType stunType) {
        return null;
    }
}
