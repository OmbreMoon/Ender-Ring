package com.ombremoon.enderring.client.model.block;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.object.block.entity.GraceSiteBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GraceSiteModel extends GeoModel<GraceSiteBlockEntity> {
    @Override
    public ResourceLocation getModelResource(GraceSiteBlockEntity animatable) {
        return CommonClass.customLocation("geo/block/grace_site.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GraceSiteBlockEntity animatable) {
        return CommonClass.customLocation("textures/block/grace_site.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GraceSiteBlockEntity animatable) {
        return CommonClass.customLocation("animations/block/grace_site.animation.json");
    }
}
