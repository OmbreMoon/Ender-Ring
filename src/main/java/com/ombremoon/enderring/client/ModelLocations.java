package com.ombremoon.enderring.client;

import com.ombremoon.enderring.CommonClass;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModelLocations {
    public static final ModelLayerLocation TEST_DUMMY = new ModelLayerLocation(CommonClass.customLocation("test_dummy"), "main");

    //MOBS
    public static final ModelLayerLocation TORRENT = new ModelLayerLocation(CommonClass.customLocation("torrent"), "main");

    //MISC ENTITIES
    public static final ModelLayerLocation MIDAS_SHOULDER_GUARD = new ModelLayerLocation(CommonClass.customLocation("midas_shoulder_guard"), "main");
}
