package com.ombremoon.enderring;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec CLIENT_CONFIG;

    public static final ForgeConfigSpec.ConfigValue<Integer> WEAPON_SCALE;
    public static final ForgeConfigSpec.ConfigValue<Integer> STAT_SCALE;

    static {
        WEAPON_SCALE = COMMON_BUILDER.defineInRange("endderring.config.weapon_scale", 15, 1, 100);
        STAT_SCALE = COMMON_BUILDER.defineInRange("endderring.config.stat_scale", 15, 1, 100);
        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
