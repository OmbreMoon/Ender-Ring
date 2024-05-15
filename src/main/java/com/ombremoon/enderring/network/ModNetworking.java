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
        sendServer(new ServerboundPlayerOriginPacket(characterBase, keepsake, confirmFlag));
    }

    public void updateMainAttributes(boolean setMax) {
        this.sendToServer(new ServerboundUpdateMainStatsPacket(setMax));
    }

    public void passTime() {
        this.sendToServer(new ServerboundPassTimePacket());
    }

    public void openGraceSiteMenu(int menuFlag) {
        this.sendToServer(new ServerboundOpenGraceMenuPacket(menuFlag));
    }

    public void openQuickAccessMenu() {
        this.sendToServer(new ServerboundOpenQuickAccessPacket());
    }

    public void cycleQuickAccessItem() {
        this.sendToServer(new ServerboundCycleQuickAccessPacket());
    }

    public void useQuickAccessItem() {
        this.sendToServer(new ServerboundUseQuickAccessPacket());
    }

    public void selectOrigin(Component component, ServerPlayer serverPlayer) {
        this.sendToPlayer(new ClientboundOriginSelectPacket(component), serverPlayer);
    }

    public void openGraceSiteScreen(Component component, ServerPlayer serverPlayer) {
        this.sendToPlayer(new ClientboundGraceSitePacket(component), serverPlayer);
    }

    public static void syncCap(ServerPlayer serverPlayer) {
        sendPlayer(new ClientboundSyncCapabiltyPacket(PlayerStatusProvider.get(serverPlayer).serializeNBT()), serverPlayer);
    }

    public void useQuickAccessItem(int ticks, ServerPlayer serverPlayer) {
        this.sendToPlayer(new ClientboundUseQuickAccessPacket(ticks), serverPlayer);
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

    protected <MSG> void sendToServer(MSG message) {
        PACKET_CHANNEL.sendToServer(message);
    }

    protected static  <MSG> void sendServer(MSG message) {
        PACKET_CHANNEL.sendToServer(message);
    }

    protected <MSG> void sendToPlayer(MSG message, ServerPlayer serverPlayer) {
        PACKET_CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }

    protected static <MSG> void sendPlayer(MSG message, ServerPlayer serverPlayer) {
        PACKET_CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }

    protected <MSG> void sendToClients(MSG message) {
        PACKET_CHANNEL.send(PacketDistributor.ALL.noArg(), message);
    }
}
