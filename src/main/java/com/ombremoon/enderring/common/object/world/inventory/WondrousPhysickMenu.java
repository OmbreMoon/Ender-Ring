package com.ombremoon.enderring.common.object.world.inventory;

import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;

public class WondrousPhysickMenu extends AbstractContainerMenu {
    private final ItemStack wondrousPhysick;
    private final Level level;

    public WondrousPhysickMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory, ItemInit.WONDROUS_PHYSICK_FLASK.get().getDefaultInstance());
    }

    public WondrousPhysickMenu(int pContainerId, Inventory inventory, ItemStack itemStack) {
        super(MenuTypeInit.WONDROUS_PHYSICK_MENU.get(), pContainerId);
        checkContainerSize(inventory, 2);
        this.wondrousPhysick = itemStack;
        this.level = inventory.player.level();

        addPlayerSlots(inventory);

        this.wondrousPhysick.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            this.addSlot(new SlotItemHandler(itemHandler, 0, 20, 36));
            this.addSlot(new SlotItemHandler(itemHandler, 1, 20, 9));
        });
    }

    private void addPlayerSlots(Inventory inventory) {

        //Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col, 8 + col * 18, 142));
        }

        //Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18,  84 + row * 18));
            }
        }
    }

    public ItemStack getWondrousPhysick() {
        return this.wondrousPhysick;
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
}
