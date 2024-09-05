package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.compat.epicfight.util.EFMUtil;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class TestCatalystItem extends Item {
    public TestCatalystItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        SpellType<?> spell = EntityStatusUtil.getSelectedSpell(pPlayer);
        if (spell != null) {
            itemStack.getOrCreateTag().putInt("CastTime", spell.createSpell().getCastTime());
            AbstractSpell abstractSpell = spell.createSpell();

            /*if (abstractSpell != null && abstractSpell.getCastType() != AbstractSpell.CastType.INSTANT) {
                pPlayer.startUsingItem(pUsedHand);
            } else {
                if (!pLevel.isClientSide) {
                    abstractSpell.initSpell(playerPatch, pLevel, pLivingEntity.getOnPos(), this.getModifiedWeapon(pStack), DamageUtil.calculateMagicScaling(weapon, player, level, weaponDamage), false, this.classifications, this.spellBoost);
                }
            }*/
            /*if (!pLevel.isClientSide) {
                ServerPlayer player = (ServerPlayer) pPlayer;
                ServerPlayerPatch playerPatch = EFMUtil.getServerPlayerPatch(player);
                playerPatch.playAnimationSynchronized(Animations.BIPED_DRINK, 0.0F);
            }*/
            return InteractionResultHolder.consume(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (!pLevel.isClientSide) {
            if (pRemainingUseDuration == this.getUseDuration(pStack)) {
                ServerPlayer player = (ServerPlayer) pLivingEntity;
                ServerPlayerPatch playerPatch = EFMUtil.getServerPlayerPatch(player);
                playerPatch.playAnimationSynchronized(Animations.BIPED_SPYGLASS_USE, 0.0F);
            } else if (pRemainingUseDuration == 1) {
                Constants.LOG.info("1");
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return pStack.getOrCreateTag().getInt("CastTime");
    }
}
