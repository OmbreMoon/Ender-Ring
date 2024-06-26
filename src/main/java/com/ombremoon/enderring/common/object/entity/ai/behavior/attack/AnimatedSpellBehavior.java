package com.ombremoon.enderring.common.object.entity.ai.behavior.attack;

import com.ombremoon.enderring.common.object.entity.SpellAttackMob;
import net.minecraft.world.entity.Mob;

public class AnimatedSpellBehavior<E extends Mob & SpellAttackMob> extends AnimatedCombatBehavior<E> {
    public AnimatedSpellBehavior(int delayTicks) {
        super(delayTicks);
    }
}
