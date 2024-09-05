package com.ombremoon.enderring.client.model;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.object.item.equipment.ERArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ERArmorModel extends GeoModel<ERArmor> {

    @Override
    public ResourceLocation getModelResource(ERArmor ERArmor) {
        return CommonClass.customLocation("geo/armor/" + getArmorName(ERArmor) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ERArmor ERArmor) {
        return CommonClass.customLocation("textures/armor/" + getArmorName(ERArmor) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ERArmor ERArmor) {
        return CommonClass.customLocation("animations/" + getArmorName(ERArmor) + ".animation.json");
    }

    private String getArmorName(ERArmor armor) {
        String name = armor.getMaterial().getName();
        return name.substring(name.indexOf(":") + 1);
    }
}
