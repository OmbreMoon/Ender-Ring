package com.ombremoon.enderring.client;

import com.ombremoon.enderring.CommonClass;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLocations {
    public static final ResourceLocation BLUE_CLOTH_TEXTURE = CommonClass.customLocation("textures/entity/armor/blue_cloth_armor.png");
    public static final ModelLayerLocation BLUE_CLOTH_LAYER = new ModelLayerLocation(CommonClass.customLocation("blue_cloth_armor"), "main");
    public static final ModelLayerLocation BLUE_CLOTH_FAKE_LAYER = new ModelLayerLocation(CommonClass.customLocation("fake_blue_cloth_armor"), "main");
}
