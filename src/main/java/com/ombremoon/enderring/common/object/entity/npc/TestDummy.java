package com.ombremoon.enderring.common.object.entity.npc;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.entity.ERBoss;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.common.object.entity.ISpiritAsh;
import com.ombremoon.enderring.common.object.entity.ai.behavior.attack.AnimatedMeleeBehavior;
import com.ombremoon.enderring.common.object.entity.ai.behavior.misc.RepeatableBehaviour;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.scores.Team;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.AllApplicableBehaviours;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.SequentialBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.WalkOrRunToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.RandomUtil;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.gameasset.Animations;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

//TODO: TESTING MAD PUMPKIN HEAD
public class TestDummy extends ERBoss<TestDummy> implements ISpiritAsh {
    private final Predicate<TestDummy> MELEE_ATTACK_PREDICATE = entity -> entity.getTarget() == null || !entity.getSensing().hasLineOfSight(entity.getTarget()) || !entity.isWithinMeleeAttackRange(entity.getTarget());
    @Nullable
    private UUID owner;

    public TestDummy(EntityType<TestDummy> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public int getRuneReward(Level level, BlockPos blockPos) {
        return 1100;
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
                new LookAtTarget<>(),
                new WalkOrRunToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<? extends ERMob<TestDummy>> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new TargetOrRetaliate<>()
                        .useMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
                        .attackablePredicate(target -> target.isAlive() && (!(target instanceof Player player) || !player.getAbilities().invulnerable) && !isAlliedTo(target)));
    }

    @Override
    public BrainActivityGroup<? extends ERMob<TestDummy>> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((entity, target) -> !target.isAlive() || (target instanceof Player player && player.getAbilities().invulnerable) || distanceToSqr(target.position()) > Math.pow(getAttributeValue(Attributes.FOLLOW_RANGE), 2)),
                new SetWalkTargetToAttackTarget<>()
                        .speedMod((entity, target) -> 1.25F),
                new OneRandomBehaviour<>(
                        new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.LONGSWORD_AUTO1),
                        new SequentialBehaviour<TestDummy>(
                                new AnimatedMeleeBehavior<>(7).behaviorAnim(Animations.DAGGER_AUTO1),
                                new AnimatedMeleeBehavior<>(30).behaviorAnim(Animations.DAGGER_AUTO2)
                        ).stopIf(entity -> entity.getTarget() == null || !entity.getSensing().hasLineOfSight(entity.getTarget()) || !entity.isWithinMeleeAttackRange(entity.getTarget())),
                        new AllApplicableBehaviours<TestDummy>(
                                new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO2).startCondition(entity -> RandomUtil.percentChance(0.5F)),
                                new SequentialBehaviour<>(
                                        new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO2),
                                        new OneRandomBehaviour(
                                                new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO1),
                                                new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO2))
                                )
                        ).stopIf(entity -> entity.getTarget() == null || !entity.getSensing().hasLineOfSight(entity.getTarget()) || !entity.isWithinMeleeAttackRange(entity.getTarget())),
                        new SequentialBehaviour<TestDummy>(
                                new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO1),
                                new OneRandomBehaviour(
                                        new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO2),
                                        new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO3),
                                        new SequentialBehaviour(
                                            new RepeatableBehaviour(
                                                    new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.AXE_DASH)
                                            ).repeat(3, 5),
                                                new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO1),
                                                new OneRandomBehaviour(
                                                        new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO1),
                                                        new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO2),
                                                        new AnimatedMeleeBehavior<>(20).behaviorAnim(Animations.DAGGER_AUTO3)
                                                )
                                        )
                                )
                        ).stopIf(entity -> entity.getTarget() == null || !entity.getSensing().hasLineOfSight(entity.getTarget()) || !entity.isWithinMeleeAttackRange(entity.getTarget()))
                )
        );
    }

    @Override
    public boolean isAlliedTo(Team pTeam) {
        return super.isAlliedTo(pTeam);
    }

    @Override
    public int getSummonCost() {
        return 110;
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

    public void setOwnerUUID(UUID owner) {
        this.owner = owner;
    }

    @Override
    public ERBoss.Type getBossType() {
        return Type.INSTANCE;
    }

    public static AttributeSupplier.Builder createTestDummyAttributes() {
        return createERMobAttributes().add(Attributes.MAX_HEALTH, 88.5D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR, 3.0D)
                .add(EntityAttributeInit.PHYS_DEFENSE.get(), 6.8D).add(EntityAttributeInit.MAGIC_DEFENSE.get(), 6.8D).add(EntityAttributeInit.FIRE_DEFENSE.get(), 6.8D).add(EntityAttributeInit.LIGHT_DEFENSE.get(), 6.8D).add(EntityAttributeInit.HOLY_DEFENSE.get(), 6.8D)
                .add(EntityAttributeInit.SLASH_NEGATE.get(), -10.0D).add(EntityAttributeInit.LIGHT_NEGATE.get(), -20.0D)
                .add(EntityAttributeInit.POISON_RESIST.get(), 226).add(EntityAttributeInit.SCARLET_ROT_RESIST.get(), 226).add(EntityAttributeInit.HEMORRHAGE_RESIST.get(), 169).add(EntityAttributeInit.FROSTBITE_RESIST.get(), 169).add(EntityAttributeInit.SLEEP_RESIST.get(), 310).add(EntityAttributeInit.MADNESS_RESIST.get(), -1.0D);
    }
}
