package com.ombremoon.enderring.common.object.entity.npc;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.entity.ERBoss;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.common.object.entity.ERMonster;
import com.ombremoon.enderring.common.object.entity.ai.behavior.attack.AnimatedMeleeBehavior;
import com.ombremoon.enderring.common.object.entity.ai.behavior.misc.RepeatableBehaviour;
import com.ombremoon.enderring.common.object.entity.boss.MadPumpkinHead;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
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

import java.util.List;

//TODO: TESTING LESSER RUNEBEAR
public class TestDummy extends ERBoss<TestDummy> {
    public TestDummy(EntityType<TestDummy> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public int getRuneReward(Level level, BlockPos blockPos) {
        return 0;
    }

    @Override
    public List<? extends ExtendedSensor<? extends ERMob<TestDummy>>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<TestDummy>()
                        .setPredicate((target, entity) ->
                                target instanceof Player)
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
                new SetWalkTargetToAttackTarget<>(),
                new OneRandomBehaviour<>(
                        new AnimatedMeleeBehavior<>(20),
                        new AnimatedMeleeBehavior<>(20),
                        new AnimatedMeleeBehavior<>(20),
                        new SequentialBehaviour<>(
                                new AnimatedMeleeBehavior<>(20),
                                new AnimatedMeleeBehavior<>(20)
                        ),
                        new SequentialBehaviour<>(
                                new AnimatedMeleeBehavior<>(20),
                                new AnimatedMeleeBehavior<>(20)
                        ),
                        new AnimatedMeleeBehavior<>(20),
                        longCombo(),
                        new SequentialBehaviour<>(
                                new AnimatedMeleeBehavior<>(20),
                                new OneRandomBehaviour<>(
                                        longCombo(),
                                        new AnimatedMeleeBehavior<>(20)
                                )
                        ),
                        new SequentialBehaviour<>(
                                new AnimatedMeleeBehavior<>(20),
                                new AnimatedMeleeBehavior<>(20)
                        )
                )
        );
    }

    private SequentialBehaviour<TestDummy> longCombo() {
        return new SequentialBehaviour<>(
                new AnimatedMeleeBehavior<>(20),
                new OneRandomBehaviour<>(
                        new AnimatedMeleeBehavior<>(20),
                        new RepeatableBehaviour<>(
                                new AnimatedMeleeBehavior<>(20)
                        ).repeat(2),
                        new SequentialBehaviour<>(
                                new AnimatedMeleeBehavior<>(20),
                                new AnimatedMeleeBehavior<>(20)
                        )
                )
        );
    }

    @Override
    public Type getBossType() {
        return Type.INSTANCE;
    }

    public static AttributeSupplier.Builder createTestDummyAttributes() {
        return createERMobAttributes().add(Attributes.MAX_HEALTH, 100.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ARMOR, 3.0D)
                .add(EntityAttributeInit.PHYS_DEFENSE.get(), 6.8D).add(EntityAttributeInit.MAGIC_DEFENSE.get(), 6.8D).add(EntityAttributeInit.FIRE_DEFENSE.get(), 6.8D).add(EntityAttributeInit.LIGHT_DEFENSE.get(), 6.8D).add(EntityAttributeInit.HOLY_DEFENSE.get(), 6.8D)
                .add(EntityAttributeInit.STRIKE_NEGATE.get(), 35.0D).add(EntityAttributeInit.MAGIC_NEGATE.get(), 20.0D).add(EntityAttributeInit.FIRE_NEGATE.get(), 20.0D).add(EntityAttributeInit.LIGHT_NEGATE.get(), -20.0D)
                .add(EntityAttributeInit.POISON_RESIST.get(), IMMUNE).add(EntityAttributeInit.SCARLET_ROT_RESIST.get(), IMMUNE).add(EntityAttributeInit.HEMORRHAGE_RESIST.get(), IMMUNE).add(EntityAttributeInit.FROSTBITE_RESIST.get(), IMMUNE).add(EntityAttributeInit.SLEEP_RESIST.get(), IMMUNE).add(EntityAttributeInit.MADNESS_RESIST.get(), IMMUNE);
    }
}
