package com.ombremoon.enderring.common.object.item.equipment;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpiritCallingBellItem extends QuickAccessItem {
    public SpiritCallingBellItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public void useItem(Player player, Level level, InteractionHand usedHand, ItemStack itemStack) {

    }
}
