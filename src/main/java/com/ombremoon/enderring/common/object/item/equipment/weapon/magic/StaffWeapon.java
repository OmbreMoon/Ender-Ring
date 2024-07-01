package com.ombremoon.enderring.common.object.item.equipment.weapon.magic;

import com.ombremoon.enderring.common.magic.Classification;
import com.ombremoon.enderring.common.magic.MagicType;

public class StaffWeapon extends CatalystWeapon {
    public StaffWeapon(Properties properties, float spellBoost, Classification... classifications) {
        super(MagicType.SORCERY, properties, spellBoost, classifications);
    }

    public StaffWeapon(Properties properties) {
        super(MagicType.SORCERY, properties);
    }
}
