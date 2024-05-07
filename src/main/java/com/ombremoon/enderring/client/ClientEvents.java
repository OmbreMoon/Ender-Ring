package com.ombremoon.enderring.client;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.KeyBinds;
import com.ombremoon.enderring.client.gui.screen.*;
import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.common.object.world.inventory.GoldenSeedMenu;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
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

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinds.OPEN_QUICK_ACCESS_BINDING);
            event.register(KeyBinds.CYCLE_QUICK_ACCESS_BINDING);
            event.register(KeyBinds.USE_QUICK_ACCESS_BINDING);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(MenuTypeInit.QUICK_ACCESS_MENU.get(), QuickAccessScreen::new);
            MenuScreens.register(MenuTypeInit.WONDROUS_PHYSICK_MENU.get(), WondrousPhysickScreen::new);
            MenuScreens.register(MenuTypeInit.MEMORIZE_SPELL_MENU.get(), MemorizeSpellScreen::new);
            MenuScreens.register(MenuTypeInit.GOLDEN_SEED_MENU.get(), GoldenSeedScreen::new);
            MenuScreens.register(MenuTypeInit.SACRED_TEAR_MENU.get(), SacredTearScreen::new);
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Player player = Minecraft.getInstance().player;
            if (KeyBinds.OPEN_QUICK_ACCESS_BINDING.consumeClick()) {
                ModNetworking.getInstance().openQuickAccessMenu();
            }
            if (KeyBinds.CYCLE_QUICK_ACCESS_BINDING.consumeClick()) {
                ModNetworking.getInstance().cycleQuickAccessItem();
            }
            if (KeyBinds.USE_QUICK_ACCESS_BINDING.consumeClick()) {
                if (!PlayerStatusUtil.isUsingQuickAccess(player))
                    ModNetworking.getInstance().useQuickAccessItem();
            }
        }

        @SubscribeEvent
        public static void onUseQuickAccess(InputEvent.MouseScrollingEvent event) {
            Player player = Minecraft.getInstance().player;
            if (PlayerStatusUtil.isUsingQuickAccess(player)) {
                event.setCanceled(true);
            }
        }
    }
}
