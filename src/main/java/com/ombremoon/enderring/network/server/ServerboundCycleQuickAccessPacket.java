package com.ombremoon.enderring.network.server;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
                ItemStack itemStack = (PlayerStatusUtil.getQuickAccessItem(serverPlayer) != null && !PlayerStatusUtil.getQuickAccessItem(serverPlayer).isEmpty()) ? PlayerStatusUtil.getQuickAccessItem(serverPlayer) : CurioHelper.findFirstNonEmptyStack(serverPlayer);
                cycle(serverPlayer, itemStack);
                Constants.LOG.info(String.valueOf(PlayerStatusUtil.getQuickAccessItem(serverPlayer)));
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static <T extends ItemStack> void cycle(ServerPlayer serverPlayer, T currentItem) {
        ItemStack itemStack = findNextItemInList(createItemList(serverPlayer), currentItem);
        PlayerStatusUtil.setQuickAccessItem(serverPlayer, itemStack);
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

    private static Collection<ItemStack> createItemList(Player player) {
        List<ItemStack> itemList = new ArrayList<>();
        IDynamicStackHandler stackHandler = CurioHelper.getQuickAccessStacks(player);
        for (int i = 0; i < stackHandler.getSlots() - 1; i++) {
            if (!stackHandler.getStackInSlot(i).isEmpty()) {
                itemList.add(stackHandler.getStackInSlot(i));
            }
        }
        return itemList;
    }
}
