package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.common.capability.EntityStatus;
import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.entity.projectile.ThrowingPot;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.common.object.world.effect.buildup.BuildUpStatusEffect;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ThrowingPotItem extends AbstractWeapon {
    private final boolean isRoped;
    private final int fpCost;

    public ThrowingPotItem(boolean isRoped, Properties pProperties) {
        this(isRoped, 0, pProperties.stacksTo(20));
    }

    public ThrowingPotItem(boolean isRoped, int fpCost, Properties pProperties) {
        super(pProperties.stacksTo(20));
        this.isRoped = isRoped;
        this.fpCost = fpCost;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            if (EntityStatusUtil.consumeFP(pPlayer, this.fpCost, null, true)) {
                pLevel.playSound((Player)null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
                double d0 = isRoped ? -1.0 : 1.0;
                double d1 = isRoped ? 180.0 : 1.0;
                ThrowingPot throwingPot = new ThrowingPot(pPlayer, pLevel);
                throwingPot.setItem(itemStack);
                throwingPot.setOwner(pPlayer);
                throwingPot.shootFromRotation(pPlayer, (float) (d0 * pPlayer.getXRot()), (float) (d1 + pPlayer.getYRot()), 0.0F, 0.5F, 1.0F);
                pLevel.addFreshEntity(throwingPot);

                if (itemStack.is(ItemInit.FETID_POT.get()) || itemStack.is(ItemInit.ROPED_FETID_POT.get())) {
                    BuildUpStatusEffect effect = ((BuildUpStatusEffect)StatusEffectInit.POISON.get());
                    int threshold = (int) EntityStatusUtil.getEntityAttribute(pPlayer, EntityAttributeInit.IMMUNITY.get());
                    if (!pPlayer.hasEffect(effect)) {
                        pPlayer.getEntityData().set(EntityStatus.POISON, Math.min(pPlayer.getEntityData().get(EntityStatus.POISON) + 200, threshold));
                        if (pPlayer.getEntityData().get(EntityStatus.POISON) >= threshold) {
                            pPlayer.getEntityData().set(EntityStatus.POISON, 0);
                            pPlayer.addEffect(new MobEffectInstance(effect.setScaledWeapon(this.getModifiedWeapon(itemStack)), 600, 0, true, true));
                        }
                    }
                }

                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                if (!pPlayer.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
            } else {
                return InteractionResultHolder.pass(itemStack);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }
}
