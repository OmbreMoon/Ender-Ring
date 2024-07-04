package com.ombremoon.enderring.common.object.entity.npc;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.common.object.entity.ISpiritAsh;
import com.ombremoon.enderring.common.object.entity.ai.behavior.attack.AnimatedMeleeBehavior;
import com.ombremoon.enderring.common.object.entity.ai.behavior.attack.AnimatedRangedAttack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
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
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
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
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.gameasset.Animations;

import java.util.List;
import java.util.UUID;

//TODO: TESTING SPIRIT JELLYFISH
public class TestDummy extends ERMob<TestDummy> implements ISpiritAsh, RangedAttackMob {
    public static final EntityDataAccessor<Boolean> IS_AGGRO = SynchedEntityData.defineId(TestDummy.class, EntityDataSerializers.BOOLEAN);
    @Nullable
    private UUID owner;

    public TestDummy(EntityType<? extends ERMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_AGGRO, false);
    }

    @Override
    public int getRuneReward(Level level, BlockPos blockPos) {
        return 33;
    }

    @Override
    public List<? extends ExtendedSensor<? extends ERMob<TestDummy>>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<TestDummy>().setScanRate(entity -> 40),
                new HurtBySensor<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends ERMob<TestDummy>> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>()
        );
    }

    @Override
    public BrainActivityGroup<? extends ERMob<TestDummy>> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new TargetOrRetaliate<>()
                        .attackablePredicate(livingEntity -> {
                            Entity target = BrainUtils.getMemory(this, MemoryModuleType.HURT_BY_ENTITY);
//                            boolean spiritAsh = this.getOwnerUUID() == livingEntity.getUUID();
                            boolean normal = this.isAggro() && target != null && target.getUUID() == livingEntity.getUUID() && this.getOwnerUUID() != livingEntity.getUUID();
                            return normal;
                        }),
                new Idle<TestDummy>()
                        .whenStarting(testDummy -> {
                            BrainUtils.clearMemory(testDummy, MemoryModuleType.LOOK_TARGET);
                            testDummy.setAggro(false);
                        })
                        .whenStopping(testDummy -> testDummy.setAggro(true))
        );
    }

    @Override
    public BrainActivityGroup<? extends ERMob<TestDummy>> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new LookAtTarget<>(),
                new SequentialBehaviour<>(
                        new Idle<>().runFor(livingEntity -> 20),
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

    @Override
    public int getSummonCost() {
        return 31;
    }

    @Override
    public ResourceLocation getTextureLocation() {
        return null;
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.owner;
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pVelocity) {

    }

    public static AttributeSupplier.Builder createTestDummyAttributes() {
        return createERMobAttributes().add(Attributes.MAX_HEALTH, 15.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR, 3.0D)
                .add(EntityAttributeInit.PHYS_DEFENSE.get(), 6.8D).add(EntityAttributeInit.MAGIC_DEFENSE.get(), 6.8D).add(EntityAttributeInit.FIRE_DEFENSE.get(), 6.8D).add(EntityAttributeInit.LIGHT_DEFENSE.get(), 6.8D).add(EntityAttributeInit.HOLY_DEFENSE.get(), 6.8D)
                .add(EntityAttributeInit.STRIKE_NEGATE.get(), 35.0D).add(EntityAttributeInit.MAGIC_NEGATE.get(), 20.0D).add(EntityAttributeInit.FIRE_NEGATE.get(), 20.0D).add(EntityAttributeInit.LIGHT_NEGATE.get(), -20.0D)
                .add(EntityAttributeInit.POISON_RESIST.get(), IMMUNE).add(EntityAttributeInit.SCARLET_ROT_RESIST.get(), IMMUNE).add(EntityAttributeInit.HEMORRHAGE_RESIST.get(), IMMUNE).add(EntityAttributeInit.FROSTBITE_RESIST.get(), IMMUNE).add(EntityAttributeInit.SLEEP_RESIST.get(), IMMUNE).add(EntityAttributeInit.MADNESS_RESIST.get(), IMMUNE);
    }
}
