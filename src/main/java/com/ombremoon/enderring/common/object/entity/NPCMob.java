package com.ombremoon.enderring.common.object.entity;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.common.object.entity.npc.Merchant;
import com.ombremoon.enderring.common.object.entity.npc.NPCData;
import com.ombremoon.enderring.common.object.entity.npc.NPCDataHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class NPCMob<T extends NPCMob<T>> extends ERMob<T> implements NeutralMob, NPCDataHolder, Npc {
    public static final EntityDataSerializer<NPCData> NPC_DATA_SERIALIZER = new EntityDataSerializer.ForValueType<>() {
        @Override
        public void write(FriendlyByteBuf pBuffer, NPCData pValue) {
            pBuffer.writeResourceLocation(pValue.getMerchant().resourceLocation());
        }

        @Override
        public NPCData read(FriendlyByteBuf pBuffer) {
            return new NPCData(Merchant.getMerchantFromLocation(pBuffer.readResourceLocation()));
        }
    };
    private static final EntityDataAccessor<NPCData> NPC_DATA = SynchedEntityData.defineId(NPCMob.class, NPC_DATA_SERIALIZER);
    private static final EntityDataAccessor<Integer> AGGRO_COUNTER = SynchedEntityData.defineId(NPCMob.class, EntityDataSerializers.INT);

    protected NPCMob(EntityType<? extends NPCMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(NPC_DATA, new NPCData(Merchant.TEST));
        this.entityData.define(AGGRO_COUNTER, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        NPCData.CODEC.encodeStart(NbtOps.INSTANCE, this.getNPCData()).resultOrPartial(LOGGER::error).ifPresent(tag -> {
            pCompound.put("NPCData", tag);
        });
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("NPCData", 10)) {
            DataResult<NPCData> dataResult = NPCData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, pCompound.get("NPCData")));
            dataResult.resultOrPartial(LOGGER::error).ifPresent(this::setNPCData);
        }
    }

    public boolean canAggro() {
        return ConfigHandler.AGGRO_NPC.get();
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int pRemainingPersistentAngerTime) {

    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID pPersistentAngerTarget) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    @Override
    public void setNPCData(NPCData data) {
        this.entityData.set(NPC_DATA, data);
    }

    @Override
    public NPCData getNPCData() {
        return this.entityData.get(NPC_DATA);
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }
}
