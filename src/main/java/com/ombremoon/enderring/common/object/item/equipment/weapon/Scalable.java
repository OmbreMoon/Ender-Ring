package com.ombremoon.enderring.common.object.item.equipment.weapon;

import com.google.common.collect.Maps;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.ScaledWeaponManager;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

    default void meleeHurt(ItemStack itemStack, ScaledWeapon scaledWeapon, LivingEntity attackEntity, LivingEntity targetEntity) {
        meleeHurt(itemStack, scaledWeapon, attackEntity, targetEntity, 1.0F);
    }

    default void meleeHurt(ItemStack itemStack, ScaledWeapon scaledWeapon, LivingEntity attackEntity, LivingEntity targetEntity, float motionValue) {
        conditionalHurt(itemStack, scaledWeapon, attackEntity, attackEntity, targetEntity, motionValue);
    }

    default void conditionalHurt(ItemStack itemStack, ScaledWeapon scaledWeapon, Entity directEntity, LivingEntity attackEntity, LivingEntity targetEntity, float motionValue) {
        for (WeaponDamage weaponDamage : WeaponDamage.values()) {
            DamageSource damageSource;
            float typeDamage = weaponDamage.getDamageFunction().apply(scaledWeapon, attackEntity, this.getWeaponLevel(itemStack));

            //Ensures that type damage is 1 when between 0 and 1
            if (typeDamage > 0) {
                damageSource = DamageUtil.moddedDamageSource(attackEntity.level(), weaponDamage.getDamageType(), directEntity, attackEntity, this, scaledWeapon.getDamage().getPhysDamageTypes());
                typeDamage = Math.max(typeDamage, 1.0F);

                if (targetEntity.hurt(damageSource, motionValue != 0 ? typeDamage * motionValue : typeDamage)) {
                    DamageUtil.handleStatusBuildUp(attackEntity, targetEntity, scaledWeapon, null, damageSource);
                }
            }
        }
    }

    default int getWeaponLevel(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getInt("WeaponLevel");
    }

    default void setWeaponLevel(ItemStack itemStack, int weaponLevel) {
        itemStack.getOrCreateTag().putInt("WeaponLevel", weaponLevel);
    }

    default float getAttackDamage(LivingEntity livingEntity) {
        return 1.0F;
    }
}
