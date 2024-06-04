package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch;

import com.ombremoon.enderring.common.object.entity.TestDummy;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.gameasset.Animations;

public class TestDummyPatch extends SimpleHumanoidMobPatch<TestDummy> {

    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        clientAnimator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        clientAnimator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
        clientAnimator.setCurrentMotionsAsDefault();
    }

    @Override
    public void updateMotion(boolean b) {
        super.commonMobUpdateMotion(b);
    }
}
