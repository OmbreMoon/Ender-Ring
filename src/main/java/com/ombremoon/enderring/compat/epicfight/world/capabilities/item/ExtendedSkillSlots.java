package com.ombremoon.enderring.compat.epicfight.world.capabilities.item;

import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum ExtendedSkillSlots implements SkillSlot {
    HEAVY_ATTACK(ExtendedSkillCategories.HEAVY_ATTACK),
    ASH_OF_WAR(ExtendedSkillCategories.ASH_OF_WAR);

    SkillCategory category;
    int id;

    private ExtendedSkillSlots(SkillCategory category) {
        this.category = category;
        this.id = SkillSlot.ENUM_MANAGER.assign(this);
    }

    @Override
    public SkillCategory category() {
        return this.category;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
