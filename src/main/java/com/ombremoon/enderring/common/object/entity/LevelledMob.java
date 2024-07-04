package com.ombremoon.enderring.common.object.entity;

import com.ombremoon.enderring.common.StatusType;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public interface LevelledMob {

    default boolean isImmuneTo(LivingEntity targetEntity, StatusType statusType, DamageSource damageSource) {
        return EntityStatusUtil.getEntityAttribute(targetEntity, statusType.getEntityResist()) == -1;
    }
}
