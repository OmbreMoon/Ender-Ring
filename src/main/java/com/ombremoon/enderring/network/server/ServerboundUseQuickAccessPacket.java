package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundUseQuickAccessPacket {
    public ServerboundUseQuickAccessPacket() {
    }

    public ServerboundUseQuickAccessPacket(final FriendlyByteBuf buf) {
    }

    public void encode(final FriendlyByteBuf buf) {
    }

    public static void handle(ServerboundUseQuickAccessPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                handleUseQuickAccess(packet, serverPlayer);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static void handleUseQuickAccess(ServerboundUseQuickAccessPacket packet, ServerPlayer serverPlayer) {
        ItemStack itemStack = PlayerStatusUtil.getQuickAccessItem(serverPlayer);

        if (!PlayerStatusUtil.isUsingQuickAccess(serverPlayer)) {
            if (!itemStack.isEmpty()) {
                PlayerStatusUtil.setUsingQuickAccess(serverPlayer, true);
                serverPlayer.setItemInHand(InteractionHand.MAIN_HAND, itemStack);
            }
        } else {
            ItemStack cachedStack = PlayerStatusUtil.getCachedItem(serverPlayer);
            PlayerStatusUtil.setUsingQuickAccess(serverPlayer, false);
            serverPlayer.setItemInHand(InteractionHand.MAIN_HAND, cachedStack);
        }
    }
}
