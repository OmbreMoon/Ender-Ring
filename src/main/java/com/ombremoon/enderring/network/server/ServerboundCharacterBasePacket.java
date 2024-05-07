package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.client.gui.screen.CharacterBaseScreen;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundCharacterBasePacket {
    private final CharacterBaseScreen.Base characterBase;

    public ServerboundCharacterBasePacket(CharacterBaseScreen.Base characterBase) {
        this.characterBase = characterBase;
    }

    public ServerboundCharacterBasePacket(final FriendlyByteBuf buf) {
        this.characterBase = buf.readEnum(CharacterBaseScreen.Base.class);
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeEnum(this.characterBase);
    }

    public static void handle(ServerboundCharacterBasePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;
                setCharacterBaseStats(serverPlayer, packet.characterBase);
                ModNetworking.getInstance().updateMainAttributes(true);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static void setCharacterBaseStats(ServerPlayer serverPlayer, CharacterBaseScreen.Base characterBase) {
        setAttributeValue(serverPlayer, EntityAttributeInit.RUNE_LEVEL.get(), characterBase.getLevel());
        setAttributeValue(serverPlayer, EntityAttributeInit.VIGOR.get(), characterBase.getVigor());
        setAttributeValue(serverPlayer, EntityAttributeInit.MIND.get(), characterBase.getMind());
        setAttributeValue(serverPlayer, EntityAttributeInit.ENDURANCE.get(), characterBase.getEndurance());
        setAttributeValue(serverPlayer, EntityAttributeInit.STRENGTH.get(), characterBase.getStrength());
        setAttributeValue(serverPlayer, EntityAttributeInit.DEXTERITY.get(), characterBase.getDexterity());
        setAttributeValue(serverPlayer, EntityAttributeInit.INTELLIGENCE.get(), characterBase.getIntelligence());
        setAttributeValue(serverPlayer, EntityAttributeInit.FAITH.get(), characterBase.getFaith());
        setAttributeValue(serverPlayer, EntityAttributeInit.ARCANE.get(), characterBase.getArcane());
    }

    private static void setAttributeValue(ServerPlayer serverPlayer, Attribute attribute, double attributeValue) {
        serverPlayer.getAttributes().getInstance(attribute).setBaseValue(attributeValue);
    }
}
