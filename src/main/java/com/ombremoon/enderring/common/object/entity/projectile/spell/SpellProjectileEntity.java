package com.ombremoon.enderring.common.object.entity.projectile.spell;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.magic.spelltypes.ProjectileSpell;
import com.ombremoon.enderring.common.object.entity.projectile.PathConsumer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public abstract class SpellProjectileEntity<T extends ProjectileSpell<?, ?>> extends Projectile {
    protected static final Logger LOGGER = Constants.LOG;
    protected static final EntityDataAccessor<Float> SPEED_MODIFIER = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> GRAVITY = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> VELOCITY = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> CHARGE = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> TARGET = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> INACTIVE = SynchedEntityData.defineId(SpellProjectileEntity.class, EntityDataSerializers.INT);
    private ScaledWeapon weapon;
    private T projectileSpell;
    private int lifetime;
    private int targetEntity;
    private boolean isActive;

    protected SpellProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    protected SpellProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel, ScaledWeapon weapon, T projectileSpell) {
        this(pEntityType, pLevel);
        this.weapon = weapon;
        this.projectileSpell = projectileSpell;
        this.lifetime = projectileSpell.getProjectileLifetime();
    }

    public ScaledWeapon getWeapon() {
        return this.weapon;
    }

    public T getProjectileSpell() {
        return this.projectileSpell;
    }

    public void setProjectileSpell(T projectileSpell) {
        this.projectileSpell = projectileSpell;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SPEED_MODIFIER, 1.0F);
        this.entityData.define(GRAVITY, 0.0F);
        this.entityData.define(VELOCITY, 0.0F);
        this.entityData.define(CHARGE, 1.0F);
        this.entityData.define(OWNER, 0);
        this.entityData.define(TARGET, 0);
        this.entityData.define(INACTIVE, 0);
    }

    /**
     * Sets a target for the client to interpolate towards over the next few ticks
     */
    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.setPos(pX, pY, pZ);
        this.setRot(pYaw, pPitch);
    }

    /**
     * Updates the entity motion clientside, called by packets from the server
     */
    public void lerpMotion(double pX, double pY, double pZ) {
        super.lerpMotion(pX, pY, pZ);
    }

    @Override
    public void tick() {
        super.tick();
//        this.discard();
        if (!this.level().isClientSide && this.projectileSpell == null) this.discard();
        Vec3 vec3 = this.getDeltaMovement();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }

        Vec3 vec32 = this.position();
        Vec3 vec33 = vec32.add(vec3);
        HitResult hitResult = this.level().clip(new ClipContext(vec32, vec33, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (hitResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
            vec33 = hitResult.getLocation();
        }

        if (!this.isRemoved()) {
            EntityHitResult entityHitResult = this.findHitEntity(vec32, vec33);
            if (entityHitResult != null) {
                hitResult = entityHitResult;
            }

            if (hitResult.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult)hitResult).getEntity();
                Entity entity1 = this.getOwner();
                if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
                    hitResult = null;
                    entityHitResult = null;
                }
            }

            if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
                this.onHit(hitResult);
                this.hasImpulse = true;
            }

            hitResult = null;
        }

        if (this.isRemoved())
            return;

        if (this.tickCount == this.getInactiveTicks()) {
            this.isActive = true;
            this.shootFromInactivity(this.getXRot(), this.getYRot(), 0.0F, this.getVelocity() * this.getCharge(), 1.0F);
        }

        vec3 = this.getDeltaMovement();
        double d0 = vec3.x;
        double d1 = vec3.y;
        double d2 = vec3.z;
        double d3 = this.getX() + d0;
        double d4 = this.getY() + d1;
        double d5 = this.getZ() + d2;
        double d6 = vec3.horizontalDistance();
        Path path = this.getPath();
        float f = this.getSpeedModifier();
        if (this.isActive) {
            switch (path) {
                case SPLINE -> {
                    LOGGER.info(String.valueOf(this.lifetime));
                }
                case QUAD -> {
                    this.setYRot((float) (-Mth.atan2(d0, d2) * (double) (180F / (float) Math.PI)));
                    this.setXRot((float) (-Mth.atan2(d1, d6) * (double) (180F / (float) Math.PI)));
                    this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
                    this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
                    this.setDeltaMovement(vec3.scale((double) f));
                    if (!this.isNoGravity()) {
                        Vec3 vec34 = this.getDeltaMovement();
                        this.setDeltaMovement(vec34.x, vec34.y - this.getGravity(), vec34.z);
                    }
                }
                case HOMING -> {
                    LivingEntity homingEntity = this.getTargetEntity();
                    if (homingEntity != null && homingEntity.isAlive()) {
                        double distance = homingEntity.distanceToSqr(this);
                        Vec3 direction = new Vec3(homingEntity.getX() - this.getX(), homingEntity.getEyeY() - 0.3F - this.getY(), homingEntity.getZ() - this.getZ());
                        if (distance > 0) {
                            direction = direction.normalize();
                            double d7 = direction.horizontalDistance();
                            Vec3 vec35 = this.getDeltaMovement();
                            this.setYRot((float) (-Mth.atan2(direction.x, direction.z) * (double) (180F / (float) Math.PI)));
                            this.setXRot((float) (-Mth.atan2(direction.y, d7) * (double) (180F / (float) Math.PI)));
                            this.setXRot(lerpRotation(this.xRotO, this.getXRot()));
                            this.setYRot(lerpRotation(this.yRotO, this.getYRot()));
//                            this.setDeltaMovement(vec35.subtract(direction).scale((double) -f));
                            this.setDeltaMovement(vec35.add(direction).scale((double) f * f));
                        }
                    } else if (homingEntity != null && !homingEntity.isAlive()) {
                        this.discard();
                    } else {
                        this.setDeltaMovement(vec3.scale((double) f));
                    }
                }
                case CUSTOM -> {
                    PathConsumer pathConsumer = this.getCustomPath();
                    pathConsumer.accept(this);
                }
                default -> this.setDeltaMovement(vec3.scale((double) f));
            }
            this.setPos(d3, d4, d5);
        }/* else {
            LivingEntity livingEntity = (LivingEntity) this.getOwner();
            if (livingEntity != null) {
                vec3 = this.getDeltaMovement();
                double d10 = vec3.x;
                double d11 = vec3.y;
                double d12 = vec3.z;
                double d13 = this.getX() + d10;
                double d14 = this.getY() + d11;
                double d15 = this.getZ() + d12;
                Vec3 vec31 = this.getOwner().getDeltaMovement();
                this.setDeltaMovement(vec31.x, vec31.y > 0 || vec31.y < -0.2 ? vec31.y : 0, vec31.z);
                this.setPos(d13, d14, d15);
            }
        }*/
        if (!this.level().isClientSide) {
            this.tickDespawn();
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity entity = pResult.getEntity();
        if (!this.level().isClientSide) {
            Entity owner = this.getOwner();
            if (entity instanceof LivingEntity livingEntity && livingEntity != owner) {
                this.getProjectileSpell().checkHurt(livingEntity);
                if (owner instanceof LivingEntity entity1) {
                    entity1.setLastHurtMob(livingEntity);
                }
            }
            if (this.stopOnEnemyHit()) {
                this.discard();
            }
        }
        this.level().playSound(this, entity.getOnPos(), this.getHitSound(), SoundSource.NEUTRAL, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        if (!this.level().isClientSide && !this.projectileSpell.canClip()) {
            this.discard();
        } else {
            this.playSound(this.getHitSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        }
    }

    /**
     * Gets the EntityHitResult representing the entity hit
     */
    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, pStartVec, pEndVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    public void prepareShootFromRotation(float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        this.prepareShoot((double) f, (double) f1, (double) f2, pVelocity, pInaccuracy);
    }

    public void shootFromRotation(float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        this.shoot((double) f, (double) f1, (double) f2, pVelocity, pInaccuracy);
    }

    public void shootFromInactivity(float pX, float pY, float pZ, float pVelocity, float pInaccuracy) {
        float f = -Mth.sin(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((pX + pZ) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(pY * ((float)Math.PI / 180F)) * Mth.cos(pX * ((float)Math.PI / 180F));
        Vec3 vec3 = (new Vec3(f, f1, f2)).normalize().add(this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy), this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy), this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy)).scale((double)pVelocity);
        this.setDeltaMovement(vec3);
    }

    public void prepareShoot(double pX, double pY, double pZ, float pVelocity, float pInaccuracy) {
        Vec3 vec3 = (new Vec3(pX, pY, pZ)).normalize().add(this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy), this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy), this.random.triangle(0.0D, 0.0172275D * (double)pInaccuracy)).scale((double)pVelocity);
        double d0 = vec3.horizontalDistance();
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }

    protected void tickDespawn() {
        this.lifetime--;
        if (this.lifetime <= 0) {
            this.discard();
        }
    }

    protected PathConsumer getCustomPath() {
        return spellProjectileEntity -> {

        };
    }

    protected Vec3 getTargetVector(LivingEntity target) {
        Vec3 vec3 = target.getPosition(0.1F);
        return this.position().vectorTo(vec3).normalize();
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public Path getPath() {
        return Path.STRAIGHT;
    }

    public void setSpeedModifier(float speedModifier) {
        this.entityData.set(SPEED_MODIFIER, speedModifier);
    }

    public void setGravity(float gravity) {
        this.entityData.set(GRAVITY, gravity);
    }

    public void setVelocity(float velocity) {
        this.entityData.set(VELOCITY, velocity);
    }

    public void setCharge(float charge) {
        this.entityData.set(CHARGE, charge);
    }

    public void setInactiveTicks(int ticks) {
        this.entityData.set(INACTIVE, ticks);
    }

    public float getSpeedModifier() {
        if (!this.level().isClientSide) {
            if (projectileSpell != null) {
                return this.projectileSpell.getSpeedModifier();
            } else {
                return 1.0F;
            }
        } else {
            return this.entityData.get(SPEED_MODIFIER);
        }
    }

    public float getVelocity() {
        if (!this.level().isClientSide) {
            if (projectileSpell != null) {
                return this.projectileSpell.getVelocity();
            } else {
                return 0.0F;
            }
        } else {
            return this.entityData.get(VELOCITY);
        }
    }


    public float getGravity() {
        if (!this.level().isClientSide) {
            if (projectileSpell != null) {
                return this.projectileSpell.getGravity();
            } else {
                return 0.0F;
            }
        } else {
            return this.entityData.get(GRAVITY);
        }
    }


    public float getCharge() {
        if (!this.level().isClientSide) {
            if (projectileSpell != null) {
                return this.projectileSpell.getChargeAmount();
            } else {
                return 1.0F;
            }
        } else {
            return this.entityData.get(CHARGE);
        }
    }


    public int getInactiveTicks() {
        if (!this.level().isClientSide) {
            if (projectileSpell != null) {
                return this.projectileSpell.getInactiveTicks();
            } else {
                return 0;
            }
        } else {
            return this.entityData.get(INACTIVE);
        }
    }

    @Override
    public void setOwner(@Nullable Entity pOwner) {
        super.setOwner(pOwner);
        if (pOwner != null) {
            this.entityData.set(OWNER, pOwner.getId());
        }
    }

    @Nullable
    @Override
    public Entity getOwner() {
        if (!this.level().isClientSide) {
            return super.getOwner();
        } else {
            return getEntity(this.entityData.get(OWNER));
        }
    }

    public void setTargetEntity(LivingEntity targetEntity) {
        if (targetEntity != null) {
            this.targetEntity = targetEntity.getId();
            this.entityData.set(TARGET, targetEntity.getId());
        }
    }

    protected LivingEntity getTargetEntity() {
        if (!this.level().isClientSide) {
            return getEntity(this.targetEntity);
        } else {
            return getEntity(this.entityData.get(TARGET));
        }
    }

    private LivingEntity getEntity(int targetEntity) {
        return (LivingEntity) this.level().getEntity(targetEntity);
    }

    public List<Vec3> initPosition(LivingEntity owner) {
        return List.of();
    }

    public List<Vec3> initRotation(LivingEntity owner) {
        return List.of();
    }

    protected Vec3 getDefaultSpawnPos(LivingEntity owner) {
        return new Vec3(owner.getX(), owner.getEyeY() - (double)0.3F, owner.getZ());
    }

    protected Vec3 getDefaultRotation(LivingEntity owner) {
        return new Vec3(owner.getXRot(), owner.getYRot(), 0.0F);
    }

    protected boolean stopOnEnemyHit() {
        return true;
    }

    protected SoundEvent getHitSound() {
        return SoundEvents.GHAST_SHOOT;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private static void log(Object o) {
        LOGGER.info(String.valueOf(o));
    }

    public enum Path {
        STRAIGHT,
        QUAD,
        SPLINE,
        HOMING,
        CUSTOM;
    }
}
