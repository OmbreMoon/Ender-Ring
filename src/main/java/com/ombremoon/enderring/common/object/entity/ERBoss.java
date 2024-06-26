package com.ombremoon.enderring.common.object.entity;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class ERBoss<T extends ERMonster<T>> extends ERMonster<T> {
    protected final ServerBossEvent bossEvent = new ServerBossEvent(getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.NOTCHED_20);

    protected ERBoss(EntityType<T> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer pServerPlayer) {
        super.startSeenByPlayer(pServerPlayer);
        this.bossEvent.addPlayer(pServerPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer pServerPlayer) {
        super.stopSeenByPlayer(pServerPlayer);
        this.bossEvent.removePlayer(pServerPlayer);
    }

    public Type getBossType() {
        return Type.FIELD;
    }

    protected enum Type {
        SHARDBEARER,
        GREATER,
        FIELD,
        INSTANCE
    }
}
