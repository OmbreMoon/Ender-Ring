package com.ombremoon.enderring.common.object.world.inventory;

import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.item.equipment.FlaskItem;
import com.ombremoon.enderring.util.FlaskUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class GoldenSeedMenu extends UpgradeFlaskMenu {
    private static final int MAX_CHARGES = 14;

    public GoldenSeedMenu(int pContainerId, Inventory inventory, FriendlyByteBuf buf) {
        this(pContainerId, inventory, inventory.player);
    }

    public GoldenSeedMenu(int pContainerId, Inventory inventory, Player player) {
        super(MenuTypeInit.GOLDEN_SEED_MENU.get(), pContainerId, inventory, player);
    }

    @Override
    protected Type setSeedOrTear() {
        return Type.SEED;
    }

    @Override
    protected Item getUpgradeItem() {
        return ItemInit.GOLDEN_SEED.get();
    }

    @Override
    protected void upgradeFlask(ItemStack itemStack) {
        itemStack.getOrCreateTag().putInt("MaxCharges", FlaskUtil.getMaxCharges(itemStack) + 1);
        FlaskUtil.setCharges(itemStack, FlaskUtil.getMaxCharges(itemStack));
    }

    @Override
    protected boolean canUpgradeFlask(ItemStack itemStack, Container container) {
        return super.canUpgradeFlask(itemStack, container) && FlaskUtil.getMaxCharges(itemStack) < MAX_CHARGES;
    }
}
