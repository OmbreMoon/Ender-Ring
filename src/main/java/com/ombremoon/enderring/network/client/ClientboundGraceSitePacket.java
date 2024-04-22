package com.ombremoon.enderring.network.client;

import com.ombremoon.enderring.client.gui.screen.CharacterBaseScreen;
import com.ombremoon.enderring.client.gui.screen.GraceSiteScreen;
import mod.azure.azurelibarmor.core.math.functions.limit.Min;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundGraceSitePacket {
    private final Component title;

    public ClientboundGraceSitePacket(Component title) {
        this.title = title;
    }

    public ClientboundGraceSitePacket(final FriendlyByteBuf buf) {
        this.title = buf.readComponent();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeComponent(this.title);
    }

    public static void handle(ClientboundGraceSitePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ClientGamePacketListener) {
                Minecraft.getInstance().setScreen(new GraceSiteScreen.Base(packet.title));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
