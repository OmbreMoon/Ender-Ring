package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch;

import com.ombremoon.enderring.common.object.entity.HumanoidMob;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.world.capabilities.entitypatch.Faction;

public class HostileHumanoidMobPatch<E extends HumanoidMob<E>> extends ExtendedHumanoidMobPatch<E> {
    public HostileHumanoidMobPatch(Faction faction) {
        super(faction);
    }

    @Override
    public void initAnimator(ClientAnimator clientAnimator) {

    }

    @Override
    public void updateMotion(boolean b) {

    }
}
