package com.ombremoon.enderring.client.render;

import com.ombremoon.enderring.client.model.ERWeaponModel;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class ERWeaponRenderer extends GeoItemRenderer<MeleeWeapon> {
    public ERWeaponRenderer() {
        super(new ERWeaponModel());
    }
}
