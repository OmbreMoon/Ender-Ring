package com.ombremoon.enderring.network.client;

import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundSyncCapabiltyPacket {
    private final CompoundTag nbt;

    public ClientboundSyncCapabiltyPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public ClientboundSyncCapabiltyPacket(final FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeNbt(this.nbt);
    }

    public static void handle(ClientboundSyncCapabiltyPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ClientGamePacketListener) {
                Player player = Minecraft.getInstance().player;
                if (player != null) {
                    EntityStatusProvider.get(player).deserializeNBT(packet.nbt);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
