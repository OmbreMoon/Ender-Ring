package com.ombremoon.enderring.common.object.item.equipment.weapon;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.data.ScaledWeaponManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface Scalable {

    ScaledWeapon getWeapon();

    void setWeapon(ScaledWeaponManager.Wrapper wrapper);

    default ScaledWeapon getModifiedWeapon(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("Weapon", 10)) {
            return ScaledWeapon.create(nbt.getCompound("Weapon"));
        }
        return this.getWeapon();
    }

    default int getWeaponLevel(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getInt("WeaponLevel");
    }

    default void setWeaponLevel(ItemStack itemStack, int weaponLevel) {
        itemStack.getOrCreateTag().putInt("WeaponLevel", weaponLevel);
    }
}
