package com.ombremoon.enderring.common.object.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class CrystalTearItem extends Item {
    private final Supplier<MobEffect> mobEffect;
    private final int duration;

    public CrystalTearItem(Supplier<MobEffect> mobEffect, int duration, Properties pProperties) {
        super(pProperties);
        this.mobEffect = mobEffect;
        this.duration = duration;
    }

    public MobEffect getMobEffect() {
        return mobEffect.get();
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("CrystalTear", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this)).toString());
        compoundTag.putString("TearEffect", Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getKey(this.mobEffect.get())).toString());
        compoundTag.putInt("TearDuration", this.duration);
        return compoundTag;
    }

}
