package com.ombremoon.enderring.client.render.block;

import com.ombremoon.enderring.client.model.block.GraceSiteModel;
import com.ombremoon.enderring.common.object.block.GraceSiteBlock;
import com.ombremoon.enderring.common.object.block.entity.GraceSiteBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GraceSiteRenderer extends GeoBlockRenderer<GraceSiteBlockEntity> {
    public GraceSiteRenderer() {
        super(new GraceSiteModel());
    }
}
