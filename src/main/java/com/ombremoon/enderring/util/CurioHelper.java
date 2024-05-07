package com.ombremoon.enderring.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CurioHelper {
    public static final String TALISMAN = "talismans";
    public static final String QUICK_ACCESS = "quick_access";

    private static IDynamicStackHandler getCurioStacks(Player player, String id) {
        return player.getCapability(CuriosCapability.INVENTORY).orElseThrow(NullPointerException::new).getStacksHandler(id).get().getStacks();
    }

    public static IDynamicStackHandler getQuickAccessStacks(Player player) {
        return getCurioStacks(player, QUICK_ACCESS);
    }

    public static IDynamicStackHandler getTalismanStacks(Player player) {
        return getCurioStacks(player, TALISMAN);
    }

    public static ItemStack findFirstNonEmptyStack(Player player) {
        return getQuickAccessStacks(player).getStackInSlot(findFirstNonEmptySlot(player));
    }

    public static int findFirstNonEmptySlot(Player player) {
        return findFirstNonEmptySlot(CurioHelper.getQuickAccessStacks(player));
    }

    private static int findFirstNonEmptySlot(IDynamicStackHandler stackHandler) {
        for (int i = 0; i < stackHandler.getSlots(); i++) {
            if (!stackHandler.getStackInSlot(i).isEmpty()) {
                return i;
            }
        }
        return 0;
    }

    public static int findFirstEmptySlot(Player player) {
        return findFirstEmptySlot(CurioHelper.getQuickAccessStacks(player));
    }

    private static int findFirstEmptySlot(IDynamicStackHandler stackHandler) {
        for (int i = 0; i < stackHandler.getSlots() - 1; i++) {
            if (stackHandler.getStackInSlot(i).isEmpty()) {
                return i;
            }
        }
        return 10;
    }

    public static int getItemSlot(Player player, Item item) {
        IDynamicStackHandler stackHandler = CurioHelper.getQuickAccessStacks(player);
        for (int i = 0; i < stackHandler.getSlots() - 1; i++) {
            ItemStack itemStack = stackHandler.getStackInSlot(i);
            if (itemStack.is(item)) {
                return i;
            }
        }
        return 10;
    }

    public static void populateSlot(Player player, ItemStack itemStack) {
        populateSlot(player, findFirstEmptySlot(player), itemStack);
    }

    public static void populateSlot(Player player, int slot, ItemStack itemStack) {
        getQuickAccessStacks(player).setStackInSlot(slot, itemStack);
    }
}
