package com.ombremoon.enderring.common.object.entity.mob.creature;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.common.object.entity.spirit.ERSpiritMob;
import com.ombremoon.enderring.common.object.entity.ai.behavior.attack.AnimatedMeleeBehavior;
import com.ombremoon.enderring.common.object.entity.ai.behavior.attack.AnimatedRangedAttack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.SequentialBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.WalkOrRunToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;

import java.util.List;
import java.util.function.Predicate;

public class SpiritJellyfish extends ERSpiritMob<SpiritJellyfish> implements RangedAttackMob {
    public static final EntityDataAccessor<Boolean> IS_AGGRO = SynchedEntityData.defineId(SpiritJellyfish.class, EntityDataSerializers.BOOLEAN);

    public SpiritJellyfish(EntityType<? extends ERMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel, 33);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_AGGRO, false);
    }

    @Override
    public List<? extends ExtendedSensor<? extends ERMob<SpiritJellyfish>>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<SpiritJellyfish>().setScanRate(entity -> 40),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends ERMob<SpiritJellyfish>> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new WalkOrRunToWalkTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends ERMob<SpiritJellyfish>> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new TargetOrRetaliate<>()
                        .useMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
                        .attackablePredicate(neutralAttackCondition()),
                new Idle<SpiritJellyfish>()
                        .whenStarting(spiritJellyfish -> {
                            BrainUtils.clearMemory(spiritJellyfish, MemoryModuleType.LOOK_TARGET);
                            spiritJellyfish.setAggro(false);
                        })
                        .whenStopping(spiritJellyfish -> spiritJellyfish.setAggro(true))
        );
    }

    @Override
    public BrainActivityGroup<? extends ERMob<SpiritJellyfish>> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new LookAtTarget<>(),
                new SequentialBehaviour<>(
                        new Idle<>().runFor(livingEntity -> 40),
                        new FirstApplicableBehaviour<>(
                                new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.GREATSWORD_DASH),
                                new AnimatedRangedAttack<>(20).behaviorAnim(Animations.BIPED_BOW_SHOT)
                        )
                )
        );
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        boolean hurt = super.hurt(pSource, pAmount);
        if (hurt) this.setAggro(true);
        return hurt;
    }

    public boolean isAggro() {
        return this.entityData.get(IS_AGGRO);
    }

    public void setAggro(boolean isAggro) {
        this.entityData.set(IS_AGGRO, isAggro);
    }

    public int getSummonCost() {
        return 31;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return null;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {

    }

    @Override
    protected Predicate<LivingEntity> neutralAttackCondition() {
        if (this.getBrain() == null) return livingEntity -> false; //this.getBrain() is sometimes null don't believe the lies
        Entity target = BrainUtils.getMemory(this, MemoryModuleType.HURT_BY_ENTITY);
        return livingEntity -> target != null && target.getUUID() == livingEntity.getUUID() && !isAlliedTo(livingEntity);
    }

    @Override
    public ObjectArrayList<Pair<StaticAnimation, List<DamageInstance>>> getAnimationDamage() {
        return ObjectArrayList.of();
    }

    public static AttributeSupplier.Builder createSpiritJellyfishAttributes() {
        return createERMobAttributes().add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR, 3.0D)
                .add(EntityAttributeInit.PHYS_DEFENSE.get(), 6.8D).add(EntityAttributeInit.MAGIC_DEFENSE.get(), 6.8D).add(EntityAttributeInit.FIRE_DEFENSE.get(), 6.8D).add(EntityAttributeInit.LIGHT_DEFENSE.get(), 6.8D).add(EntityAttributeInit.HOLY_DEFENSE.get(), 6.8D)
                .add(EntityAttributeInit.STRIKE_NEGATE.get(), 35.0D).add(EntityAttributeInit.MAGIC_NEGATE.get(), 20.0D).add(EntityAttributeInit.FIRE_NEGATE.get(), 20.0D).add(EntityAttributeInit.LIGHT_NEGATE.get(), -20.0D)
                .add(EntityAttributeInit.POISON_RESIST.get(), IMMUNE).add(EntityAttributeInit.SCARLET_ROT_RESIST.get(), IMMUNE).add(EntityAttributeInit.HEMORRHAGE_RESIST.get(), IMMUNE).add(EntityAttributeInit.FROSTBITE_RESIST.get(), IMMUNE).add(EntityAttributeInit.SLEEP_RESIST.get(), IMMUNE).add(EntityAttributeInit.MADNESS_RESIST.get(), IMMUNE);
    }
}
