package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.patches;

import com.ombremoon.enderring.common.object.entity.npc.Hewg;
import com.ombremoon.enderring.common.object.entity.npc.TestDummy;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.SimpleHumanoidMobPatch;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.damagesource.StunType;

public class HewgPatch extends SimpleHumanoidMobPatch<Hewg> {

    @Override
    public void initAnimator(ClientAnimator clientAnimator) {
        clientAnimator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        clientAnimator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        clientAnimator.addLivingAnimation(LivingMotions.CLIMB, Animations.BIPED_CLIMBING);
        clientAnimator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
        clientAnimator.setCurrentMotionsAsDefault();
    }

    @Override
    public void updateMotion(boolean b) {
        super.commonMobUpdateMotion(b);
        if (this.getOriginal().isSmithing()) {
            this.currentLivingMotion = LivingMotions.CLIMB;
        }
    }

    @Override
    public StaticAnimation getHitAnimation(StunType stunType) {
        return null;
    }
}
