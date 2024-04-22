package com.ombremoon.enderring.client;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.gui.screen.GraceSiteScreen;
import com.ombremoon.enderring.client.gui.screen.MemorizeSpellScreen;
import com.ombremoon.enderring.client.gui.screen.WondrousPhysickScreen;
import com.ombremoon.enderring.common.init.MenuTypeInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(MenuTypeInit.WONDROUS_PHYSICK_MENU.get(), WondrousPhysickScreen::new);
            MenuScreens.register(MenuTypeInit.MEMORIZE_SPELL_MENU.get(), MemorizeSpellScreen::new);
        }
    }
}
