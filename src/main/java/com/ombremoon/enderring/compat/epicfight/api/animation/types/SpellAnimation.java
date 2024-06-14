package com.ombremoon.enderring.compat.epicfight.api.animation.types;

import com.ombremoon.enderring.compat.epicfight.api.collider.WorldPosOBBCollider;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class SpellAnimation extends AttackAnimation {

    public SpellAnimation( float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, colliderJoint, path, armature);
    }

    public SpellAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        super(convertTime, antic, preDelay, contact, recovery, hand, collider, colliderJoint, path, armature);
    }

    public SpellAnimation(float convertTime, String path, Armature armature, Phase... phases) {
        super(convertTime, path, armature, phases);
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
                        if (entity instanceof Player player)
                            EntityStatusUtil.getRecentlyActivatedSpell(player).checkHurt(trueEntity);

                        entitypatch.getCurrenltyAttackedEntities().add(trueEntity);
                    }
                }
            }
        }
    }

    @Override
    public void changeCollider(Collider newCollider, int index) {

    }
}
