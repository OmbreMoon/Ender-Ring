package com.ombremoon.enderring.compat.epicfight.api.collider;

import net.minecraft.world.phys.AABB;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class WorldPosMultiOBBCollider extends MultiOBBCollider {
    public WorldPosMultiOBBCollider(int arrayLength, double posX, double posY, double posZ, double vecX, double vecY, double vecZ) {
        super(arrayLength, posX, posY, posZ, vecX, vecY, vecZ);
    }

    @Override
    public AABB getHitboxAABB() {
        return super.getHitboxAABB();
    }
}
