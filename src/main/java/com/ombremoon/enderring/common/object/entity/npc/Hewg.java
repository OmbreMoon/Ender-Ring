package com.ombremoon.enderring.common.object.entity.npc;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyBlocksSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;

import java.util.List;

public class Hewg extends MerchantNPCMob {
    public Hewg(EntityType<Hewg> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public List<ExtendedSensor<? extends MerchantNPCMob>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new NearbyBlocksSensor<>()
        );
    }

    @Override
    public boolean canAggro() {
        return false;
    }

    public static AttributeSupplier.Builder createHewgAttributes() {
        return createERMobAttributes().add(Attributes.MAX_HEALTH, 35)
                .add(EntityAttributeInit.PHYS_DEFENSE.get(), 6.7)
                .add(EntityAttributeInit.SLASH_NEGATE.get(), 0.1D)
                .add(EntityAttributeInit.PIERCE_NEGATE.get(), -0.1D)
                .add(EntityAttributeInit.MAGIC_DEFENSE.get(), 6.7)
                .add(EntityAttributeInit.MAGIC_NEGATE.get(), -0.2D)
                .add(EntityAttributeInit.FIRE_DEFENSE.get(), 6.7)
                .add(EntityAttributeInit.LIGHT_DEFENSE.get(), 6.7)
                .add(EntityAttributeInit.HOLY_DEFENSE.get(), 6.7);
    }

    @Override
    public int getRuneReward() {
        return 0;
    }
}
