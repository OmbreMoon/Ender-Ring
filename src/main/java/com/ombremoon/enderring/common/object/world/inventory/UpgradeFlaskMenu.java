package com.ombremoon.enderring.common.object.world.inventory;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.FlaskUtil;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

public abstract class UpgradeFlaskMenu extends AbstractContainerMenu {
    private static final int INVALID = 10;
    private static final Logger LOG = Constants.LOG;
    private final DataSlot seedOrTear = DataSlot.standalone();
    private final DataSlot slotCache = DataSlot.standalone();
    private final DataSlot canUpgrade = DataSlot.standalone();
    private final DataSlot canSwitch = DataSlot.standalone();
    private final Container container = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            super.setChanged();
            UpgradeFlaskMenu.this.slotsChanged(this);
        }
    };
    private final Player player;

    public UpgradeFlaskMenu(MenuType<?> menuType, int pContainerId, Inventory inventory, Player player) {
        super(menuType, pContainerId);
        this.player = player;

        addMenuSlots();
        addPlayerSlots(inventory);
        this.addDataSlot(this.seedOrTear).set(this.setSeedOrTear().ordinal());
        this.addDataSlot(this.slotCache).set(this.getFirstFlaskSlot(player));
        this.addDataSlot(this.canUpgrade).set(0);
        this.addDataSlot(this.canSwitch).set(0);
        this.fillFlaskSlots(player);
    }

    private void addMenuSlots() {

        this.addSlot(new Slot(this.container, 0, 44, 32) {

            //TODO: WILL BE REMOVED
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ItemInit.CRIMSON_FLASK.get()) || pStack.is(ItemInit.CERULEAN_FLASK.get());
            }
        });
        this.addSlot(new FlaskSlot(this.container, this, 1, 116, 32));
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

    private void fillFlaskSlots(Player player) {
        boolean flag1 = this.hasCrimsonInSlot(player);
        boolean flag2 = this.hasCeruleanInSlot(player);
        if (flag1 && flag2) {
            this.canSwitch.set(1);
            this.setItem(0, this.incrementStateId(), this.getFirstFlask(player));
        } else  if (flag1 || flag2) {
            this.setItem(0, this.incrementStateId(), this.getFirstFlask(player));
        }
    }

    @Override
    public void slotsChanged(Container pContainer) {
        if (pContainer == this.container) {
            ItemStack itemStack = pContainer.getItem(0);
            boolean flag = this.hasBothFlasks(this.player) || (this.hasFlaskInSlot(player) && !itemStack.is(this.getFirstFlask(player).getItem()));
            if (!itemStack.isEmpty()) {
                this.canUpgrade.set(this.canUpgradeFlask(itemStack, this.container) ? 1 : 0);
                this.canSwitch.set(flag ? 1 : 0);
            } else {
                this.canUpgrade.set(0);
                this.canSwitch.set(0);
                this.slotCache.set(0);
            }
        }
        super.slotsChanged(pContainer);
    }

    @Override
    public boolean clickMenuButton(Player pPlayer, int pId) {
        ItemStack itemStack = this.container.getItem(0);
        ItemStack itemStack1 = this.container.getItem(1);
        if (pId == 0 && this.canSwitch()) {

            if (this.getSecondFlaskSlot(pPlayer) != INVALID) {
                this.slotCache.set(this.getSecondFlaskSlot(pPlayer));
                this.setItem(0, this.incrementStateId(), this.getSecondFlask(pPlayer));
            }

            this.container.setChanged();
            this.slotsChanged(this.container);
            pPlayer.level().playSound((Player) null, pPlayer.getOnPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1.0F, pPlayer.level().random.nextFloat() * 0.1F + 0.9F);
            return true;
        } else if (pId == 1 && this.canUpgrade()) {

            this.upgradeFlask(itemStack);

            if (!pPlayer.getAbilities().instabuild) {
                itemStack1.shrink(1);
                this.container.setItem(1, ItemStack.EMPTY);
            }

            this.container.setChanged();
            this.slotsChanged(this.container);
            pPlayer.level().playSound((Player) null, pPlayer.getOnPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1.0F, pPlayer.level().random.nextFloat() * 0.1F + 0.9F);
            return true;
        } else {
            Util.logAndPauseIfInIde(pPlayer.getName() + " pressed invalid button id: " + pId);
            return false;
        }
    }

    protected abstract Type setSeedOrTear();

    protected abstract Item getUpgradeItem();

    protected abstract void upgradeFlask(ItemStack itemStack);

    protected boolean canUpgradeFlask(ItemStack itemStack, Container container) {
        return container.getItem(1).is(this.getUpgradeItem());
    }

    public boolean canUpgrade() {
        return this.canUpgrade.get() != 0;
    }

    public boolean canSwitch() {
        return this.canSwitch.get() != 0;
    }

    public boolean isSeed() {
        return this.seedOrTear.get() == 0;
    }

    public boolean isTear() {
        return this.seedOrTear.get() == 1;
    }

    private int getSlotCache() {
        return this.slotCache.get();
    }

    private ItemStack getFirstFlask(Player player) {
        return CurioHelper.getQuickAccessStacks(player).getStackInSlot(getFirstFlaskSlot(player));
    }

    private ItemStack getSecondFlask(Player player) {
        return CurioHelper.getQuickAccessStacks(player).getStackInSlot(getSecondFlaskSlot(player));
    }

    private int getFirstFlaskSlot(Player player) {
        IDynamicStackHandler stackHandler = CurioHelper.getQuickAccessStacks(player);
        for (int i = 0; i < stackHandler.getSlots() - 1; i++) {
            ItemStack itemStack = stackHandler.getStackInSlot(i);
            if (itemStack.is(ItemInit.CRIMSON_FLASK.get()) || itemStack.is(ItemInit.CERULEAN_FLASK.get())) {
                return i;
            }
        }
        return 10;
    }

    private int getSecondFlaskSlot(Player player) {
        ItemStack containerStack = this.container.getItem(0);
        IDynamicStackHandler stackHandler = CurioHelper.getQuickAccessStacks(player);
        for (int i = 0; i < stackHandler.getSlots() - 1; i++) {
            ItemStack itemStack = stackHandler.getStackInSlot(i);
            if (!itemStack.is(containerStack.getItem()) && (itemStack.is(ItemInit.CRIMSON_FLASK.get()) || itemStack.is(ItemInit.CERULEAN_FLASK.get()))) {
                return i;
            }
        }
        return 10;
    }

    private boolean hasCrimsonInSlot(Player player) {
        return FlaskUtil.getCrimsonSlot(player) != 10;
    }

    private boolean hasCeruleanInSlot(Player player) {
        return FlaskUtil.getCeruleanSlot(player) != 10;
    }

    private boolean hasFlaskInSlot(Player player) {
        return hasCrimsonInSlot(player) || hasCeruleanInSlot(player);
    }

    private boolean hasBothFlasks(Player player) {
        return hasCrimsonInSlot(player) && hasCeruleanInSlot(player);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.clearContainer(pPlayer, this.container);
    }

    @Override
    protected void clearContainer(Player pPlayer, Container pContainer) {
        if (pContainer == this.container) {
            ItemStack containerStack = pContainer.getItem(0);
            if (pPlayer.isAlive()) {
                if (containerStack.is(ItemInit.CRIMSON_FLASK.get()) || containerStack.is(ItemInit.CERULEAN_FLASK.get())) {
                    ItemStack itemStack = containerStack.copy();
                    if (this.getSlotCache() != INVALID) {
                        CurioHelper.populateSlot(pPlayer, this.getSlotCache(), itemStack);
                    } else if (this.getSlotCache() == INVALID && CurioHelper.findFirstEmptySlot(pPlayer) != INVALID) {
                        CurioHelper.populateSlot(pPlayer, itemStack);
                    }
                    pContainer.removeItem(0, 1);
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

    static class FlaskSlot extends Slot {
        public final UpgradeFlaskMenu menu;

        public FlaskSlot(Container pContainer, UpgradeFlaskMenu menu, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
            this.menu = menu;
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return pStack.is(menu.getUpgradeItem());
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }

    enum Type {
        SEED,
        TEAR
    }
}
