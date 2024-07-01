package com.ombremoon.enderring.common.object.item.equipment.weapon.magic;

import com.ombremoon.enderring.common.magic.Classification;
import com.ombremoon.enderring.common.magic.MagicType;

public class SealWeapon extends CatalystWeapon {
    public SealWeapon(Properties properties, float spellBoost, Classification... classifications) {
        super(MagicType.INCANTATION, properties, spellBoost, classifications);
    }

    public SealWeapon(Properties properties) {
        super(MagicType.INCANTATION, properties);
    }
}
