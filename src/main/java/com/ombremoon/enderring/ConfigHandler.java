package com.ombremoon.enderring;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigHandler {
    public static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final ForgeConfigSpec CLIENT_CONFIG;

    public static final ForgeConfigSpec.ConfigValue<Integer> STAT_SCALE;
    public static final ForgeConfigSpec.ConfigValue<Boolean> AGGRO_NPC;

    static {
        STAT_SCALE = COMMON_BUILDER.defineInRange("endderring.config.stat_scale", 15, 1, 100);
        AGGRO_NPC = COMMON_BUILDER.define("endderring.config.aggro_npc", true);
        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
