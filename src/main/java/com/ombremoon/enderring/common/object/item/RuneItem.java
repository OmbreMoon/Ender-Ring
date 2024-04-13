package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class RuneItem extends Item {
    private final int runeAmount;

    public RuneItem(Properties pProperties, int runeAmount) {
        super(pProperties);
        this.runeAmount = runeAmount;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            PlayerStatusUtil.increaseRunes(pPlayer, this.runeAmount);
            if (!pPlayer.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }

        pLevel.playSound((Player) null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1.0F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        pPlayer.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    public int getRuneAmount() {
        return this.runeAmount;
    }
}
