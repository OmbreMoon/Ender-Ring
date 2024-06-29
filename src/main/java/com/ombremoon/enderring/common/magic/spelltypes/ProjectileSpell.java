package com.ombremoon.enderring.common.magic.spelltypes;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.entity.projectile.spell.SpellProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.function.Supplier;

public abstract class ProjectileSpell<S extends ProjectileSpell<S, T>, T extends SpellProjectileEntity<S>> extends AnimatedSpell {
    private final ProjectileFactory<S, T> factory;
    private final int projectileLifetime;
    private final float projectileVelocity;
    private final boolean shootFromCatalyst;
    private final boolean canClip;
    private final float speedModifier;
    private final float gravity;
    private final int inactiveTicks;
    protected T projectile;

    public static Builder createProjectileBuilder() {
        return new Builder<>();
    }

    public ProjectileSpell(SpellType<?> spellType, ProjectileFactory<S, T> factory, Builder<S> builder) {
        super(spellType, builder);
        this.factory = factory;
        this.projectileLifetime = builder.projectileLifetime;
        this.projectileVelocity = builder.projectileVelocity;
        this.shootFromCatalyst = builder.shootFromCatalyst;
        this.canClip = builder.canClip;
        this.speedModifier = builder.speedModifier;
        this.gravity = builder.gravity;
        this.inactiveTicks = builder.inactiveTicks;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onSpellStart(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellStart(playerPatch, level, blockPos, weapon);
        this.projectile = this.factory.create(this.level, this.scaledWeapon, (S) this);
    }

    @Override
    protected void onSpellTick(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellTick(playerPatch, level, blockPos, weapon);
        Player player = playerPatch.getOriginal();
        this.projectile.setOwner(playerPatch.getOriginal());
        this.projectile.setVelocity(this.projectileVelocity);
        this.projectile.setSpeedModifier(this.speedModifier);
        this.projectile.setGravity(this.gravity);
        this.projectile.setCharge(this.getChargeAmount());

        Vec3 vec31 = this.shootFromCatalyst ? new Vec3(player.getX(), player.getEyeY() - (double)0.3F, player.getZ()) : this.projectile.initPosition(player).get(0);
        Vec3 vec32 = this.shootFromCatalyst ? new Vec3(player.getXRot(), player.getYRot(), 0.0F) : this.projectile.initRotation(player).get(0);
        this.projectile.setPos(vec31.x, vec31.y, vec31.z);
        if (this.inactiveTicks == 0) {
            this.projectile.shootFromRotation((float) vec32.x, (float) vec32.y, 0.0F, this.projectileVelocity * this.getChargeAmount(), 1.0F);
            this.projectile.setActive(true);
        } else {
            Constants.LOG.info(String.valueOf(vec32.x));
            Constants.LOG.info(String.valueOf(vec32.y));
            this.projectile.prepareShootFromRotation((float) vec32.x, (float) vec32.y, 0.0F, this.projectileVelocity * this.getChargeAmount(), 1.0F);
        }
        this.projectile.setXRot((float) vec32.x);
        this.projectile.setYRot((float) vec32.y);

        if (this.projectile.getPath() == SpellProjectileEntity.Path.HOMING) {
            this.projectile.setTargetEntity(playerPatch.getTarget());
        }
        level.addFreshEntity(this.projectile);
    }

    @Override
    protected boolean shouldTickEffect(int duration) {
        return this.isInstantSpell() ? duration == 2 : super.shouldTickEffect(duration);
    }

    public int getProjectileLifetime() {
        return this.projectileLifetime;
    }

    public float getVelocity() {
        return this.projectileVelocity;
    }

    public boolean canClip() {
        return this.canClip;
    }

    public float getSpeedModifier() {
        return this.speedModifier;
    }

    public float getGravity() {
        return this.gravity;
    }

    public int getInactiveTicks() {
        return this.inactiveTicks;
    }

    public static class Builder<T extends ProjectileSpell<T, ?>> extends AnimatedSpell.Builder<T> {
        protected int projectileLifetime;
        protected float projectileVelocity;
        protected boolean shootFromCatalyst = false;
        protected boolean canClip = false;
        protected float speedModifier = 1.0F;
        protected float gravity;
        protected int inactiveTicks;

        public Builder() {
        }

        public Builder<T> setMagicType(MagicType magicType) {
            this.magicType = magicType;
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

        public Builder<T> setCanCharge(boolean canCharge) {
            this.canCharge = canCharge;
            return this;
        }

        public Builder<T> setCastSound(SoundEvent castSound) {
            this.castSound = castSound;
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

    @FunctionalInterface
    protected interface ProjectileFactory<U extends ProjectileSpell<U, V>, V extends SpellProjectileEntity<U>> {
        V create(Level level, ScaledWeapon weapon, U spell);
    }
}
