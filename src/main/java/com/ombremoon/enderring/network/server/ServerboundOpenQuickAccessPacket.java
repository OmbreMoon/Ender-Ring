package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.common.object.world.inventory.QuickAccessMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ServerboundOpenQuickAccessPacket {
    private final MenuProvider quickAccessProvider = new MenuProvider() {
        @Override
        public Component getDisplayName() {
            return Component.translatable("container.enderring.quick_access_menu");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
            return new QuickAccessMenu(pContainerId, pPlayerInventory);
        }
    };

    public ServerboundOpenQuickAccessPacket() {
    }

    public ServerboundOpenQuickAccessPacket(final FriendlyByteBuf buf) {
    }

    public void encode(final FriendlyByteBuf buf) {
    }

    public static void handle(ServerboundOpenQuickAccessPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                NetworkHooks.openScreen(serverPlayer, packet.quickAccessProvider);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
