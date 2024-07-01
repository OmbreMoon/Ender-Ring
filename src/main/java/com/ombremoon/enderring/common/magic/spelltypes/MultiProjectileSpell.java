package com.ombremoon.enderring.common.magic.spelltypes;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.magic.Classification;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.entity.projectile.spell.SpellProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.function.Supplier;

public abstract class MultiProjectileSpell<S extends MultiProjectileSpell<S, T>, T extends SpellProjectileEntity<S>> extends ProjectileSpell<S, T> {
    private final int projectileCount;

    public static Builder createMultiProjectileBuilder() {
        return new Builder<>();
    }

    public MultiProjectileSpell(SpellType<?> spellType, ProjectileFactory<S, T> factory, Builder<S> builder) {
        super(spellType, factory, builder);
        this.projectileCount = builder.projectileCount;
    }

    @Override
    protected void onSpellTick(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        if (this.projectileID < this.projectileCount) {
            createProjectile(playerPatch, this.projectileID);
            LOGGER.info(String.valueOf(this.projectileID));
            this.projectileID++;
        }
    }

    public static class Builder<T extends MultiProjectileSpell<T, ?>> extends ProjectileSpell.Builder<T> {
        protected int projectileCount = 2;

        public Builder() {
        }

        public Builder<T> setClassification(Classification classification) {
            this.classification = classification;
            return this;
        }

        public Builder<T> setRequirements(WeaponScaling weaponScaling, int statReq) {
            this.createReqList(weaponScaling, statReq);
            return this;
        }

        public Builder<T> setFPCost(int fpCost) {
            this.fpCost = fpCost;
            return this;
        }

        public Builder<T> setStaminaCost(int staminaCost) {
            this.staminaCost = staminaCost;
            return this;
        }

        public Builder<T> setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder<T> setMotionValue(float motionValue) {
            this.motionValue = motionValue;
            return this;
        }

        public Builder<T> setChargedMotionValue(float motionValue) {
            this.chargedMotionValue = motionValue;
            return this;
        }

        public Builder<T> canCharge() {
            this.canCharge = true;
            return this;
        }

        public Builder<T> setCastSound(SoundEvent castSound) {
            this.castSound = castSound;
            return this;
        }

        public Builder<T> setCount(int count) {
            this.projectileCount = count;
            if (count < 2) throw new IllegalArgumentException(String.format("Projectile spell count too small: %s", count));
            return this;
        }

        public Builder<T> setLifetime(int lifetime) {
            this.projectileLifetime = lifetime;
            return this;
        }

        public Builder<T> setVelocity(float velocity) {
            this.projectileVelocity = velocity;
            return this;
        }

        public Builder<T> shootFromCatalyst() {
            this.shootFromCatalyst = true;
            return this;
        }

        public Builder<T> canClip() {
            this.canClip = true;
            return this;
        }

        public Builder<T> setSpeedModifier(float modifier) {
            this.speedModifier = modifier;
            return this;
        }

        public Builder<T> setGravity(float gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder<T> setInactiveTicks(int inactiveTicks) {
            this.inactiveTicks = inactiveTicks;
            return this;
        }

        public Builder<T> setAnimation(Supplier<StaticAnimation> animation) {
            this.spellAnimation = animation;
            return this;
        }
    }
}
