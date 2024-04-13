package com.ombremoon.enderring.client.model;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.object.item.equipment.ModdedArmor;
import mod.azure.azurelibarmor.model.GeoModel;
import net.minecraft.resources.ResourceLocation;

public class ERArmorModel extends GeoModel<ModdedArmor> {

    @Override
    public ResourceLocation getModelResource(ModdedArmor moddedArmor) {
        return CommonClass.customLocation("geo/blue_cloth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ModdedArmor moddedArmor) {
        return CommonClass.customLocation("textures/armor/blue_cloth.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ModdedArmor moddedArmor) {
        return CommonClass.customLocation("animations/blue_cloth.animation.json");
    }
}
