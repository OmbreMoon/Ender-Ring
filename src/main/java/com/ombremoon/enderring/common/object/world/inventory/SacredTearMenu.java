package com.ombremoon.enderring.common.object.world.inventory;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.util.FlaskUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SacredTearMenu extends UpgradeFlaskMenu {
    private static final int MAX_LEVEL = 12;

    public SacredTearMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory, inventory.player);
    }

    public SacredTearMenu(int pContainerId, Inventory inventory, Player player) {
        super(MenuTypeInit.SACRED_TEAR_MENU.get(), pContainerId, inventory, player);
    }

    @Override
    protected Type setSeedOrTear() {
        return Type.TEAR;
    }

    @Override
    protected Item getUpgradeItem() {
        return ItemInit.SACRED_TEAR.get();
    }

    @Override
    protected void upgradeFlask(ItemStack itemStack) {
        itemStack.getOrCreateTag().putInt("FlaskLevel", FlaskUtil.getFlaskLevel(itemStack) + 1);
    }

    @Override
    protected boolean canUpgradeFlask(ItemStack itemStack, Container container) {
        return super.canUpgradeFlask(itemStack, container) && FlaskUtil.getFlaskLevel(itemStack) < MAX_LEVEL;
    }
}
