package com.ombremoon.enderring.common.object.entity.mob.creature;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.common.object.entity.ISpiritAsh;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class SpiritJellyfish extends ERMob<SpiritJellyfish> implements ISpiritAsh {
    @Nullable
    private UUID owner;

    protected SpiritJellyfish(EntityType<? extends ERMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public int getRuneReward(Level level, BlockPos blockPos) {
        return 33;
    }

    @Override
    public List<? extends ExtendedSensor<? extends ERMob<SpiritJellyfish>>> getSensors() {
        return null;
    }

    @Override
    public int getSummonCost() {
        return 31;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return null;
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return null;
    }

    public static AttributeSupplier.Builder createTestDummyAttributes() {
        return createERMobAttributes().add(Attributes.MAX_HEALTH, 88.5D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR, 3.0D)
                .add(EntityAttributeInit.PHYS_DEFENSE.get(), 6.8D).add(EntityAttributeInit.MAGIC_DEFENSE.get(), 6.8D).add(EntityAttributeInit.FIRE_DEFENSE.get(), 6.8D).add(EntityAttributeInit.LIGHT_DEFENSE.get(), 6.8D).add(EntityAttributeInit.HOLY_DEFENSE.get(), 6.8D)
                .add(EntityAttributeInit.SLASH_NEGATE.get(), -10.0D).add(EntityAttributeInit.LIGHT_NEGATE.get(), -20.0D)
                .add(EntityAttributeInit.POISON_RESIST.get(), 226).add(EntityAttributeInit.SCARLET_ROT_RESIST.get(), 226).add(EntityAttributeInit.HEMORRHAGE_RESIST.get(), 169).add(EntityAttributeInit.FROSTBITE_RESIST.get(), 169).add(EntityAttributeInit.SLEEP_RESIST.get(), 310).add(EntityAttributeInit.MADNESS_RESIST.get(), -1.0D);
    }
}
