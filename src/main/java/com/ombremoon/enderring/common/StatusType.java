package com.ombremoon.enderring.common;

import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.common.object.world.effect.buildup.BuildUpStatusEffect;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;

public enum StatusType {
    POISON(StatusEffectInit.POISON.get(), PlayerStatus.POISON, ERMob.POISON, EntityAttributeInit.IMMUNITY.get(), EntityAttributeInit.POISON_RESIST.get()),
    SCARLET_ROT(StatusEffectInit.SCARLET_ROT.get(), PlayerStatus.SCARLET_ROT, ERMob.SCARLET_ROT, EntityAttributeInit.IMMUNITY.get(), EntityAttributeInit.SCARLET_ROT_RESIST.get()),
    BLOOD_LOSS(StatusEffectInit.BLOOD_LOSS.get(), PlayerStatus.BLOOD_LOSS, ERMob.BLOOD_LOSS, EntityAttributeInit.ROBUSTNESS.get(), EntityAttributeInit.HEMORRHAGE_RESIST.get()),
    FROSTBITE(StatusEffectInit.FROSTBITE.get(), PlayerStatus.FROSTBITE, ERMob.FROSTBITE, EntityAttributeInit.ROBUSTNESS.get(), EntityAttributeInit.FROSTBITE_RESIST.get()),
    SLEEP(StatusEffectInit.SLEEP.get(), PlayerStatus.SLEEP, ERMob.SLEEP, EntityAttributeInit.FOCUS.get(), EntityAttributeInit.SLEEP_RESIST.get()),
    MADNESS(StatusEffectInit.MADNESS.get(), PlayerStatus.MADNESS, ERMob.MADNESS, EntityAttributeInit.FOCUS.get(), EntityAttributeInit.MADNESS_RESIST.get()),
    DEATH_BLIGHT(StatusEffectInit.DEATH_BLIGHT.get(), PlayerStatus.DEATH_BLIGHT, ERMob.DEATH_BLIGHT, EntityAttributeInit.VITALITY.get(), EntityAttributeInit.DEATH_BLIGHT_RESIST.get());

    private final MobEffect effect;
    private final EntityDataAccessor<Integer> playerStatus;
    private final EntityDataAccessor<Integer> entityStatus;
    private final Attribute playerResist;
    private final Attribute entityResist;

    StatusType(MobEffect effect, EntityDataAccessor<Integer> playerStatus, EntityDataAccessor<Integer> entityStatus, Attribute playerResist, Attribute entityResist) {
        this.effect = effect;
        this.playerStatus = playerStatus;
        this.entityStatus = entityStatus;
        this.playerResist = playerResist;
        this.entityResist = entityResist;
    }

    public BuildUpStatusEffect getEffect() {
        return (BuildUpStatusEffect) this.effect;
    }

    public EntityDataAccessor<Integer> getPlayerStatus() {
        return this.playerStatus;
    }

    public EntityDataAccessor<Integer> getEntityStatus() {
        return this.entityStatus;
    }

    public Attribute getPlayerResist() {
        return this.playerResist;
    }

    public Attribute getEntityResist() {
        return this.entityResist;
    }
}
