package com.ombremoon.enderring.network;


import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.client.gui.screen.CharacterBaseScreen;
import com.ombremoon.enderring.network.client.ClientboundCharBaseSelectPacket;
import com.ombremoon.enderring.network.server.ServerboundCharacterBasePacket;
import com.ombremoon.enderring.network.server.ServerboundUpdateMainStatsPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModNetworking {
    private static final String VER = "1";
    public static final SimpleChannel PACKET_CHANNEL = NetworkRegistry.newSimpleChannel(CommonClass.customLocation("main"), () -> VER, VER::equals, VER::equals);
    public static ModNetworking instance;

    public static ModNetworking getInstance() {
        if (instance == null) {
            return new ModNetworking();
        }
        return instance;
    }

    public void setBaseStats(CharacterBaseScreen.Base characterBase) {
        this.sendToServer(new ServerboundCharacterBasePacket(characterBase));
    }

    public void updateMainAttributes() {
        this.sendToServer(new ServerboundUpdateMainStatsPacket());
    }

    public void openCharBaseSelectScreen(Component component, ServerPlayer serverPlayer) {
        this.sendToPlayer(new ClientboundCharBaseSelectPacket(component), serverPlayer);
    }

    public static void registerPackets() {
        var id = 0;
        PACKET_CHANNEL.registerMessage(id++, ClientboundCharBaseSelectPacket.class, ClientboundCharBaseSelectPacket::encode, ClientboundCharBaseSelectPacket::new, ClientboundCharBaseSelectPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundCharacterBasePacket.class, ServerboundCharacterBasePacket::encode, ServerboundCharacterBasePacket::new, ServerboundCharacterBasePacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundUpdateMainStatsPacket.class, ServerboundUpdateMainStatsPacket::encode, ServerboundUpdateMainStatsPacket::new, ServerboundUpdateMainStatsPacket::handle);
    }

    protected <MSG> void sendToServer(MSG message) {
        ModNetworking.PACKET_CHANNEL.sendToServer(message);
    }

    protected <MSG> void sendToPlayer(MSG message, ServerPlayer serverPlayer) {
        ModNetworking.PACKET_CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }
}
