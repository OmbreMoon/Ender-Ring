package com.ombremoon.enderring.common.object.item.equipment.weapon;

import com.ombremoon.enderring.common.data.ArmorResistance;
import com.ombremoon.enderring.common.data.ArmorResistanceManager;

public interface Resistance {

    ArmorResistance getArmor();

    void setWeapon(ArmorResistanceManager.Wrapper wrapper);
}
