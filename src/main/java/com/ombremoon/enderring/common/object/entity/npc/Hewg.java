package com.ombremoon.enderring.common.object.entity.npc;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.patches.TestDummyPatch;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.HeldBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToBlock;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.NearbyBlocksSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.List;

//TODO: ADD SHACKLE RENDER LAYER
//TODO: ADD TRADE MENU
public class Hewg extends MerchantNPCMob {
    public static final EntityDataAccessor<Boolean> SMITHING = SynchedEntityData.defineId(TestDummy.class, EntityDataSerializers.BOOLEAN);

    public Hewg(EntityType<Hewg> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SMITHING, false);
    }

    @Override
    public List<ExtendedSensor<Hewg>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new NearbyBlocksSensor<Hewg>().setRadius(10).setPredicate((blockState, livingEntity) -> blockState.is(Blocks.ANVIL)));
    }

    @Override
    public BrainActivityGroup<? extends ERMob<MerchantNPCMob>> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        new HewgIdle(),
                        new SetAnvilWalkTarget().closeEnoughWhen((testDummy, pair) -> 1),
                        new FirstApplicableBehaviour<>(
                                new SetPlayerLookTarget<>(),
                                new SetRandomLookTarget<>()),
                        new OneRandomBehaviour<>(
                                new SetRandomWalkTarget<>(),
                                new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60)))
                ));
    }

    @Override
    protected ItemStack getMainHandWeapon() {
        return new ItemStack(EquipmentInit.GUARDIAN_SWORDSPEAR.get());
    }

    @Override
    protected ItemStack getOffHandWeapon() {
        return new ItemStack(Items.SHIELD);
    }

    @Override
    public BrainActivityGroup<? extends ERMob<MerchantNPCMob>> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>(),
                new MoveToWalkTarget<>());
    }

    public void setSmithing(boolean smithing) {
        this.entityData.set(SMITHING, smithing);
    }

    public boolean isSmithing() {
        return this.entityData.get(SMITHING);
    }

    @Override
    public boolean isPushable() {
        return !this.isSmithing();
    }

    @Override
    public boolean canAggro() {
        return false;
    }

    @Override
    public int getRuneReward(Level level, BlockPos blockPos) {
        return 1000;
    }

    //DEBUGGER
    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        TestDummyPatch mobPatch = this.getEntityPatch(TestDummyPatch.class);
        if (!this.level().isClientSide) {
//            mobPatch.playAnimationSynchronized(Animations.BIPED_DEATH, 0.0F);
//            this.updateTrades();
            LOGGER.info(String.valueOf(this.getMaxHealth()));
        }
        return super.mobInteract(pPlayer, pHand);
    }

    private double getAnvilRangeSqr() {
        return this.getBbWidth() * 2.0F * this.getBbWidth() * 2.0F + 1.0F;
    }

    private double getPerceivedAnvilDistanceSquare(BlockPos blockPos) {
        return this.distanceToSqr(blockPos.getCenter());
    }

    private boolean isWithinAnvilRange(BlockPos blockPos) {
        double d0 = this.getPerceivedAnvilDistanceSquare(blockPos);
        return d0 <= this.getAnvilRangeSqr();
    }

    public static AttributeSupplier.Builder createHewgAttributes() {
        return createERMobAttributes().add(Attributes.MAX_HEALTH, 35.0D).add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(EntityAttributeInit.PHYS_DEFENSE.get(), 6.7D)
                .add(EntityAttributeInit.SLASH_NEGATE.get(), 10.0D)
                .add(EntityAttributeInit.PIERCE_NEGATE.get(), -10.0D)
                .add(EntityAttributeInit.MAGIC_DEFENSE.get(), 6.7D)
                .add(EntityAttributeInit.MAGIC_NEGATE.get(), -20.0D)
                .add(EntityAttributeInit.FIRE_DEFENSE.get(), 6.7D)
                .add(EntityAttributeInit.LIGHT_DEFENSE.get(), 6.7D)
                .add(EntityAttributeInit.HOLY_DEFENSE.get(), 6.7D);
    }

    @Override
    public ObjectArrayList<Pair<StaticAnimation, List<DamageInstance>>> getAnimationDamage() {
        return ObjectArrayList.of();
    }

    private static class HewgIdle extends HeldBehaviour<Hewg> {
        private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(SBLMemoryTypes.NEARBY_BLOCKS.get(), MemoryStatus.VALUE_PRESENT));

        protected Pair<BlockPos, BlockState> target = null;

        public HewgIdle() {

            startCondition(testDummy -> {
                var posList = BrainUtils.getMemory(testDummy, SBLMemoryTypes.NEARBY_BLOCKS.get());
                if (posList == null || posList.isEmpty())
                    return false;

                final var position = posList.get(0);
                final var blockPos = position.getFirst();
                final var blockState = position.getSecond();
                return blockPos.closerToCenterThan(testDummy.position(), 2) && !testDummy.level().getBlockState(blockPos.relative(blockState.getValue(AnvilBlock.FACING)).below()).isAir();
            });
            whenStarting(testDummy -> {
                var blockPos = this.target.getFirst();
                var blockState = this.target.getSecond();
                testDummy.setXxa(0);
                testDummy.setYya(0);
                testDummy.setZza(0);
                testDummy.setDeltaMovement(Vec3.ZERO);
                testDummy.setPos(blockPos.relative(blockState.getValue(AnvilBlock.FACING)).getCenter());
                BrainUtils.clearMemory(testDummy, MemoryModuleType.PATH);
                BrainUtils.setMemory(testDummy, MemoryModuleType.LOOK_TARGET, new BlockPosTracker(this.target.getFirst()));
                testDummy.setSmithing(true);
            });
            whenStopping(testDummy -> {
                BrainUtils.clearMemory(testDummy, MemoryModuleType.LOOK_TARGET);
                testDummy.setSmithing(false);
            });
            stopIf(testDummy -> {
                var blockPos = this.target.getFirst();
                return !testDummy.level().getBlockState(blockPos).is(Blocks.ANVIL) || !testDummy.isWithinAnvilRange(blockPos);
            });
        }

        @Override
        protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
            return MEMORY_REQUIREMENTS;
        }

        //TODO: TRANSFER TO REGULAR START CONDITION METHOD
        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, Hewg entity) {
            for (var position : BrainUtils.getMemory(entity, SBLMemoryTypes.NEARBY_BLOCKS.get())) {
                this.target = position;

                break;
            }

            return this.target != null;

        }
    }

    private static class SetAnvilWalkTarget extends SetWalkTargetToBlock<Hewg> {

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, Hewg entity) {
            for (var position : BrainUtils.getMemory(entity, SBLMemoryTypes.NEARBY_BLOCKS.get())) {
                BlockPos blockPos = position.getFirst();
                BlockState blockState = position.getSecond();
                BlockPos freePos = blockPos.relative(blockState.getValue(AnvilBlock.FACING));
                if (level.getBlockState(freePos).isAir() && !level.getBlockState(freePos.below()).isAir()) {
                    this.target = Pair.of(freePos.immutable(), level.getBlockState(freePos));
                }

                break;
            }

            return this.target != null;
        }
    }
}
