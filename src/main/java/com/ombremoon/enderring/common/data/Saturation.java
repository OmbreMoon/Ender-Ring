package com.ombremoon.enderring.common.data;

import yesman.epicfight.api.utils.ExtendableEnumManager;
import yesman.epicfight.skill.SkillSlot;

public interface Saturation {
    ExtendableEnumManager<SkillSlot> ENUM_MANAGER = new ExtendableEnumManager("saturation");

    int[] getStat();

    double[] getExp();

    int[] getGrow();
}
