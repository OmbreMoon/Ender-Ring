package com.ombremoon.enderring.client.render;

import com.ombremoon.enderring.client.model.MidasGauntletModel;
import com.ombremoon.enderring.common.object.item.MidasGauntletItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class MidasGauntletRenderer extends GeoItemRenderer<MidasGauntletItem> {
    public MidasGauntletRenderer() {
        super(new MidasGauntletModel());
    }
}
