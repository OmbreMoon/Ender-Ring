package com.ombremoon.enderring.client;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.client.model.armor.ERArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class ModelLocations {
    public static final ResourceLocation BLUE_CLOTH_TEXTURE = CommonClass.customLocation("textures/entity/armor/blue_cloth_armor.png");
    public static final ModelLayerLocation BLUE_CLOTH_LAYER = new ModelLayerLocation(CommonClass.customLocation("blue_cloth_armor"), "main");
    public static final ModelLayerLocation BLUE_CLOTH_FAKE_LAYER = new ModelLayerLocation(CommonClass.customLocation("fake_blue_cloth_armor"), "main");
    public static final HumanoidModel<Player> BLUE_CLOTH_MODEL = new ERArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(BLUE_CLOTH_LAYER));
    public static final HumanoidModel<Player> FAKE_BLUE_CLOTH_MODEL = new ERArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(BLUE_CLOTH_FAKE_LAYER));
}
