package com.ombremoon.enderring.common.object.entity;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.common.object.world.LevelledList;
import com.ombremoon.enderring.common.object.world.LevelledLists;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.ERMobPatch;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.registries.ForgeRegistries;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;

import java.util.Optional;

public abstract class ERMob<T extends ERMob<T>> extends PathfinderMob implements SmartBrainOwner<ERMob<T>> {
    protected static final Logger LOGGER = Constants.LOG;
    protected static final EntityDataAccessor<Float> POISON = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> SCARLET_ROT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> BLOOD_LOSS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> FROSTBITE = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> SLEEP = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> MADNESS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> DEATH_BLIGHT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.FLOAT);

    protected ERMob(EntityType<? extends ERMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(POISON, 0.0F);
        this.getEntityData().define(SCARLET_ROT, 0.0F);
        this.getEntityData().define(BLOOD_LOSS, 0.0F);
        this.getEntityData().define(FROSTBITE, 0.0F);
        this.getEntityData().define(SLEEP, 0.0F);
        this.getEntityData().define(MADNESS, 0.0F);
        this.getEntityData().define(DEATH_BLIGHT, 0.0F);
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
        this.setItemSlot(EquipmentSlot.HEAD, this.getHeadArmor());
        this.setItemSlot(EquipmentSlot.CHEST, this.getChestArmor());
        this.setItemSlot(EquipmentSlot.LEGS, this.getLegArmor());
        this.setItemSlot(EquipmentSlot.FEET, this.getFeetArmor());
        this.setDropChance(EquipmentSlot.MAINHAND, this.getMainHandWeaponDropChance());
        this.setDropChance(EquipmentSlot.OFFHAND, this.getOffHandWeaponDropChance());
        this.setDropChance(EquipmentSlot.HEAD, this.getHeadArmorDropChance());
        this.setDropChance(EquipmentSlot.CHEST, this.getChestArmorDropChance());
        this.setDropChance(EquipmentSlot.LEGS, this.getLegArmorDropChance());
        this.setDropChance(EquipmentSlot.FEET, this.getFeetArmorDropChance());
    }

    protected ItemStack getMainHandWeapon() {
        return ItemStack.EMPTY;
    }

    protected ItemStack getOffHandWeapon() {
        return ItemStack.EMPTY;
    }

    protected ItemStack getHeadArmor() {
        return ItemStack.EMPTY;
    }

    protected ItemStack getChestArmor() {
        return ItemStack.EMPTY;
    }

    protected ItemStack getLegArmor() {
        return ItemStack.EMPTY;
    }

    protected ItemStack getFeetArmor() {
        return ItemStack.EMPTY;
    }

    protected float getMainHandWeaponDropChance() {
        return 0.0F;
    }

    protected float getOffHandWeaponDropChance() {
        return 0.0F;
    }

    protected float getHeadArmorDropChance() {
        return 0.0F;
    }

    protected float getChestArmorDropChance() {
        return 0.0F;
    }

    protected float getLegArmorDropChance() {
        return 0.0F;
    }

    protected float getFeetArmorDropChance() {
        return 0.0F;
    }

    //TODO: CHANGE TO UNIVERSAL VALUE ENUM
    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        Optional<ResourceKey<Biome>> optional = pLevel.getBiome(this.getOnPos()).unwrapKey();
        if (optional.isPresent()) {
            ResourceKey<Biome> biome = optional.get();
            for (var levelledList : LevelledLists.values()) {
                if (levelledList.getBiome() == biome && this instanceof LevelledMob) {
                    this.scaleStats(levelledList);
                }
            }
        }
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
                .add(EntityAttributeInit.STRIKE_NEGATE.get()).add(EntityAttributeInit.SLASH_NEGATE.get()).add(EntityAttributeInit.PIERCE_NEGATE.get());
    }
}
