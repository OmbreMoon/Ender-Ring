package com.ombremoon.enderring.util;

import com.ombremoon.enderring.common.init.item.ItemInit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

public class CurioHelper {
    public static final String TALISMAN = "talismans";
    public static final String QUICK_ACCESS = "quick_access";

    private static IDynamicStackHandler getCurioStacks(Player player, String id) {
        Optional<ICurioStacksHandler> stacksHandler = player.getCapability(CuriosCapability.INVENTORY).orElseThrow(NullPointerException::new).getStacksHandler(id);
        return stacksHandler.map(ICurioStacksHandler::getStacks).orElse(null);
    }

    public static IDynamicStackHandler getQuickAccessStacks(Player player) {
        return getCurioStacks(player, QUICK_ACCESS);
    }

    public static IDynamicStackHandler getTalismanStacks(Player player) {
        return getCurioStacks(player, TALISMAN);
    }

    public static boolean hasTalisman(Player player, Item talisman) {
        IDynamicStackHandler talismans = getTalismanStacks(player);
        for (int i = 0; i < talismans.getSlots(); i++) {
            if (talismans.getStackInSlot(i).is(talisman)) return true;
        }
        return false;
    }

    public static boolean hasTalisman(Player player, RegistryObject<Item> talisman) {
        return hasTalisman(player, talisman.get());
    }

    public static ItemStack getQuickAccessStack(Player player, int slot) {
        return getQuickAccessStacks(player).getStackInSlot(slot);
    }

    public static ItemStack findFirstNonEmptyStack(Player player) {
        return getQuickAccessStacks(player).getStackInSlot(findFirstNonEmptySlot(player));
    }

    public static int findFirstNonEmptySlot(Player player) {
        return findFirstNonEmptySlot(getQuickAccessStacks(player));
    }

    private static int findFirstNonEmptySlot(IDynamicStackHandler stackHandler) {
        for (int i = 0; i < stackHandler.getSlots() - 1; i++) {
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

    public static int getItemSlot(Player player, ItemStack itemStack) {
        IDynamicStackHandler stackHandler = CurioHelper.getQuickAccessStacks(player);
        for (int i = 0; i < stackHandler.getSlots() - 1; i++) {
            ItemStack currentStack = stackHandler.getStackInSlot(i);
            if (currentStack.equals(itemStack, false)) {
                return i;
            }
        }
        return findFirstNonEmptySlot(stackHandler);
    }

    public static void populateSlot(Player player, ItemStack itemStack) {
        populateSlot(player, findFirstEmptySlot(player), itemStack);
    }

    public static void populateSlot(Player player, int slot, ItemStack itemStack) {
        getQuickAccessStacks(player).setStackInSlot(slot, itemStack);
    }
}
