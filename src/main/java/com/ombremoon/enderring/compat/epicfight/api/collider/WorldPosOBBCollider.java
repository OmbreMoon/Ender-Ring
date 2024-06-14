package com.ombremoon.enderring.compat.epicfight.api.collider;

import net.minecraft.world.phys.AABB;
import yesman.epicfight.api.collider.OBBCollider;

public class WorldPosOBBCollider extends OBBCollider {
    public WorldPosOBBCollider(double posX, double posY, double posZ, double center_x, double center_y, double center_z) {
        super(posX, posY, posZ, center_x, center_y, center_z);
    }

    @Override
    public AABB getHitboxAABB() {
        return super.getHitboxAABB();
    }
}
