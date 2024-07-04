package com.ombremoon.enderring.common.object.entity.mob.creature;

import com.ombremoon.enderring.common.init.entity.MobInit;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Torrent extends AbstractHorse implements OwnableEntity, Saddleable, PlayerRideableJumping {
    private static EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(Torrent.class, EntityDataSerializers.BYTE);
    private static EntityDataAccessor<Integer> JUMP_COUNT = SynchedEntityData.defineId(Torrent.class, EntityDataSerializers.INT);

    public Torrent(Level pLevel, Player player) {
        super(MobInit.TORRENT.get(), pLevel);
        this.setPos(player.getX(), player.getY(), player.getZ());
    }

    public Torrent(EntityType<? extends AbstractHorse> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_ID_FLAGS, (byte)0);
        this.entityData.define(JUMP_COUNT, 0);
    }

    public static AttributeSupplier.Builder createTorrentAttributes() {
        return AbstractHorse.createBaseHorseAttributes().add(Attributes.MAX_HEALTH, 77.0D);
    }

    @Override
    public void tick() {
        super.tick();
        //TODO: ADD CONFIGURABILITY
        if (this.getPassengers().isEmpty()) {
            if (this.getOwner() != null && this.getOwner() instanceof Player player) {
                player.removeVehicle();
                EntityStatusUtil.setTorrentSpawned(player, false);
                EntityStatusUtil.setTorrentHealth(player, this.getHealth());
            }
            this.discard();
        }
    }

    @Override
    public boolean canSprint() {
        return true;
    }

    @Override
    public boolean isSaddled() {
        return true;
    }

    @Override
    public boolean isTamed() {
        return true;
    }
}
