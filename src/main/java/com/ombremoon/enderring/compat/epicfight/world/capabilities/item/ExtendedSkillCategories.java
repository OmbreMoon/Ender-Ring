package com.ombremoon.enderring.compat.epicfight.world.capabilities.item;

import yesman.epicfight.skill.SkillCategory;

public enum ExtendedSkillCategories implements SkillCategory {
    HEAVY_ATTACK(false, false, false),
    ASH_OF_WAR(false, true, false);

    boolean shouldSave;
    boolean shouldSyncronize;
    boolean modifiable;
    int id;

    private ExtendedSkillCategories(boolean shouldSave, boolean shouldSyncronize, boolean modifiable) {
        this.shouldSave = shouldSave;
        this.shouldSyncronize = shouldSyncronize;
        this.modifiable = modifiable;
        this.id = SkillCategory.ENUM_MANAGER.assign(this);
    }

    @Override
    public boolean shouldSave() {
        return this.shouldSave;
    }

    @Override
    public boolean shouldSynchronize() {
        return this.shouldSyncronize;
    }

    @Override
    public boolean learnable() {
        return this.modifiable;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
