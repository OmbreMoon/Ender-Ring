package com.ombremoon.enderring.common.object.world.inventory;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.util.FlaskUtil;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class UpgradeFlaskMenu extends AbstractContainerMenu {
    private static final int INVALID = 10;
    private static final int FLASK_SLOTS = 2;
    private final DataSlot upgradeType = DataSlot.standalone();
    private final int[] slotCache = new int[2];
    private final Container flaskSlots = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            super.setChanged();
            UpgradeFlaskMenu.this.slotsChanged(this);
        }
    };

    public UpgradeFlaskMenu(MenuType<?> menuType, int pContainerId, Inventory inventory) {
        super(menuType, pContainerId);

        addMenuSlots();
        addPlayerSlots(inventory);
        this.addDataSlot(this.upgradeType).set(this.setUpgradeType());
        this.addDataSlot(DataSlot.shared(slotCache, 0));
        this.addDataSlot(DataSlot.shared(slotCache, 1));
        this.fillFlaskSlots(inventory.player);
    }

    private void addMenuSlots() {

        this.addSlot(new Slot(this.flaskSlots, 0, 86, 15) {

            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ItemInit.CRIMSON_FLASK.get()) || pStack.is(ItemInit.CERULEAN_FLASK.get());
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.flaskSlots, 1, 86, 60) {

            @Override
            public boolean mayPlace(ItemStack pStack) {
                return pStack.is(ItemInit.GOLDEN_SEED.get()) || pStack.is(ItemInit.SACRED_TEAR.get());
            }

            @Override
            public int getMaxStackSize() {
                return 1;
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

    private void fillFlaskSlots(Player player) {
        boolean flag1 = FlaskUtil.hasCrimsonInSlot(player);
        boolean flag2 = FlaskUtil.hasCeruleanInSlot(player);
        if (flag1 && flag2) {
            this.slotCache[0] = FlaskUtil.getCrimsonSlot(player);
            this.slotCache[1] = FlaskUtil.getCeruleanSlot(player);
            this.setItem(0, this.incrementStateId(), FlaskUtil.getCrimsonFlask(player));
            this.setItem(1, this.incrementStateId(), FlaskUtil.getCeruleanFlask(player));
        } else  if (flag1) {
            this.slotCache[0] = FlaskUtil.getCrimsonSlot(player);
            this.setItem(0, this.incrementStateId(), FlaskUtil.getCrimsonFlask(player));
        }  else  if (flag2) {
            this.slotCache[0] = FlaskUtil.getCeruleanSlot(player);
            this.setItem(0, this.incrementStateId(), FlaskUtil.getCeruleanFlask(player));
        } else {
            for (int i = 0; i < FLASK_SLOTS; i++) {
                this.slotCache[i] = INVALID;
            }
        }
    }

    public boolean hasCrimsonFlask() {
        return this.slotCache[0] != 10;
    }

    public boolean hasCeruleanFlask() {
        return this.slotCache[1] != 10;
    }

    @Override
    public void slotsChanged(Container pContainer) {
        if (pContainer == this.flaskSlots) {
            ItemStack itemStack = pContainer.getItem(0);
        }
        super.slotsChanged(pContainer);
    }

    protected abstract int setUpgradeType();

    protected int getUpgradeType() {
        return this.upgradeType.get();
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        Constants.LOG.info(String.valueOf(this.getUpgradeType()));
    }
}
