package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.event.FirstSpawnEvent;
import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DebugItem extends Item {

    public DebugItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            if (pPlayer.isCrouching()) {
                ModNetworking.getInstance().openCharBaseSelectScreen(FirstSpawnEvent.CHARACTER_BASE, (ServerPlayer) pPlayer);
            } else {
                ItemStack stack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
                if (stack.getItem() instanceof AbstractWeapon weapon) {
                    Constants.LOG.info(String.valueOf(weapon.getWeapon().getBaseStats().getMaxUpgrades()));
                    Constants.LOG.info(String.valueOf(weapon.getWeapon().getBaseStats().isInfusable()));
                    Constants.LOG.info(String.valueOf(weapon.getWeapon().getBaseStats().hasTwoHandBonus()));
                }
            }

//            PlayerStatusUtil.getStatusAttributeModifiers(pPlayer).clear();
            if (pPlayer.isInWater()) {
                DamageSource damageSource = new DamageSource(pPlayer.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ModDamageTypes.PHYSICAL));
                pPlayer.hurt(damageSource, 10.0F);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }
}