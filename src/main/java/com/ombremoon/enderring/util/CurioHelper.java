package com.ombremoon.enderring.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public class CurioHelper {
    public static final String TALISMAN = "talismans";
    public static final String FLASK = "flasks";

    public static IDynamicStackHandler getCurioStacks(Player player, String id) {
        return player.getCapability(CuriosCapability.INVENTORY).orElseThrow(NullPointerException::new).getStacksHandler(id).get().getStacks();
    }

    public static int getCurioSlots(Player player, String id) {
        return getCurioStacks(player, id).getSlots();
    }

    public static ItemStack getCurioStack(Player player, String id, int index) {
        return getCurioStacks(player, id).getStackInSlot(index);
    }
}
