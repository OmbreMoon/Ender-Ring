package com.ombremoon.enderring.event;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.ModFunctions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.concurrent.CopyOnWriteArrayList;

public class FirstSpawnEvent {
    public static CopyOnWriteArrayList<Pair<Integer, Runnable>> scheduledRunnables = new CopyOnWriteArrayList<Pair<Integer, Runnable>>();
    public static final Component CHARACTER_ORIGIN = Component.translatable(Constants.MOD_ID + ".menu.character_origin");


    public static void onSpawn(Level level, Entity entity) {
        if (level.isClientSide) return;
        if (!(entity instanceof Player)) return;
        var player = (ServerPlayer) entity;
        ModFunctions.enqueueCollectiveTask(level.getServer(), () -> {
            if (ModFunctions.isJoiningWorldForTheFirstTime(player, Constants.MOD_ID, false)) {
                ModNetworking.getInstance().selectOrigin(CHARACTER_ORIGIN, player);
            }
        }, 5);
    }

    public static void onServerTick(MinecraftServer minecraftServer) {
        var serverTickCount = minecraftServer.getTickCount();
        for (var pair : scheduledRunnables)
            if (pair.getFirst() <= serverTickCount) {
                minecraftServer.tell(new TickTask(minecraftServer.getTickCount(), pair.getSecond()));
                scheduledRunnables.remove(pair);
            }
    }
}
