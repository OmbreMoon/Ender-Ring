package com.ombremoon.enderring.common.object.item.equipment.weapon;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.Saturation;
import com.ombremoon.enderring.common.data.ScaledWeaponManager;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponTypeReloadListener;
import yesman.epicfight.world.capabilities.provider.ItemCapabilityProvider;

import java.util.Arrays;
import java.util.WeakHashMap;

public class AbstractWeapon extends Item {
    private ScaledWeapon weapon = new ScaledWeapon();

    public AbstractWeapon(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    public void setWeapon(ScaledWeaponManager.Wrapper wrapper) {
        this.weapon = wrapper.getWeapon();
    }

    public ScaledWeapon getWeapon() {
        return this.weapon;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            CapabilityItem w = ItemCapabilityProvider.get(this);
            if (w instanceof ExtendedWeaponCapability extendedWeaponCapability) {
//                extendedWeaponCapability.swapAshOfWar(itemStack, EpicFightSkills.SWEEPING_EDGE);
                Constants.LOG.info(String.valueOf(extendedWeaponCapability.getAshOfWar(itemStack)));
            }
            Constants.LOG.info(String.valueOf(this.getModifiedWeapon(itemStack).serializeNBT()));

        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        Player player = ((Player) pAttacker);
        player.sendSystemMessage(Component.literal(String.valueOf(DamageUtil.getWeaponAP(this.getModifiedWeapon(pStack), player, this.getWeaponLevel(pStack)))));
        player.sendSystemMessage(Component.literal(String.valueOf(this.getModifiedWeapon(pStack).getDamage().getScaledDamageMap(this.getModifiedWeapon(pStack), player, this.getWeaponLevel(pStack)))));
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public int getWeaponLevel(ItemStack itemStack) {
        return itemStack.getTag().getInt("WeaponLevel");
    }

    public ScaledWeapon getModifiedWeapon(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("Weapon", 10)) {
            return ScaledWeapon.create(nbt.getCompound("Weapon"));
        }
        return this.weapon;
    }

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        return !pPlayer.isCreative();
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return false;
    }
}
