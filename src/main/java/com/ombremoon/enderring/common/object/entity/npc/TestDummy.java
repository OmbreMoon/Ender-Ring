package com.ombremoon.enderring.common.object.entity.npc;

import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.common.object.entity.ERMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;

//TODO: TESTING LESSER RUNEBEAR
public class TestDummy extends ERMonster<TestDummy> {
    protected TestDummy(EntityType<TestDummy> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public int getRuneReward(Level level, BlockPos blockPos) {
        return 0;
    }

    @Override
    public List<? extends ExtendedSensor<? extends ERMob<TestDummy>>> getSensors() {
        return null;
    }
}
