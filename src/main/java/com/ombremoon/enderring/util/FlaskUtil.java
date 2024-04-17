package com.ombremoon.enderring.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class FlaskUtil {

    public static ResourceLocation getEffectId(CompoundTag compoundTag) {
        return ResourceLocation.tryParse(compoundTag.getString("TearEffect"));
    }
}
