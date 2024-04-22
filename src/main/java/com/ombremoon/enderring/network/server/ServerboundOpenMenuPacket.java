package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.common.object.world.inventory.GraceSiteProvider;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class ServerboundOpenMenuPacket {
    private final boolean flag;

    public ServerboundOpenMenuPacket(boolean flag) {
        this.flag = flag;
    }

    public ServerboundOpenMenuPacket(final FriendlyByteBuf buf) {
        this.flag = buf.readBoolean();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeBoolean(this.flag);
    }

    public static void handle(ServerboundOpenMenuPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                PlayerStatusUtil.setGraceSiteFlag(serverPlayer, packet.flag);
                NetworkHooks.openScreen(serverPlayer, new GraceSiteProvider());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
