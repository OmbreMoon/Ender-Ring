package com.ombremoon.enderring.common.object.world.inventory;

import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.common.init.TagInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.FlaskUtil;
import net.minecraft.Util;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class WondrousPhysickMenu extends AbstractContainerMenu {
    private static final int MAX_TEARS = 2;
    private static final int INVALID = 10;
    private final Container crystalTearSlots = new SimpleContainer(3) {
        @Override
        public void setChanged() {
            super.setChanged();
            WondrousPhysickMenu.this.slotsChanged(this);
        }
    };
    private final DataSlot insertSlot = DataSlot.standalone();
    private final DataSlot extractSlot = DataSlot.standalone();
    private final DataSlot slotCache = DataSlot.standalone();

    public WondrousPhysickMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory);
    }

    public WondrousPhysickMenu(int pContainerId, Inventory inventory) {
        super(MenuTypeInit.WONDROUS_PHYSICK_MENU.get(), pContainerId);
        Player player = inventory.player;

        addMenuSlots();
        addPlayerSlots(inventory);

        this.addDataSlot(this.insertSlot).set(0);
        this.addDataSlot(this.extractSlot).set(0);
        this.fillFlaskSlot(player);
    }

    private void addMenuSlots() {
        this.addSlot(new Slot(this.crystalTearSlots, 0, 86, 15) {

            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(TagInit.Items.CRYSTAL_TEAR);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.crystalTearSlots, 1, 86, 60) {

            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(TagInit.Items.CRYSTAL_TEAR);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.crystalTearSlots, 2, 60, 37) {

            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ItemInit.WONDROUS_PHYSICK_FLASK.get());
            }
        });
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

    private boolean hasFilledFlask(ItemStack itemStack) {
        return !itemStack.isEmpty() && FlaskUtil.getCrystalTears(itemStack).size() >= 2;
    }

    private int getFlaskSize() {
        ItemStack itemStack = this.crystalTearSlots.getItem(2);
        return itemStack.isEmpty() ? 0 : FlaskUtil.getCrystalTears(itemStack).size();
    }

    private int getCrystalTearCount() {
        int i1 = 0;
        for (int i = 0; i < 2; i++) {
            if (this.crystalTearSlots.getItem(i) != ItemStack.EMPTY) {
                i1 += 1;
            }
        }
        return i1;
    }

    private void fillFlaskSlot(Player player) {
        if (FlaskUtil.hasPhysickInSlot(player)) {
            ItemStack itemStack = FlaskUtil.getPhysick(player);
            this.slotCache.set(FlaskUtil.getPhysickSlot(player));
            this.setItem(2, this.incrementStateId(), itemStack);
        } else {
            this.slotCache.set(INVALID);
        }
    }

    public boolean canExtract() {
        return this.extractSlot.get() != 0;
    }

    public boolean canInsert() {
        return this.insertSlot.get() != 0;
    }

    private boolean canExtractTear() {
        return this.getFlaskSize() > 0 && this.getFlaskSize() <= (MAX_TEARS - this.getCrystalTearCount());
    }

    private boolean canInsertTear(ItemStack itemStack) {
        return !this.hasFilledFlask(itemStack) && this.getCrystalTearCount() > 0 && this.getCrystalTearCount() <= (MAX_TEARS - this.getFlaskSize()) && !FlaskUtil.hasDuplicateTears(itemStack, this.crystalTearSlots);
    }

    public int getSlotCache() {
        return this.slotCache.get();
    }

    @Override
    public void slotsChanged(Container pContainer) {
        if (pContainer == this.crystalTearSlots) {
            ItemStack itemStack = pContainer.getItem(2);
            int i = this.canExtractTear() ? 1 : 0;
            int j = this.canInsertTear(itemStack) ? 1 : 0;
            if (!itemStack.isEmpty()) {
                this.insertSlot.set(j);
                this.extractSlot.set(i);
            } else {
                this.insertSlot.set(0);
                this.extractSlot.set(0);
            }
        }
        super.slotsChanged(pContainer);
    }

    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        ItemStack itemStack = this.crystalTearSlots.getItem(0);
        ItemStack itemStack1 = this.crystalTearSlots.getItem(1);
        ItemStack itemStack2 = this.crystalTearSlots.getItem(2);
        if (pId == 0 && this.canInsert()) {
            if ((itemStack.isEmpty() && itemStack1.isEmpty()) && !pPlayer.getAbilities().instabuild) {
                return false;
            } else {

                FlaskUtil.setCrystalTears(this.crystalTearSlots, itemStack2);

                if (!pPlayer.getAbilities().instabuild) {
                    for (int i = 0; i < MAX_TEARS; i++) {
                        ItemStack stack = this.crystalTearSlots.getItem(i);
                        if (!stack.isEmpty()) {
                            stack.shrink(1);
                            this.crystalTearSlots.setItem(i, ItemStack.EMPTY);
                        }
                    }
                }

                this.crystalTearSlots.setChanged();
                this.slotsChanged(this.crystalTearSlots);
                pPlayer.level().playSound((Player) null, pPlayer.getOnPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1.0F, pPlayer.level().random.nextFloat() * 0.1F + 0.9F);
                return true;
            }
        } else if (pId == 1 && this.canExtract()) {

            ListTag listTag = FlaskUtil.getCrystalTears(itemStack2);
            for (int i = 0; i < listTag.size(); i++) {
                ItemStack stack = new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(FlaskUtil.getCrystalTear(listTag.getCompound(i)))));
                this.crystalTearSlots.setItem(this.findFirstEmptySlot(), stack);
            }

            listTag.clear();

            this.crystalTearSlots.setChanged();
            this.slotsChanged(this.crystalTearSlots);
            pPlayer.level().playSound((Player) null, pPlayer.getOnPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1.0F, pPlayer.level().random.nextFloat() * 0.1F + 0.9F);
            return true;
        } else {
            Util.logAndPauseIfInIde(pPlayer.getName() + " pressed invalid button id: " + pId);
            return false;
        }
    }

    private int findFirstEmptySlot() {
        for (int i = 0; i < MAX_TEARS; i++) {
            if (this.crystalTearSlots.getItem(i).isEmpty()) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.clearContainer(pPlayer, this.crystalTearSlots);
    }


    @Override
    protected void clearContainer(Player pPlayer, Container pContainer) {
        if (pContainer == this.crystalTearSlots) {
            if (pPlayer.isAlive()) {
                if (pContainer.getItem(2).is(ItemInit.WONDROUS_PHYSICK_FLASK.get())) {
                    ItemStack itemStack = pContainer.getItem(2).copy();
                    if (this.getSlotCache() != INVALID || (this.getSlotCache() == INVALID && CurioHelper.findFirstEmptySlot(pPlayer) != INVALID)) {
                        CurioHelper.populateSlot(pPlayer, itemStack);
                        pContainer.removeItem(2, 1);
                    } else {
                        super.clearContainer(pPlayer, pContainer);
                    }
                }
            }
        }
        super.clearContainer(pPlayer, pContainer);
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
