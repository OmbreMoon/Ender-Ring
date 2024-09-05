package com.ombremoon.enderring.common.object.entity.spirit;

import com.ombremoon.enderring.common.object.entity.ERMob;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public abstract class ERSpiritMob <T extends ERMob<T>> extends ERMob<T> implements OwnableEntity, ISpiritAsh {
    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(ERSpiritMob.class, EntityDataSerializers.OPTIONAL_UUID);
    private final int runeReward;

    /**
     * Represents a regular ERMob that can be summoned as a spirit ash
     * @param pEntityType The entity type being created
     * @param pLevel The game level
     * @param runeReward The amount of runes to be awarded for defeating the mob, 0 if summoned
     */
    protected ERSpiritMob(EntityType<? extends ERMob> pEntityType, Level pLevel, int runeReward) {
        super(pEntityType, pLevel);
        this.runeReward = runeReward;
    }

    /**
     * Defines the owner uuid data accessor
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_UUID, Optional.empty());
    }

    /**
     * Saves owner UUID to the entities NBT data
     * @param pCompound tag attached to entity
     */
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (getOwnerUUID() != null) {
            pCompound.putUUID("Owner", getOwnerUUID());
        }
    }

    /**
     * Reads the owners UUID from the NBT data
     * @param pCompound tag attached to entity
     */
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.hasUUID("Owner")) {
            setOwnerUUID(pCompound.getUUID("Owner"));
        }
    }

    /**
     * Gets the amount of runes that the player is rewarded for defeating this entity
     * @param level the world the entity was killed in
     * @param blockPos the position of the entity on death
     * @return 0 if a spirit ash, runeReward value otherwise
     */
    @Override
    public int getRuneReward(Level level, BlockPos blockPos) {
        return getOwnerUUID() == null ? this.runeReward : 0;
    }

    /**
     * Gets the UUID of the player that summoned the spirit ash
     * @return Spirit ash summoners UUID, if not a spirit ash returns null
     */
    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).isPresent() ? this.entityData.get(OWNER_UUID).get() : null;
    }

    /**
     * Saves the UUID of the player that summoned the spirit ash
     * @param uuid the UUID of the summoning player
     */
    public void setOwnerUUID(@Nullable UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    /**
     * Checks if the target entity is allied
     * @param entity the entity to check
     * @return true if the target is allied, false otherwise
     */
    @Override
    public boolean isAlliedTo(Entity entity) {
        LivingEntity spiritOwner = this.getOwner();
        if (spiritOwner == null) return false;

        return entity.is(spiritOwner) || (entity instanceof ISpiritAsh ash && ash.getOwner() != null && ash.getOwner().is(getOwner()));
    }
}
