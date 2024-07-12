package com.ombremoon.enderring.compat.epicfight.api.animation;

import yesman.epicfight.api.animation.property.AnimationProperty;

public class AnimationProperties {

    public static class ERPhaseProperty<T> extends AnimationProperty.AttackPhaseProperty<T> {
        public static final AttackPhaseProperty<Float> PHYS_DAMAGE = new AttackPhaseProperty<>();
        public static final AttackPhaseProperty<Float> MAGIC_DAMAGE = new AttackPhaseProperty<>();
        public static final AttackPhaseProperty<Float> FIRE_DAMAGE = new AttackPhaseProperty<>();
        public static final AttackPhaseProperty<Float> LIGHT_DAMAGE = new AttackPhaseProperty<>();
        public static final AttackPhaseProperty<Float> HOLY_DAMAGE = new AttackPhaseProperty<>();
    }
}
