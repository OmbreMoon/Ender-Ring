package com.ombremoon.enderring.network.client;

import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientboundUseQuickAccessPacket {
    private final int ticks;

    public ClientboundUseQuickAccessPacket(int ticks) {
        this.ticks = ticks;
    }

    public ClientboundUseQuickAccessPacket(final FriendlyByteBuf buf) {
        this.ticks = buf.readInt();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeInt(this.ticks);
    }

    public static void handle(ClientboundUseQuickAccessPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ClientGamePacketListener) {
                Minecraft minecraft = Minecraft.getInstance();
                PlayerStatus playerStatus = EntityStatusProvider.get(minecraft.player);
                playerStatus.setUseItemTicks(packet.ticks);
                useItem(playerStatus, minecraft, true);
                if (packet.ticks <= 0) {
                    useItem(playerStatus, minecraft, false);
                    ModNetworking.useQuickAccessItem();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static void useItem(PlayerStatus playerStatus, Minecraft minecraft, boolean usingItem) {
        playerStatus.setUsingQuickAccess(usingItem);
        minecraft.options.keyUse.setDown(usingItem);
//        minecraft.options.keyHotbarSlots[playerStatus.getCachedSlot()].consumeClick();
    }
}
