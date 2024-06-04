package com.ombremoon.enderring.client.model;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.object.item.equipment.ModdedArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ERArmorModel extends GeoModel<ModdedArmor> {

    @Override
    public ResourceLocation getModelResource(ModdedArmor moddedArmor) {
        return CommonClass.customLocation("geo/" + getArmorName(moddedArmor) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ModdedArmor moddedArmor) {
        return CommonClass.customLocation("textures/armor/" + getArmorName(moddedArmor) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ModdedArmor moddedArmor) {
        return CommonClass.customLocation("animations/" + getArmorName(moddedArmor) + ".animation.json");
    }

    private String getArmorName(ModdedArmor armor) {
        String name = armor.getMaterial().getName();
        return name.substring(name.indexOf(":") + 1);
    }
}
