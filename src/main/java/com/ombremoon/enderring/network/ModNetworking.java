package com.ombremoon.enderring.network;


import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.capability.PlayerStatusProvider;
import com.ombremoon.enderring.client.gui.screen.StarterScreen;
import com.ombremoon.enderring.network.client.ClientboundGraceSitePacket;
import com.ombremoon.enderring.network.client.ClientboundOriginSelectPacket;
import com.ombremoon.enderring.network.client.ClientboundSyncCapabiltyPacket;
import com.ombremoon.enderring.network.client.ClientboundUseQuickAccessPacket;
import com.ombremoon.enderring.network.server.*;
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

    public static void setPlayerOrigin(StarterScreen.Base characterBase, StarterScreen.Keepsake keepsake, boolean confirmFlag) {
        sendToServer(new ServerboundPlayerOriginPacket(characterBase, keepsake, confirmFlag));
    }

    //TODO: CHECK FOR SIDEDNESS
    //TODO: CHANGE TO SATURATION SYSTEM
    public static void updateMainAttributes(boolean setMax) {
        sendToServer(new ServerboundUpdateMainStatsPacket(setMax));
    }

    public static void passTime() {
        sendToServer(new ServerboundPassTimePacket());
    }

    public static void openGraceSiteMenu(int menuFlag) {
        sendToServer(new ServerboundOpenGraceMenuPacket(menuFlag));
    }

    public static void openQuickAccessMenu() {
        sendToServer(new ServerboundOpenQuickAccessPacket());
    }

    public static void cycleQuickAccessItem() {
        sendToServer(new ServerboundCycleQuickAccessPacket());
    }

    public static void useQuickAccessItem() {
        sendToServer(new ServerboundUseQuickAccessPacket());
    }

    public static void selectOrigin(Component component, ServerPlayer serverPlayer) {
        sendToPlayer(new ClientboundOriginSelectPacket(component), serverPlayer);
    }

    public static void openGraceSiteScreen(Component component, ServerPlayer serverPlayer) {
        sendToPlayer(new ClientboundGraceSitePacket(component), serverPlayer);
    }

    public static void syncCap(ServerPlayer serverPlayer) {
        sendToPlayer(new ClientboundSyncCapabiltyPacket(PlayerStatusProvider.get(serverPlayer).serializeNBT()), serverPlayer);
    }

    public static void useQuickAccessItem(int ticks, ServerPlayer serverPlayer) {
        sendToPlayer(new ClientboundUseQuickAccessPacket(ticks), serverPlayer);
    }

    public static void registerPackets() {
        var id = 0;
        PACKET_CHANNEL.registerMessage(id++, ServerboundPlayerOriginPacket.class, ServerboundPlayerOriginPacket::encode, ServerboundPlayerOriginPacket::new, ServerboundPlayerOriginPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundUpdateMainStatsPacket.class, ServerboundUpdateMainStatsPacket::encode, ServerboundUpdateMainStatsPacket::new, ServerboundUpdateMainStatsPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundUpdateWeaponDataPacket.class, ServerboundUpdateWeaponDataPacket::encode, ServerboundUpdateWeaponDataPacket::new, ServerboundUpdateWeaponDataPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundPassTimePacket.class, ServerboundPassTimePacket::encode, ServerboundPassTimePacket::new, ServerboundPassTimePacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundOpenQuickAccessPacket.class, ServerboundOpenQuickAccessPacket::encode, ServerboundOpenQuickAccessPacket::new, ServerboundOpenQuickAccessPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundCycleQuickAccessPacket.class, ServerboundCycleQuickAccessPacket::encode, ServerboundCycleQuickAccessPacket::new, ServerboundCycleQuickAccessPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundUseQuickAccessPacket.class, ServerboundUseQuickAccessPacket::encode, ServerboundUseQuickAccessPacket::new, ServerboundUseQuickAccessPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundOpenGraceMenuPacket.class, ServerboundOpenGraceMenuPacket::encode, ServerboundOpenGraceMenuPacket::new, ServerboundOpenGraceMenuPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ClientboundOriginSelectPacket.class, ClientboundOriginSelectPacket::encode, ClientboundOriginSelectPacket::new, ClientboundOriginSelectPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ClientboundGraceSitePacket.class, ClientboundGraceSitePacket::encode, ClientboundGraceSitePacket::new, ClientboundGraceSitePacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ClientboundSyncCapabiltyPacket.class, ClientboundSyncCapabiltyPacket::encode, ClientboundSyncCapabiltyPacket::new, ClientboundSyncCapabiltyPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ClientboundUseQuickAccessPacket.class, ClientboundUseQuickAccessPacket::encode, ClientboundUseQuickAccessPacket::new, ClientboundUseQuickAccessPacket::handle);
    }

    protected static <MSG> void sendToServer(MSG message) {
        PACKET_CHANNEL.sendToServer(message);
    }

    protected static <MSG> void sendToPlayer(MSG message, ServerPlayer serverPlayer) {
        PACKET_CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }

    protected static  <MSG> void sendToClients(MSG message) {
        PACKET_CHANNEL.send(PacketDistributor.ALL.noArg(), message);
    }
}
