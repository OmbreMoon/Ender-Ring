package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundChargeSpellPacket {
    private boolean chargeSpell;

    public ServerboundChargeSpellPacket(boolean chargeSpell) {
        this.chargeSpell = chargeSpell;
    }

    public ServerboundChargeSpellPacket(final FriendlyByteBuf buf) {
        this.chargeSpell = buf.readBoolean();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeBoolean(this.chargeSpell);
    }

    public static void handle(ServerboundChargeSpellPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                EntityStatusUtil.setChannelling(serverPlayer, packet.chargeSpell);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
