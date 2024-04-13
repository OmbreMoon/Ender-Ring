package com.ombremoon.enderring.network.client;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class ClientboundSyncOverlaysPacket {
    private final CompoundTag nbt;
    private double fpAmount;
    private SpellType<?> selectedSpell;

    public ClientboundSyncOverlaysPacket(CompoundTag nbt) {
        this.nbt = nbt;
    }

    public ClientboundSyncOverlaysPacket(final FriendlyByteBuf buf) {
        this.nbt = buf.readNbt();
        this.fpAmount = Objects.requireNonNull(nbt).getDouble("FP");
        this.selectedSpell = PlayerStatusUtil.getSpellByName(PlayerStatusUtil.getSpellId(Objects.requireNonNull(nbt), "SelectedSpell"));
    }

    public void encode(final FriendlyByteBuf buf) {
        buf.writeNbt(this.nbt);
    }

    public static void handle(ClientboundSyncOverlaysPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ClientGamePacketListener) {
                Constants.LOG.info(String.valueOf(packet.fpAmount));
                Constants.LOG.info(packet.selectedSpell.toString());
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
