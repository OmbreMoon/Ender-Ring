package com.ombremoon.enderring.common.object.entity.projectile;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.ProjectileInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.common.object.entity.projectile.spell.SpellProjectileEntity;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ThrowingPot extends ThrowableItemProjectile {
    protected static final EntityDataAccessor<Integer> OWNER = SynchedEntityData.defineId(ThrowingPot.class, EntityDataSerializers.INT);

    public ThrowingPot(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ThrowingPot(double pX, double pY, double pZ, Level pLevel) {
        super(ProjectileInit.THROWING_POT.get(), pX, pY, pZ, pLevel);
    }

    public ThrowingPot(LivingEntity pShooter, Level pLevel) {
        super(ProjectileInit.THROWING_POT.get(), pShooter, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER, 0);
    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            double d0 = 0.08D;

            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5D) * d0, ((double) this.random.nextFloat() - 0.5D) * d0, ((double) this.random.nextFloat() - 0.5D) * d0);
            }
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        DamageUtil.conditionalHurt(this.getItemRaw(), ((AbstractWeapon)this.getDefaultItem()).getModifiedWeapon(this.getItemRaw()), this, (LivingEntity) this.getOwner(), (LivingEntity) pResult.getEntity(), 1.0F);
        if (pResult.getEntity() instanceof ERMob<?> mob) {
            Constants.LOG.info(String.valueOf(mob.getEntityData().get(ERMob.SLEEP)));
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            //TODO: SPAWN AOE CLOUD
            this.discard();
        }
    }

    @Override
    public void setOwner(@Nullable Entity pOwner) {
        super.setOwner(pOwner);
        if (pOwner != null) this.entityData.set(OWNER, pOwner.getId());
    }

    @Nullable
    @Override
    public Entity getOwner() {
        if (!this.level().isClientSide) {
            return super.getOwner();
        } else {
            return this.level().getEntity(this.entityData.get(OWNER));
        }
    }

    @Override
    protected Item getDefaultItem() {
        return this.getItemRaw().getItem();
    }
}
