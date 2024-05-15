package com.ombremoon.enderring.util;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.event.FirstSpawnEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ModFunctions {
    public static void enqueueCollectiveTask(MinecraftServer minecraftServer, Runnable runnable, int delay) {
        FirstSpawnEvent.scheduledRunnables.add(new Pair<Integer, Runnable>(minecraftServer.getTickCount() + delay, runnable));
    }

    public static void enqueueImmediateTask(Level world, Runnable task, boolean allowClient) {
        if (world.isClientSide && allowClient) task.run();
        else enqueueTask(world, task, 0);
    }

    public static void enqueueTask(Level world, Runnable task, int delay) {
        if (!(world instanceof ServerLevel)) return;

        var server = ((ServerLevel) world).getServer();
        server.submit(new TickTask(server.getTickCount() + delay, task));
    }

    public static boolean isJoiningWorldForTheFirstTime(Player player, String modid, boolean mustHaveEmptyInventory) {
        var firstjointag = Constants.MOD_ID + ".firstJoin." + modid;
        var tags = player.getTags();
        if (tags.contains(firstjointag)) return false;

        player.addTag(firstjointag);

        if (mustHaveEmptyInventory) {
            var isempty = true;
            for (var i = 0; i < 36; i++)
                if (!player.getInventory().getItem(i).isEmpty()) {
                    isempty = false;
                    break;
                }
            if (!isempty) return false;
        }

        var sharedSpawnPos = ((ServerLevel) player.level()).getSharedSpawnPos();
        var blockPos = player.blockPosition();
        var blockPos1 = new BlockPos(blockPos.getX(), sharedSpawnPos.getY(), blockPos.getZ());

        return blockPos1.closerThan(sharedSpawnPos, 50);
    }
}
