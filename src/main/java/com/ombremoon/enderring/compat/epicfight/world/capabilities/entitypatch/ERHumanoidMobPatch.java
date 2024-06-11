package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch;

import com.ombremoon.enderring.common.object.entity.ERMob;
import yesman.epicfight.model.armature.HumanoidArmature;

public abstract class ERHumanoidMobPatch<E extends ERMob> extends ERMobPatch<E> {

    public HumanoidArmature getArmature() {
        return (HumanoidArmature)this.armature;
    }
}
