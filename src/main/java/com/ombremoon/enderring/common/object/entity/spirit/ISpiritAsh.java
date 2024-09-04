package com.ombremoon.enderring.common.object.entity.spirit;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;

import javax.annotation.Nullable;
import java.util.UUID;

public interface ISpiritAsh extends OwnableEntity {

    void setOwnerUUID(@Nullable UUID uuid);

    int getSummonCost();

    ResourceLocation getTextureLocation();

}
