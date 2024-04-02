package com.ombremoon.enderring.client;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.model.armor.ERArmorModel;
import com.ombremoon.enderring.client.render.ERArmorLayer;
import com.ombremoon.enderring.common.init.item.ArmorsInit;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
//            event.registerLayerDefinition(ModelLocations.BLUE_CLOTH_LAYER, ERArmorModel::createBlueClothLayer);
//            event.registerLayerDefinition(ModelLocations.BLUE_CLOTH_FAKE_LAYER, ERArmorModel::createFakeBlueClothLayer);
            registerArmorLayers(event, ModelLocations.BLUE_CLOTH_LAYER, ModelLocations.BLUE_CLOTH_FAKE_LAYER, ERArmorModel::createBlueClothLayer, ERArmorModel::createFakeBlueClothLayer);
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.AddLayers event) {
            for (final String skin : event.getSkins()) {
                final LivingEntityRenderer<Player, PlayerModel<Player>> playerRenderer = event.getSkin(skin);
                if (playerRenderer == null)
                    continue;
                playerRenderer.addLayer(new ERArmorLayer<>(ArmorsInit.BLUE_CLOTH, ModelLocations.BLUE_CLOTH_TEXTURE, playerRenderer, ModelLocations.BLUE_CLOTH_MODEL, ModelLocations.FAKE_BLUE_CLOTH_MODEL));
            }
        }

        private static void registerArmorLayers(EntityRenderersEvent.RegisterLayerDefinitions event, ModelLayerLocation armorLocation, ModelLayerLocation fakeArmorLocation, Supplier<LayerDefinition> armorLayer, Supplier<LayerDefinition> fakeArmorLayer) {
            event.registerLayerDefinition(armorLocation, armorLayer);
            event.registerLayerDefinition(fakeArmorLocation, fakeArmorLayer);
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

    }
}
