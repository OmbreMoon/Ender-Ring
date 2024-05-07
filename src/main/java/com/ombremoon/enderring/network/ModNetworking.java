package com.ombremoon.enderring.network;


import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.capability.PlayerStatusProvider;
import com.ombremoon.enderring.client.gui.screen.CharacterBaseScreen;
import com.ombremoon.enderring.network.client.ClientboundCharBaseSelectPacket;
import com.ombremoon.enderring.network.client.ClientboundGraceSitePacket;
import com.ombremoon.enderring.network.client.ClientboundSyncOverlaysPacket;
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

    public void setBaseStats(CharacterBaseScreen.Base characterBase) {
        this.sendToServer(new ServerboundCharacterBasePacket(characterBase));
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

    public void updateWeaponData() {
        this.sendToClients(new ServerboundUpdateWeaponDataPacket());
    }

    public void openCharBaseSelectScreen(Component component, ServerPlayer serverPlayer) {
        this.sendToPlayer(new ClientboundCharBaseSelectPacket(component), serverPlayer);
    }

    public void openGraceSiteScreen(Component component, ServerPlayer serverPlayer) {
        this.sendToPlayer(new ClientboundGraceSitePacket(component), serverPlayer);
    }

    public void syncOverlays(ServerPlayer serverPlayer) {
        this.sendToPlayer(new ClientboundSyncOverlaysPacket(PlayerStatusProvider.get(serverPlayer).serializeNBT()), serverPlayer);
    }

    public void useQuickAccessItem(int ticks, ServerPlayer serverPlayer) {
        this.sendToPlayer(new ClientboundUseQuickAccessPacket(ticks), serverPlayer);
    }

    public static void registerPackets() {
        var id = 0;
        PACKET_CHANNEL.registerMessage(id++, ServerboundCharacterBasePacket.class, ServerboundCharacterBasePacket::encode, ServerboundCharacterBasePacket::new, ServerboundCharacterBasePacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundUpdateMainStatsPacket.class, ServerboundUpdateMainStatsPacket::encode, ServerboundUpdateMainStatsPacket::new, ServerboundUpdateMainStatsPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundUpdateWeaponDataPacket.class, ServerboundUpdateWeaponDataPacket::encode, ServerboundUpdateWeaponDataPacket::new, ServerboundUpdateWeaponDataPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundPassTimePacket.class, ServerboundPassTimePacket::encode, ServerboundPassTimePacket::new, ServerboundPassTimePacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundOpenQuickAccessPacket.class, ServerboundOpenQuickAccessPacket::encode, ServerboundOpenQuickAccessPacket::new, ServerboundOpenQuickAccessPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundCycleQuickAccessPacket.class, ServerboundCycleQuickAccessPacket::encode, ServerboundCycleQuickAccessPacket::new, ServerboundCycleQuickAccessPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundUseQuickAccessPacket.class, ServerboundUseQuickAccessPacket::encode, ServerboundUseQuickAccessPacket::new, ServerboundUseQuickAccessPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ServerboundOpenGraceMenuPacket.class, ServerboundOpenGraceMenuPacket::encode, ServerboundOpenGraceMenuPacket::new, ServerboundOpenGraceMenuPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ClientboundCharBaseSelectPacket.class, ClientboundCharBaseSelectPacket::encode, ClientboundCharBaseSelectPacket::new, ClientboundCharBaseSelectPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ClientboundGraceSitePacket.class, ClientboundGraceSitePacket::encode, ClientboundGraceSitePacket::new, ClientboundGraceSitePacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ClientboundSyncOverlaysPacket.class, ClientboundSyncOverlaysPacket::encode, ClientboundSyncOverlaysPacket::new, ClientboundSyncOverlaysPacket::handle);
        PACKET_CHANNEL.registerMessage(id++, ClientboundUseQuickAccessPacket.class, ClientboundUseQuickAccessPacket::encode, ClientboundUseQuickAccessPacket::new, ClientboundUseQuickAccessPacket::handle);
    }

    protected <MSG> void sendToServer(MSG message) {
        ModNetworking.PACKET_CHANNEL.sendToServer(message);
    }

    protected <MSG> void sendToPlayer(MSG message, ServerPlayer serverPlayer) {
        ModNetworking.PACKET_CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }

    protected <MSG> void sendToClients(MSG message) {
        ModNetworking.PACKET_CHANNEL.send(PacketDistributor.ALL.noArg(), message);
    }
}
