package com.ombremoon.enderring.common.object.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;

import java.util.List;

public abstract class HumanoidMob<T extends HumanoidMob<T>> extends ERMob<T> {
    protected HumanoidMob(EntityType<? extends HumanoidMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


}
