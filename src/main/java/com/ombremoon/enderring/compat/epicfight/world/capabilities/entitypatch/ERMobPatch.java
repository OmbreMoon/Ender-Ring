package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch;

import com.ombremoon.enderring.common.object.entity.ERMob;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

public abstract class ERMobPatch<E extends ERMob> extends MobPatch<E> {

    @Override
    protected void initAI() {
    }
}
