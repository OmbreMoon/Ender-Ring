package com.ombremoon.enderring.client.render;

import com.ombremoon.enderring.client.model.ERArmorModel;
import com.ombremoon.enderring.common.object.item.equipment.ERArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ERArmorRenderer extends GeoArmorRenderer<ERArmor> {
    public ERArmorRenderer() {
        super(new ERArmorModel());
    }
}
