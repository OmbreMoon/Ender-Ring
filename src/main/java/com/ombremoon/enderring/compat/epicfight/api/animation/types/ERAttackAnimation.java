package com.ombremoon.enderring.compat.epicfight.api.animation.types;

import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.object.entity.LevelledMob;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Scalable;
import com.ombremoon.enderring.common.object.world.ERDamageSource;
import com.ombremoon.enderring.compat.epicfight.api.animation.AnimationProperties;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

/**
 * Only should be used for melee animations on levelled mobs
 */
public class ERAttackAnimation extends AttackAnimation {
    private final boolean handAnimation;

    public ERAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature, boolean handAnimation) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
        this.handAnimation = handAnimation;
    }

    public ERAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature, boolean handAnimation) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
        this.handAnimation = handAnimation;
    }

    public ERAttackAnimation(float convertTime, String path, Armature armature, boolean handAnimation, Phase... phases) {
        super(convertTime, path, armature, phases);
        this.handAnimation = handAnimation;
    }

    @Override
    protected void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {
        LivingEntity entity = entitypatch.getOriginal();
        if ((!(entity instanceof LevelledMob levelledMob) || levelledMob.isPlayerEnemy())) {
            super.hurtCollidingEntities(entitypatch, prevElapsedTime, elapsedTime, prevState, state, phase);
        } else {
            float prevPoseTime = prevState.attacking() ? prevElapsedTime : phase.preDelay;
            float poseTime = state.attacking() ? elapsedTime : phase.contact;
            List<Entity> list = this.getPhaseByTime(elapsedTime).getCollidingEntities(entitypatch, this, prevPoseTime, poseTime, this.getPlaySpeed(entitypatch));

            if (!list.isEmpty()) {
                HitEntityList hitEntities = new HitEntityList(entitypatch, list, phase.getProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY).orElse(HitEntityList.Priority.DISTANCE));
                int maxStrikes = this.getMaxStrikes(entitypatch, phase);

                while (entitypatch.getCurrenltyHurtEntities().size() < maxStrikes && hitEntities.next()) {
                    Entity hitten = hitEntities.getEntity();
                    LivingEntity trueEntity = this.getTrueEntity(hitten);

                    if (trueEntity != null && trueEntity.isAlive() && !entitypatch.getCurrenltyAttackedEntities().contains(trueEntity) && !entitypatch.isTeammate(hitten)) {
                        if (hitten instanceof LivingEntity || hitten instanceof PartEntity) {
                            if (entity.hasLineOfSight(hitten)) {
                                var attacks = levelledMob.getAnimationDamage();
                                List<DamageInstance> instances = null;
                                for (var pair : attacks) {
                                    if (pair.getFirst() == this) {
                                        instances = pair.getSecond();
                                        break;
                                    }
                                }
                                if (instances != null) {
                                    Item item = entity.getItemInHand(phase.hand).getItem();
                                    Scalable scalable = item instanceof Scalable ? (Scalable) item : null;
                                    for (DamageInstance damage : instances) {
                                        ERDamageSource source = DamageUtil.moddedDamageSource(entity.level(), damage.damageType(), entity);
                                        if (this.handAnimation) source.addScalable(scalable);
                                        if (trueEntity.hurt(source, damage.amount())) {
                                            hitten.level().playSound((Player) null, hitten.getX(), hitten.getY(), hitten.getZ(), this.getHitSound(entitypatch, phase), hitten.getSoundSource(), 1.0F, 1.0F);
                                            this.spawnHitParticle((ServerLevel) hitten.level(), entitypatch, hitten, phase);
                                        }
                                    }

                                    entitypatch.getCurrenltyAttackedEntities().add(trueEntity);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
