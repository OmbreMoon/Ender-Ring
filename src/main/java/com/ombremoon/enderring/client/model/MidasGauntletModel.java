package com.ombremoon.enderring.client.model;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.object.item.MidasGauntletItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MidasGauntletModel extends GeoModel<MidasGauntletItem> {
    @Override
    public ResourceLocation getModelResource(MidasGauntletItem animatable) {
        return CommonClass.customLocation("geo/midas_gauntlet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MidasGauntletItem animatable) {
        return CommonClass.customLocation("textures/item/midas_gauntlet.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MidasGauntletItem animatable) {
        return null;
    }
}
