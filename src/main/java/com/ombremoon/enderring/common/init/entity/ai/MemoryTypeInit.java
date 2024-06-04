package com.ombremoon.enderring.common.init.entity.ai;

import com.mojang.serialization.Codec;
import com.ombremoon.enderring.Constants;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import javax.annotation.Nullable;
import java.util.UUID;
import java.util.function.Supplier;

public class MemoryTypeInit {
    public static void init() {}

    public static final Supplier<MemoryModuleType<UUID>> AGGRO_PLAYERS = register("aggro_players");

    public static <T> Supplier<MemoryModuleType<T>> register(String id) {
        return register(id, null);
    }

    public static <T> Supplier<MemoryModuleType<T>> register(String id, @Nullable Codec<T> codec) {
        return Constants.SBL_LOADER.registerMemoryType(id, codec);
    }
}
