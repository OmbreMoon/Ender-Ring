package com.ombremoon.enderring.common;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public record DamageInstance(ResourceKey<DamageType> damageType, float amount) {
}
