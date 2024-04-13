package com.ombremoon.enderring.common.object.item.equipment.weapon;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.data.ScaledWeaponManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.WeakHashMap;

public abstract class AbstractWeapon extends Item {
    private WeakHashMap<CompoundTag, ScaledWeapon> scaledWeaponCache = new WeakHashMap<>();
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
            Constants.LOG.info(String.valueOf(weapon.serializeNBT()));

        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    /*public ScaledWeapon getModifiedWeapon(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("Weapon", 10)) {
            return this.scaledWeaponCache.computeIfAbsent(nbt, item -> {
                ScaledWeapon scaledWeapon = this.weapon.copy();
                scaledWeapon.deserializeNBT(nbt.getCompound("Weapon"));
                return scaledWeapon;
            });
        }
        return this.weapon;
    }*/
}
