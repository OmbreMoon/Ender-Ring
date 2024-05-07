package com.ombremoon.enderring.network.server;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundPassTimePacket {

    public ServerboundPassTimePacket() {
    }

    public ServerboundPassTimePacket(final FriendlyByteBuf buf) {
    }

    public void encode(final FriendlyByteBuf buf) {

    }

    public static void handle(ServerboundPassTimePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                final var serverLevel = (ServerLevel) serverPlayer.level();
                /*if (serverLevel.players().size() > 1) {
                    serverLevel.updateSleepingPlayerList();
                }*/
                serverLevel.setDayTime(serverLevel.getDayTime() + 12000);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
