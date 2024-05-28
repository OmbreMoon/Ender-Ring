package com.ombremoon.enderring.compat.epicfight.skills;

import net.minecraft.world.entity.ai.attributes.Attributes;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class NewGuillotineAxeSkill extends SimpleAshOfWarSkill {
    private static final UUID EVENT_UUID = UUID.fromString("3460b8ec-cf64-4dd6-b1d8-cb7c72943558");

    public NewGuillotineAxeSkill(Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        container.getExecuter().getEventListener().addEventListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_PRE, EVENT_UUID, (event) -> {
            if (event.getDamageSource().getAnimation() == Animations.THE_GUILLOTINE) {
                ValueModifier damageModifier = ValueModifier.empty();
                this.getProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, this.properties.get(0)).ifPresent(damageModifier::merge);
                damageModifier.merge(ValueModifier.multiplier(0.8F));
                float health = event.getTarget().getHealth();
                float executionHealth = damageModifier.getTotalValue((float)event.getPlayerPatch().getOriginal().getAttributeValue(Attributes.ATTACK_DAMAGE));

                if (health < executionHealth) {
                    if (event.getDamageSource() != null) {
                        event.getDamageSource().addRuntimeTag(EpicFightDamageType.EXECUTION);
                    }
                }
            }
        });
    }

    @Override
    public void onRemoved(SkillContainer container) {
        super.onRemoved(container);

        container.getExecuter().getEventListener().removeListener(PlayerEventListener.EventType.DEALT_DAMAGE_EVENT_PRE, EVENT_UUID);
    }
}
