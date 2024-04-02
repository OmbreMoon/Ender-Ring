package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundUpdateMainStatsPacket {

    public ServerboundUpdateMainStatsPacket() {
    }

    public ServerboundUpdateMainStatsPacket(final FriendlyByteBuf buf) {
    }

    public void encode(final FriendlyByteBuf buf) {
    }

    public static void handle(ServerboundUpdateMainStatsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                var attributeValue = PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.VIGOR.get());
                byte flag;
                if (attributeValue <= 25) {
                    flag = 1;
                } else if (attributeValue <= 40) {
                    flag = 2;
                } else if (attributeValue <= 60) {
                    flag = 3;
                } else {
                    flag = 4;
                }
                float maxHealth = getMaxHealth(serverPlayer, flag);
                setMaxHealth(serverPlayer, maxHealth);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static float getMaxHealth(ServerPlayer serverPlayer, byte flag) {
        switch (flag) {
            case 1 -> {
                return (float) (20 + 33 * (Math.pow(((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.VIGOR.get()) - 1) / 24), 1.5)));
            }
            case 2 -> {
                return (float) (53 + 43 * (Math.pow(((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.VIGOR.get()) - 25) / 15), 1.1)));
            }
            case 3 -> {
                return (float) (97 + 30 * (1 - ( 1 - Math.pow(((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.VIGOR.get()) - 40) / 20), 1.2))));
            }
            case 4 -> {
                return (float) (127 + 13 * (1 - ( 1 - Math.pow(((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.VIGOR.get()) - 60) / 39), 1.2))));
            }
            default -> {
                return 0;
            }
        }
    }

    private static void setMaxHealth(ServerPlayer serverPlayer, double maxHealth) {
        serverPlayer.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        serverPlayer.setHealth(serverPlayer.getMaxHealth());
    }
}
