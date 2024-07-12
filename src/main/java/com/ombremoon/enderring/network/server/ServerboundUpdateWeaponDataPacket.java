package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.data.ScaledWeaponManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import org.apache.commons.lang3.Validate;

import java.util.Map;
import java.util.function.Supplier;

public class ServerboundUpdateWeaponDataPacket {
    private Map<ResourceLocation, ScaledWeapon> registeredWeapons;

    public ServerboundUpdateWeaponDataPacket() {
    }

    public ServerboundUpdateWeaponDataPacket(final FriendlyByteBuf buf) {
        this.registeredWeapons = ScaledWeaponManager.readRegisteredWeapons(buf);
    }

    public void encode(final FriendlyByteBuf buf) {
        Validate.notNull(ScaledWeaponManager.getInstance());
        ScaledWeaponManager.getInstance().writeRegisteredWeapons(buf);
    }

    public static void handle(ServerboundUpdateWeaponDataPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {

            ScaledWeaponManager.updateRegisteredWeapons(packet.registeredWeapons);

        });
        ctx.get().setPacketHandled(true);
    }
}
