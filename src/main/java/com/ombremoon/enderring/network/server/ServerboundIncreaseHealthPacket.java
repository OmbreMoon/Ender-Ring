package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ServerboundIncreaseHealthPacket {
    private UUID uuid;
    private int increaseAmount;

    public ServerboundIncreaseHealthPacket(UUID uuid, int increaseAmount) {
        this.uuid = uuid;
        this.increaseAmount = increaseAmount;
    }

    public ServerboundIncreaseHealthPacket(final FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        this.increaseAmount = buf.readInt();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeUUID(this.uuid);
        buf.writeInt(this.increaseAmount);
    }

    public static void handle(ServerboundIncreaseHealthPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                PlayerStatusUtil.increaseStat(serverPlayer, Attributes.MAX_HEALTH, packet.uuid, serverPlayer.getMaxHealth() * ((packet.increaseAmount * 0.01) + 1));

            }
        });
        ctx.get().setPacketHandled(true);
    }
}
