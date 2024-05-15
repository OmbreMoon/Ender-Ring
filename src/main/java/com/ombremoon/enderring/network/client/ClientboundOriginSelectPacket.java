package com.ombremoon.enderring.network.client;

import com.ombremoon.enderring.client.gui.screen.StarterScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundOriginSelectPacket {
    private final Component title;

    public ClientboundOriginSelectPacket(Component title) {
        this.title = title;
    }

    public ClientboundOriginSelectPacket(final FriendlyByteBuf buf) {
        this.title = buf.readComponent();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeComponent(this.title);
    }

    public static void handle(ClientboundOriginSelectPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ClientGamePacketListener) {
                Minecraft.getInstance().setScreen(new StarterScreen.CharacterBaseScreen(packet.title));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
