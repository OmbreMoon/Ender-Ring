package com.ombremoon.enderring.client.render;

import com.ombremoon.enderring.client.model.ERArmorModel;
import com.ombremoon.enderring.common.object.item.equipment.ModdedArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ERArmorRenderer extends GeoArmorRenderer<ModdedArmor> {
    public ERArmorRenderer() {
        super(new ERArmorModel());
    }
}
