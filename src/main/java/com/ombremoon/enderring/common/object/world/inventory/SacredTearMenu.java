package com.ombremoon.enderring.common.object.world.inventory;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.MenuTypeInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SacredTearMenu extends UpgradeFlaskMenu {
    public SacredTearMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory);
    }

    public SacredTearMenu(int pContainerId, Inventory inventory) {
        super(MenuTypeInit.SACRED_TEAR_MENU.get(), pContainerId, inventory);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot != null && slot.hasItem()) {
            int slotSize = this.slots.size();
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < pPlayer.getInventory().items.size()) {
                if (!this.moveItemStackTo(itemstack1, pPlayer.getInventory().items.size(), slotSize, false))
                    return ItemStack.EMPTY;
            } else if (!this.moveItemStackTo(itemstack1, 0, pPlayer.getInventory().items.size(), false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) slot.set(ItemStack.EMPTY); else slot.setChanged();
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    protected int setUpgradeType() {
        return 1;
    }
}
