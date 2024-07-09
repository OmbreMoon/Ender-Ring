package com.ombremoon.enderring.common.object.item.equipment.weapon;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.ScaledWeaponManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface Scalable {

    //TODO: REMOVE
    ScaledWeapon getWeapon();

    void setWeapon(ScaledWeaponManager.Wrapper wrapper);

    Map<ReinforceType, ScaledWeapon> getAffinity();

    void setAffinities(ReinforceType type, ScaledWeaponManager.Wrapper wrapper);

    default ScaledWeapon getModifiedWeapon(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null) {
            if (nbt.contains("Affinity", 8)) {
                ReinforceType type = ReinforceType.getTypeFromLocation(ResourceLocation.tryParse(nbt.getString("Affinity")));
                if (type != null) {
                    ScaledWeapon weapon = this.getAffinity().get(type);
                    if (weapon != null) return weapon;
                }
            } else if (nbt.contains("Weapon", 10)) {
                return ScaledWeapon.create(nbt.getCompound("Weapon"));
            }
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
