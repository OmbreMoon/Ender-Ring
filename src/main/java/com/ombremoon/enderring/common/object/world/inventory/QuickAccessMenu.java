package com.ombremoon.enderring.common.object.world.inventory;

import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class QuickAccessMenu extends AbstractContainerMenu {
    private final DataSlot talismanPouches = DataSlot.standalone();

    public QuickAccessMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory);
    }

    public QuickAccessMenu(int pContainerId, Inventory inventory) {
        super(MenuTypeInit.QUICK_ACCESS_MENU.get(), pContainerId);
        Player player = inventory.player;

        addPlayerSlots(inventory);
        addQuickAccessSlots(player);
        addTalismanSlots(player);
        this.addDataSlot(this.talismanPouches).set(EntityStatusUtil.getTalismanPouches(player));
    }

    private void addPlayerSlots(Inventory inventory) {

        //Hotbar
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inventory, col, 8 + col * 18, 144));
        }

        //Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inventory, col + row * 9 + 9, 8 + col * 18,  86 + row * 18));
            }
        }
    }

    private void addQuickAccessSlots(Player player) {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 5; col++) {
                this.addSlot(new SlotItemHandler(CurioHelper.getQuickAccessStacks(player), col + row * 5, 44 + col * 18, 43 + row * 18));
            }
        }
    }

    private void addTalismanSlots(Player player) {
        for (int col = 0; col < 4; col++) {
            int index = col;
            this.addSlot(new SlotItemHandler(CurioHelper.getTalismanStacks(player), index, 53 + col * 18, 18) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return EntityStatusUtil.getTalismanPouches(player) >= index;
                }
            });
        }
    }

    public int getTalismanPouches() {
        return talismanPouches.get();
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        if (pPlayer instanceof ServerPlayer serverPlayer) {
            ItemStack itemStack = EntityStatusUtil.getQuickAccessItem(serverPlayer);
            if (itemStack.isEmpty())
                EntityStatusUtil.setQuickAccessSlot(serverPlayer, CurioHelper.findFirstNonEmptySlot(serverPlayer));
        }
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
