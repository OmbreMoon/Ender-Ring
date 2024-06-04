package com.ombremoon.enderring.common.object.entity;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.entity.ai.behavior.misc.AnimatedIdle;
import com.ombremoon.enderring.common.object.entity.npc.MerchantNPCMob;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.TestDummyPatch;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyBlocksSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.List;

//TODO: TESTING HEWG
public class TestDummy extends MerchantNPCMob {
    public TestDummy(EntityType<? extends TestDummy> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public List<ExtendedSensor<? extends MerchantNPCMob>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new NearbyBlocksSensor<TestDummy>().setPredicate((blockState, livingEntity) -> blockState.is(Blocks.ANVIL)));
    }

    @Override
    public BrainActivityGroup<? extends ERMob<MerchantNPCMob>> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<TestDummy>(
                        new AnimatedIdle<>(this, Animations.BIPED_CLIMBING)).startCondition(testDummy -> testDummy.getHealth() < testDummy.getMaxHealth()),
                        new SetPlayerLookTarget<>(),
                        new SetRandomLookTarget<>());
    }

    @Override
    public BrainActivityGroup<? extends ERMob<MerchantNPCMob>> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }

    @Override
    public boolean canAggro() {
        return false;
    }

    @Override
    public int getRuneReward() {
        return 1000;
    }


    //DEBUGGER
    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        TestDummyPatch mobPatch = EpicFightCapabilities.getEntityPatch(this, TestDummyPatch.class);
        if (!this.level().isClientSide) {
            mobPatch.playAnimationSynchronized(Animations.BIPED_DEATH, 0.0F);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    public static AttributeSupplier.Builder createTestDummyAttributes() {
        return createERMobAttributes().add(Attributes.MAX_HEALTH, 35)
                .add(EntityAttributeInit.PHYS_DEFENSE.get(), 6.7)
                .add(EntityAttributeInit.SLASH_NEGATE.get(), 0.1D)
                .add(EntityAttributeInit.PIERCE_NEGATE.get(), -0.1D)
                .add(EntityAttributeInit.MAGIC_DEFENSE.get(), 6.7)
                .add(EntityAttributeInit.MAGIC_NEGATE.get(), -0.2D)
                .add(EntityAttributeInit.FIRE_DEFENSE.get(), 6.7)
                .add(EntityAttributeInit.LIGHT_DEFENSE.get(), 6.7)
                .add(EntityAttributeInit.HOLY_DEFENSE.get(), 6.7);
    }
}
