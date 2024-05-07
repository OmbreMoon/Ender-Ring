package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.common.MenuProviders;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.MenuProvider;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class ServerboundOpenGraceMenuPacket {
    private final int menuFlag;

    public ServerboundOpenGraceMenuPacket(int menuFlag) {
        this.menuFlag = menuFlag;
    }

    public ServerboundOpenGraceMenuPacket(final FriendlyByteBuf buf) {
        this.menuFlag = buf.readInt();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeInt(this.menuFlag);
    }

    public static void handle(ServerboundOpenGraceMenuPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                handleOpenGraceSiteMenu(packet, serverPlayer);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static void handleOpenGraceSiteMenu(ServerboundOpenGraceMenuPacket packet, ServerPlayer serverPlayer) {
        MenuProvider menuProvider;
        switch (packet.menuFlag) {
            case 1 -> {
                menuProvider = MenuProviders.MEMORIZE_SPELL;
            }
            case 2 -> {
                menuProvider = MenuProviders.GOLDEN_SEED;
            }
            case 3 -> {
                menuProvider = MenuProviders.SACRED_TEAR;
            }
            default -> {
                menuProvider = MenuProviders.WONDROUS_PHYSICK;
            }
        }
        NetworkHooks.openScreen(serverPlayer, menuProvider);
    }
}
