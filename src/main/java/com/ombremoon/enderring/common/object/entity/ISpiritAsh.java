package com.ombremoon.enderring.common.object.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.OwnableEntity;

public interface ISpiritAsh extends OwnableEntity {

    int getSummonCost();

    ResourceLocation getTextureLocation();

}
