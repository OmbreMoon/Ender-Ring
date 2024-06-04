package com.ombremoon.enderring.common.object.entity;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.compat.epicfight.util.EFMCapabilityUtil;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.ERMobPatch;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.SimpleHumanoidMobPatch;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.server.SPPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.List;

public abstract class ERMob<T extends ERMob<T>> extends PathfinderMob implements SmartBrainOwner<ERMob<T>> {

    protected ERMob(EntityType<? extends ERMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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
        if (pDamageSource.getEntity() instanceof Player) {
            this.dropRunes();
        }
    }

    protected void dropRunes() {
        if (this.level() instanceof ServerLevel) {

        }
    }

    public abstract int getRuneReward();

    protected void playAnim(StaticAnimation animation) {
        ERMobPatch<?> mobPatch = EpicFightCapabilities.getEntityPatch(this, ERMobPatch.class);
        if (!this.level().isClientSide) {
            mobPatch.playAnimationSynchronized(animation, 0.0F, SPPlayAnimation::new);
        }
    }

    public static AttributeSupplier.Builder createERMobAttributes() {
        return Mob.createMobAttributes()
                .add(EntityAttributeInit.RUNE_LEVEL.get()).add(EntityAttributeInit.VIGOR.get()).add(EntityAttributeInit.MIND.get()).add(EntityAttributeInit.ENDURANCE.get())
                .add(EntityAttributeInit.STRENGTH.get()).add(EntityAttributeInit.DEXTERITY.get()).add(EntityAttributeInit.INTELLIGENCE.get()).add(EntityAttributeInit.FAITH.get()).add(EntityAttributeInit.ARCANE.get())
                .add(EntityAttributeInit.PHYS_DEFENSE.get()).add(EntityAttributeInit.MAGIC_DEFENSE.get()).add(EntityAttributeInit.FIRE_DEFENSE.get()).add(EntityAttributeInit.LIGHT_DEFENSE.get()).add(EntityAttributeInit.HOLY_DEFENSE.get())
                .add(EntityAttributeInit.PHYS_NEGATE.get()).add(EntityAttributeInit.MAGIC_NEGATE.get()).add(EntityAttributeInit.FIRE_NEGATE.get()).add(EntityAttributeInit.LIGHT_NEGATE.get()).add(EntityAttributeInit.HOLY_NEGATE.get())
                .add(EntityAttributeInit.STRIKE_DEFENSE.get()).add(EntityAttributeInit.SLASH_DEFENSE.get()).add(EntityAttributeInit.PIERCE_DEFENSE.get())
                .add(EntityAttributeInit.STRIKE_NEGATE.get()).add(EntityAttributeInit.SLASH_NEGATE.get()).add(EntityAttributeInit.PIERCE_NEGATE.get());
    }

}
