package com.ombremoon.enderring.common.object.item.equipment;

import com.ombremoon.enderring.common.object.entity.ISpiritAsh;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpiritCallingBellItem extends Item implements IQuickAccess {
    public SpiritCallingBellItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            EntityType<?> entityType = EntityStatusUtil.getSpiritSummon(pPlayer);
            if (entityType != null) {
                LivingEntity livingEntity = (LivingEntity) entityType.create(pLevel);
                if (this.canSpawnSummon(pPlayer, livingEntity)) {
                    pLevel.addFreshEntity(livingEntity);
                }
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    private boolean canSpawnSummon(Player player, LivingEntity livingEntity) {
        return livingEntity instanceof ISpiritAsh spiritAsh && EntityStatusUtil.getFP(player) >= spiritAsh.getSummonCost();
    }
}
