package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch;

import com.ombremoon.enderring.common.object.entity.ERMob;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public abstract class ERMobPatch<T extends ERMob<T>> extends MobPatch<T> {

    @Override
    protected void initAI() {
    }
}
