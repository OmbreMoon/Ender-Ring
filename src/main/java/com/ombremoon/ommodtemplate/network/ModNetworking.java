package com.ombremoon.ommodtemplate.network;


import com.ombremoon.ommodtemplate.CommonClass;
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

/*    public void openLevelUpScreen(Component title, ServerPlayer player) {
        this.sendToPlayer(new ClientboundOpenLevelUpScreen(title), player);
    }*/

    public static void registerPackets() {
        var id = 0;
//        PACKET_CHANNEL.registerMessage(id++, ClientboundOpenLevelUpScreen.class, ClientboundOpenLevelUpScreen::encode, ClientboundOpenLevelUpScreen::new, ClientboundOpenLevelUpScreen::handle);
    }

    protected <MSG> void sendToServer(MSG message) {
        ModNetworking.PACKET_CHANNEL.sendToServer(message);
    }

    protected <MSG> void sendToPlayer(MSG message, ServerPlayer serverPlayer) {
        ModNetworking.PACKET_CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), message);
    }
}
