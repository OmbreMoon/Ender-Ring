package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class ServerboundCycleQuickAccessPacket {

    public ServerboundCycleQuickAccessPacket() {
    }

    public ServerboundCycleQuickAccessPacket(final FriendlyByteBuf buf) {
    }

    public void encode(final FriendlyByteBuf buf) {
    }

    public static void handle(ServerboundCycleQuickAccessPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final var context = ctx.get();
            final var handler = context.getNetworkManager().getPacketListener();
            if (handler instanceof ServerGamePacketListenerImpl serverGamePacketListener) {
                ServerPlayer serverPlayer = serverGamePacketListener.player;
                int currentSlot = EntityStatusUtil.getQuickAccessSlot(serverPlayer);
                cycle(serverPlayer, currentSlot);

                Constants.LOG.info(String.valueOf(EntityStatusUtil.getQuickAccessItem(serverPlayer)));
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static <T extends Integer> void cycle(ServerPlayer serverPlayer, T currentItem) {
        int slot = findNextItemInList(createItemList(serverPlayer), currentItem);
        EntityStatusUtil.setQuickAccessSlot(serverPlayer, slot);
    }

    private static <T> T findNextItemInList(Collection<T> itemList, T currentItem) {
        Iterator<T> iterator = itemList.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().equals(currentItem)) {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
                return itemList.iterator().next();
            }
        }
        return iterator.next();
    }

    private static Collection<Integer> createItemList(Player player) {
        List<Integer> itemList = new ArrayList<>();
        IDynamicStackHandler stackHandler = CurioHelper.getQuickAccessStacks(player);
        for (int i = 0; i < stackHandler.getSlots() - 1; i++) {
            if (!stackHandler.getStackInSlot(i).isEmpty()) {
                itemList.add(i);
            }
        }
        return itemList;
    }
}
