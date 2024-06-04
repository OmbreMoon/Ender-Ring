package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch;

import com.ombremoon.enderring.common.object.entity.HumanoidMob;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.world.capabilities.entitypatch.Faction;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;

public abstract class ExtendedHumanoidMobPatch<E extends HumanoidMob<?>> extends HumanoidMobPatch<E> {
    public ExtendedHumanoidMobPatch(Faction faction) {
        super(faction);
    }

    @Override
    protected void initAI() {
    }

    @Override
    protected CombatBehaviors.Builder<HumanoidMobPatch<?>> getHoldingItemWeaponMotionBuilder() {
        return null;
    }

    @Override
    public void setAIAsInfantry(boolean holdingRanedWeapon) {
    }

    @Override
    public void setAIAsMounted(Entity ridingEntity) {
    }
}
