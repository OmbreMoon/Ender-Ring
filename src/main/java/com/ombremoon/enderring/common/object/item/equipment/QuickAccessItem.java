package com.ombremoon.enderring.common.object.item.equipment;

import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

public abstract class QuickAccessItem extends Item implements IQuickAccess {
    private final boolean hasAnimation;

    public QuickAccessItem(Properties pProperties) {
        this(pProperties, false);
    }

    public QuickAccessItem(Properties pProperties, boolean hasAnimation) {
        super(pProperties);
        this.hasAnimation = hasAnimation;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (this.hasAnimation) {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
        }

        this.useItem(pPlayer, pLevel, pUsedHand, itemStack);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }
}
