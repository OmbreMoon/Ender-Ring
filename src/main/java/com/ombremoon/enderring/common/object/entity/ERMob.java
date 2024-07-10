package com.ombremoon.enderring.common.object.entity;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.world.LevelledList;
import com.ombremoon.enderring.common.object.world.LevelledLists;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.ERMobPatch;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class ERMob<T extends ERMob<T>> extends PathfinderMob implements LevelledMob, SmartBrainOwner<ERMob<T>> {
    protected static final Logger LOGGER = Constants.LOG;
    public static final EntityDataAccessor<Boolean> SPIRIT_ASH = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> POISON = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SCARLET_ROT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> BLOOD_LOSS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FROSTBITE = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SLEEP = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MADNESS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DEATH_BLIGHT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    protected static int IMMUNE = -1;

    protected ERMob(EntityType<? extends ERMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(SPIRIT_ASH, false);
        this.getEntityData().define(POISON, 0);
        this.getEntityData().define(SCARLET_ROT, 0);
        this.getEntityData().define(BLOOD_LOSS, 0);
        this.getEntityData().define(FROSTBITE, 0);
        this.getEntityData().define(SLEEP, 0);
        this.getEntityData().define(MADNESS, 0);
        this.getEntityData().define(DEATH_BLIGHT, 0);
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
    }

    public boolean shouldDropRunes() {
        return true;
    }

    @Override
    protected boolean shouldDropLoot() {
        return true;
    }

    @Override
    protected void dropAllDeathLoot(DamageSource pDamageSource) {
        super.dropAllDeathLoot(pDamageSource);
        if (pDamageSource.getEntity() instanceof Player player) {
            this.dropRunes(player);
        }
    }

    protected void dropRunes(Player player) {
        if (this.level() instanceof ServerLevel) {
            EntityStatusUtil.increaseRunes(player, this.getRuneReward(this.level(), this.getOnPos()));
        }
    }

    public abstract int getRuneReward(Level level, BlockPos blockPos);

    public <P extends EntityPatch<?>> P getEntityPatch(Class<P> clazz) {
        return EpicFightCapabilities.getEntityPatch(this, clazz);
    }

    protected void playAnim(StaticAnimation animation) {
        ERMobPatch<?> mobPatch = this.getEntityPatch(ERMobPatch.class);
        if (!this.level().isClientSide) {
            mobPatch.playAnimationSynchronized(animation, 0.0F, SPPlayAnimation::new);
        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, this.getMainHandWeapon());
        this.setItemSlot(EquipmentSlot.OFFHAND, this.getOffHandWeapon());
        this.setDropChance(EquipmentSlot.MAINHAND, this.getMainHandWeaponDropChance());
        this.setDropChance(EquipmentSlot.OFFHAND, this.getOffHandWeaponDropChance());
    }

    protected ItemStack getMainHandWeapon() {
        return ItemStack.EMPTY;
    }

    protected ItemStack getOffHandWeapon() {
        return ItemStack.EMPTY;
    }

    protected float getMainHandWeaponDropChance() {
        return 0.0F;
    }

    protected float getOffHandWeaponDropChance() {
        return 0.0F;
    }

    public boolean isSpiritAsh() {
        return this.entityData.get(SPIRIT_ASH);
    }

    public void setSpiritAsh(boolean spiritAsh) {
        this.entityData.set(SPIRIT_ASH, spiritAsh);
    }

    protected Predicate<LivingEntity> neutralAttackCondition() {
        if (this.getBrain() != null) {
            Entity target = BrainUtils.getMemory(this, MemoryModuleType.HURT_BY_ENTITY);
            return livingEntity -> target != null && target.getUUID() == livingEntity.getUUID();
        }
        return livingEntity -> false;
    }

    //TODO: CHANGE TO UNIVERSAL VALUE ENUM
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        /*Optional<ResourceKey<Biome>> optional = pLevel.getBiome(this.getOnPos()).unwrapKey();
        if (optional.isPresent()) {
            ResourceKey<Biome> biome = optional.get();
            for (var levelledList : LevelledLists.values()) {
                if (levelledList.getBiome() == biome) {
                    this.scaleStats(levelledList);
                }
            }
        }*/
        this.scaleStats(this, (livingEntity, levelledList) -> this.scaleStats(levelledList));
        this.populateDefaultEquipmentSlots(pLevel.getRandom(), pDifficulty);
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    protected void scaleStats(LevelledList levelledList) {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(this.getMaxHealth() * levelledList.getMaxHPMult());
        this.setHealth(this.getMaxHealth());
    }

    public static AttributeSupplier.Builder createERMobAttributes() {
        return Mob.createMobAttributes()
                .add(EntityAttributeInit.RUNE_LEVEL.get()).add(EntityAttributeInit.VIGOR.get()).add(EntityAttributeInit.MIND.get()).add(EntityAttributeInit.ENDURANCE.get())
                .add(EntityAttributeInit.STRENGTH.get()).add(EntityAttributeInit.DEXTERITY.get()).add(EntityAttributeInit.INTELLIGENCE.get()).add(EntityAttributeInit.FAITH.get()).add(EntityAttributeInit.ARCANE.get())
                .add(EntityAttributeInit.PHYS_DEFENSE.get()).add(EntityAttributeInit.MAGIC_DEFENSE.get()).add(EntityAttributeInit.FIRE_DEFENSE.get()).add(EntityAttributeInit.LIGHT_DEFENSE.get()).add(EntityAttributeInit.HOLY_DEFENSE.get())
                .add(EntityAttributeInit.PHYS_NEGATE.get()).add(EntityAttributeInit.MAGIC_NEGATE.get()).add(EntityAttributeInit.FIRE_NEGATE.get()).add(EntityAttributeInit.LIGHT_NEGATE.get()).add(EntityAttributeInit.HOLY_NEGATE.get())
                .add(EntityAttributeInit.STRIKE_NEGATE.get()).add(EntityAttributeInit.SLASH_NEGATE.get()).add(EntityAttributeInit.PIERCE_NEGATE.get())
                .add(EntityAttributeInit.POISON_RESIST.get()).add(EntityAttributeInit.SCARLET_ROT_RESIST.get()).add(EntityAttributeInit.HEMORRHAGE_RESIST.get()).add(EntityAttributeInit.FROSTBITE_RESIST.get()).add(EntityAttributeInit.SLEEP_RESIST.get()).add(EntityAttributeInit.MADNESS_RESIST.get()).add(EntityAttributeInit.DEATH_BLIGHT_RESIST.get());
    }
}
