package com.ombremoon.enderring.common.object.item.equipment.weapon;

import com.google.common.collect.Maps;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.ScaledWeaponManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public interface Scalable {

    ScaledWeapon getWeapon();

    void setWeapon(ScaledWeaponManager.Wrapper wrapper);

    default Map<ReinforceType, ScaledWeapon> getAffinity() {
        return Maps.newHashMap();
    }

    default void setAffinities(ReinforceType type, ScaledWeaponManager.Wrapper wrapper) {
        this.getAffinity().put(type, wrapper.getWeapon());
    }

    default ScaledWeapon getModifiedWeapon(ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && nbt.contains("Weapon", 10)) {
            if (nbt.contains("Affinity", 8)) {
                ReinforceType type = ReinforceType.getTypeFromLocation(ResourceLocation.tryParse(nbt.getString("Affinity")));
                if (type != null) {
                    ScaledWeapon weapon = this.getAffinity().get(type);
                    if (weapon != null) {
                        nbt.put("Weapon", weapon.serializeNBT());
                        return weapon;
                    }
                }
            }
            return ScaledWeapon.create(nbt.getCompound("Weapon"));
        }
        stack.getOrCreateTag().put("Weapon", this.getWeapon().serializeNBT());
        return this.getWeapon();
    }

    default int getWeaponLevel(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getInt("WeaponLevel");
    }

    default void setWeaponLevel(ItemStack itemStack, int weaponLevel) {
        itemStack.getOrCreateTag().putInt("WeaponLevel", weaponLevel);
    }
}
