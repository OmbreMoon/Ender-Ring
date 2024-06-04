package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.client.gui.screen.StarterScreen;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.item.ArmorInit;
import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ServerboundPlayerOriginPacket {
    private final StarterScreen.Base characterBase;
    private final StarterScreen.Keepsake keepsake;
    private final boolean confirmFlag;

    public ServerboundPlayerOriginPacket(StarterScreen.Base characterBase, StarterScreen.Keepsake keepsake, boolean confirmFlag) {
        this.characterBase = characterBase;
        this.keepsake = keepsake;
        this.confirmFlag = confirmFlag;
    }

    public ServerboundPlayerOriginPacket(final FriendlyByteBuf buf) {
        this.characterBase = buf.readEnum(StarterScreen.Base.class);
        this.keepsake = buf.readEnum(StarterScreen.Keepsake.class);
        this.confirmFlag = buf.readBoolean();
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeEnum(this.characterBase);
        buf.writeEnum(this.keepsake);
        buf.writeBoolean(this.confirmFlag);
    }

    public static void handle(ServerboundPlayerOriginPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                final var serverPlayer = serverGamePacketListener.player;

                if (!packet.confirmFlag) {
                    setCharacterBaseStats(serverPlayer, packet.characterBase);
                    handleArmorUpdate(serverPlayer, packet.characterBase);
                    ModNetworking.updateMainAttributes(true);
                } else {
                    handleOriginConfirm(serverPlayer, packet.characterBase, packet.keepsake);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static void setCharacterBaseStats(ServerPlayer serverPlayer, StarterScreen.Base characterBase) {
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

    private static void handleArmorUpdate(ServerPlayer serverPlayer, StarterScreen.Base characterBase) {
        ArmorInit armorMaterial = characterBase.getStarterArmor();
        if (armorMaterial != null) {
            switch (armorMaterial) {
                case BLUE_CLOTH -> {
//                    serverPlayer.setItemSlot(EquipmentSlot.HEAD, new ItemStack(EquipmentInit.BLUE_CLOTH_COWL.get()));
//                    serverPlayer.setItemSlot(EquipmentSlot.CHEST, new ItemStack(EquipmentInit.BLUE_CLOTH_VEST.get()));
//                    serverPlayer.setItemSlot(EquipmentSlot.LEGS, new ItemStack(EquipmentInit.BLUE_CLOTH_LEGGINGS.get()));
//                    serverPlayer.setItemSlot(EquipmentSlot.FEET, new ItemStack(EquipmentInit.BLUE_CLOTH_GREAVES.get()));
                }
            }
        }
    }

    private static void handleOriginConfirm(ServerPlayer serverPlayer, StarterScreen.Base characterBase, StarterScreen.Keepsake keepsake) {
        characterBase.getStarterItems().stream().map(Supplier::get).forEach(item -> serverPlayer.addItem(new ItemStack(item)));
        Item item = keepsake.getItem();
        if (item != null)
            serverPlayer.addItem(new ItemStack(item));
    }

    private static void setAttributeValue(ServerPlayer serverPlayer, Attribute attribute, double attributeValue) {
        serverPlayer.getAttributes().getInstance(attribute).setBaseValue(attributeValue);
    }
}
