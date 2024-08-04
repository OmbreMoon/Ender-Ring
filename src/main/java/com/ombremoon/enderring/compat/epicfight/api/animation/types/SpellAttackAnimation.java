package com.ombremoon.enderring.compat.epicfight.api.animation.types;

import com.google.common.collect.Maps;
import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.object.entity.IPlayerEnemy;
import com.ombremoon.enderring.common.object.entity.LevelledMob;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Scalable;
import com.ombremoon.enderring.common.object.world.ERDamageSource;
import com.ombremoon.enderring.compat.epicfight.api.animation.AnimationProperties;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
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
import java.util.Map;

public class SpellAttackAnimation extends AttackAnimation {

    public SpellAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
    }

    public SpellAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
    }

    public SpellAttackAnimation(float convertTime, String path, Armature armature, Phase... phases) {
        super(convertTime, path, armature, phases);
    }

    protected Map<WeaponDamage, Float> getSpellDamage(Phase phase) {
        Map<WeaponDamage, Float> map = Maps.newEnumMap(WeaponDamage.class);

        phase.getProperty(AnimationProperties.ERPhaseProperty.PHYS_DAMAGE).ifPresent(valueModifier -> map.put(WeaponDamage.PHYSICAL, valueModifier));
        phase.getProperty(AnimationProperties.ERPhaseProperty.MAGIC_DAMAGE).ifPresent(valueModifier -> map.put(WeaponDamage.MAGICAL, valueModifier));
        phase.getProperty(AnimationProperties.ERPhaseProperty.FIRE_DAMAGE).ifPresent(valueModifier -> map.put(WeaponDamage.FIRE, valueModifier));
        phase.getProperty(AnimationProperties.ERPhaseProperty.LIGHT_DAMAGE).ifPresent(valueModifier -> map.put(WeaponDamage.LIGHTNING, valueModifier));
        phase.getProperty(AnimationProperties.ERPhaseProperty.HOLY_DAMAGE).ifPresent(valueModifier -> map.put(WeaponDamage.HOLY, valueModifier));
        return map;
    }

    @Override
    protected void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, Phase phase) {
        LivingEntity entity = entitypatch.getOriginal();
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
                        AbstractSpell spell = EntityStatusUtil.getRecentlyActivatedSpell(entity);
                        if (entity instanceof Player || entity instanceof IPlayerEnemy) {
                            spell.checkHurt(trueEntity);
                        } else if (entity instanceof LevelledMob levelledMob) {
                            var attacks = levelledMob.getAnimationDamage();
                            List<DamageInstance> instances = null;
                            for (var pair : attacks) {
                                if (pair.getFirst() == this) {
                                    instances = pair.getSecond();
                                    break;
                                }
                            }
                            if (instances != null) {
                                for (DamageInstance damage : instances) {
                                    ERDamageSource source = DamageUtil.moddedDamageSource(entity.level(), damage.damageType(), entity);
                                    trueEntity.hurt(source, damage.amount());
                                }
                            }
                        } else {
                            for (var entry : this.getSpellDamage(phase).entrySet()) {
                                if (entry.getKey() != null) {
                                    trueEntity.hurt(DamageUtil.moddedDamageSource(entity.level(), entry.getKey().getDamageType(), entity), entry.getValue());
                                }
                            }
                        }

                        entitypatch.getCurrenltyAttackedEntities().add(trueEntity);
                    }
                }
            }
        }
    }
}
