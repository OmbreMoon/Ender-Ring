package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.function.Supplier;

import static yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch.STAMINA;

public class ServerboundUpdateMainStatsPacket {
    private final boolean setMax;

    public ServerboundUpdateMainStatsPacket(boolean setMax) {
        this.setMax = setMax;
    }

    public ServerboundUpdateMainStatsPacket(final FriendlyByteBuf buf) {
        this.setMax = buf.readBoolean();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeBoolean(this.setMax);
    }

    public static void handle(ServerboundUpdateMainStatsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                byte flag1 = getAttributeFlag(EntityAttributeInit.VIGOR.get(), PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.VIGOR.get()));
                byte flag2 = getAttributeFlag(EntityAttributeInit.MIND.get(), PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.MIND.get()));
                byte flag3 = getAttributeFlag(EntityAttributeInit.ENDURANCE.get(), PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.ENDURANCE.get()));

                updateMainStats(serverPlayer, getMaxHealth(serverPlayer, flag1), getMaxFP(serverPlayer, flag2), getMaxStamina(serverPlayer, flag3), packet.setMax);
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

    private static float getMaxFP(ServerPlayer serverPlayer, byte flag) {
        switch (flag) {
            case 1 -> {
                return (float) (50 + 45 * (((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.MIND.get()) - 1) / 14)));
            }
            case 2 -> {
                return (float) (95 + 105 * (((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.MIND.get()) - 15) / 20)));
            }
            case 3 -> {
                return (float) (200 + 150 * (1 - ( 1 - Math.pow(((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.MIND.get()) - 35) / 25), 1.2))));
            }
            case 4 -> {
                return (float) (350 + 100 * (((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.MIND.get()) - 60) / 39)));
            }
            default -> {
                return 0;
            }
        }
    }

    private static float getMaxStamina(ServerPlayer serverPlayer, byte flag) {
        switch (flag) {
            case 1 -> {
                return (float) (16 + 5 * (((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.ENDURANCE.get()) - 1) / 14)));
            }
            case 2 -> {
                return (float) (21 + 5 * (((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.ENDURANCE.get()) - 15) / 15)));
            }
            case 3 -> {
                return (float) (26 + 5 * (((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.ENDURANCE.get()) - 30) / 20)));
            }
            case 4 -> {
                return (float) (31 + 3 * (((PlayerStatusUtil.getPlayerStat(serverPlayer, EntityAttributeInit.ENDURANCE.get()) - 50) / 49)));
            }
            default -> {
                return 0;
            }
        }
    }

    private static byte getAttributeFlag(Attribute attribute, double attributeValue) {
        byte flag;
        if ((attribute == EntityAttributeInit.VIGOR.get() && attributeValue <= 25) || ((attribute == EntityAttributeInit.MIND.get() || attribute == EntityAttributeInit.ENDURANCE.get()) && attributeValue <= 15)) {
            flag = 1;
        } else if ((attribute == EntityAttributeInit.VIGOR.get() && attributeValue <= 40) || ((attribute == EntityAttributeInit.MIND.get() || attribute == EntityAttributeInit.ENDURANCE.get()) && attributeValue <= 35)) {
            flag = 2;
        } else if ((attribute == EntityAttributeInit.VIGOR.get() && attributeValue <= 60) || ((attribute == EntityAttributeInit.MIND.get() || attribute == EntityAttributeInit.ENDURANCE.get()) && attributeValue <= 60)) {
            flag = 3;
        } else {
            flag = 4;
        }
        return flag;
    }

    private static void updateMainStats(ServerPlayer serverPlayer, double maxHealth, double maxFP, double maxStamina, boolean setMax) {
        serverPlayer.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(maxHealth);
        serverPlayer.getAttributes().getInstance(EntityAttributeInit.MAX_FP.get()).setBaseValue(maxFP);
        if (setMax) {
            serverPlayer.setHealth(serverPlayer.getMaxHealth());
            PlayerStatusUtil.setFPAmount(serverPlayer, maxFP);
        }

        if (CommonClass.hasEpicFight()) {
            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, ServerPlayerPatch.class);
            serverPlayer.getAttributes().getInstance(EpicFightAttributes.MAX_STAMINA.get()).setBaseValue(maxStamina);
            if (setMax)
                playerPatch.setStamina(playerPatch.getMaxStamina());
        }
    }
}
