package com.ombremoon.enderring.event.custom;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponDamage;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;

import javax.annotation.Nullable;

public class CalculateEvent extends LivingEvent {
    private final WeaponDamage weaponDamage;
    private final ScaledWeapon scaledWeapon;

    public CalculateEvent(LivingEntity entity) {
        this(entity, null, null);
    }

    public CalculateEvent(LivingEntity entity, WeaponDamage weaponDamage) {
        this(entity, weaponDamage, null);
    }

    public CalculateEvent(LivingEntity entity, @Nullable WeaponDamage weaponDamage, @Nullable ScaledWeapon scaledWeapon) {
        super(entity);
        this.weaponDamage = weaponDamage;
        this.scaledWeapon = scaledWeapon;
    }

    public WeaponDamage getDamageType() {
        return this.weaponDamage;
    }

    public ScaledWeapon getWeapon() {
        return this.scaledWeapon;
    }

    public static class Damage extends CalculateEvent {
        private final float damageAmount;

        private float newDamageAmount;

        public Damage(LivingEntity entity, WeaponDamage weaponDamage, ScaledWeapon scaledWeapon, float damageAmount) {
            super(entity, weaponDamage, scaledWeapon);
            this.damageAmount = this.newDamageAmount = damageAmount;
        }

        public float getDamageAmount() {
            return this.damageAmount;
        }

        public float getNewDamageAmount() {
            return this.newDamageAmount;
        }

        public void setDamageAmount(float newDamageAmount) {
            this.newDamageAmount = newDamageAmount;
        }
    }

    public static class Scaling extends CalculateEvent {
        private final float scalingAmount;

        private float newScalingAmount;

        public Scaling(LivingEntity entity, WeaponDamage weaponDamage, ScaledWeapon scaledWeapon, float scalingAmount) {
            super(entity, weaponDamage, scaledWeapon);
            this.scalingAmount = this.newScalingAmount = scalingAmount;
        }

        public float getScalingAmount() {
            return this.scalingAmount;
        }

        public float getNewScalingAmount() {
            return this.newScalingAmount;
        }

        public void setScalingAmount(float newScalingAmount) {
            this.newScalingAmount = newScalingAmount;
        }
    }

    public static class Defense extends CalculateEvent {
        private final float defenseAmount;

        private float newDefenseAmount;

        public Defense(LivingEntity entity, WeaponDamage weaponDamage, float defenseAmount) {
            super(entity, weaponDamage);
            this.defenseAmount = this.newDefenseAmount = defenseAmount;
        }

        public float getDefenseAmount() {
            return this.defenseAmount;
        }

        public float getNewDefenseAmount() {
            return this.newDefenseAmount;
        }

        public void setDefenseAmount(float newDefenseAmount) {
            this.newDefenseAmount = newDefenseAmount;
        }
    }

    public static class Resistance extends CalculateEvent {
        private final float resistAmount;

        private float newResistAmount;

        public Resistance(LivingEntity entity, float resistAmount) {
            super(entity);
            this.resistAmount = this.newResistAmount = resistAmount;
        }

        public float getResistAmount() {
            return this.resistAmount;
        }

        public float getNewResistAmount() {
            return this.newResistAmount;
        }

        public void setResistAmount(float newResistAmount) {
            this.newResistAmount = newResistAmount;
        }
    }
}
