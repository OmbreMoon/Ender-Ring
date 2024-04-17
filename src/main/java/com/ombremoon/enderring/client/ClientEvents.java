package com.ombremoon.enderring.client;

import com.ombremoon.enderring.Constants;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            CommonClientClass.getRenderers().forEach(
                    renderers -> event.registerEntityRenderer((EntityType<?>) renderers.type().get(), renderers.renderer())
            );
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            CommonClientClass.getLayerDefinitions().forEach(
                    layerDefinitions -> event.registerLayerDefinition(layerDefinitions.layerLocation(), layerDefinitions::supplier)
            );
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.AddLayers event) {

        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

    }
}
