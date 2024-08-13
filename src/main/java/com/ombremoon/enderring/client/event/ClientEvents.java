package com.ombremoon.enderring.client.event;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.KeyBinds;
import com.ombremoon.enderring.client.gui.QuickAccessOverlay;
import com.ombremoon.enderring.client.gui.screen.*;
import com.ombremoon.enderring.client.render.block.GraceSiteRenderer;
import com.ombremoon.enderring.client.render.entity.mob.SpiritJellyfishRenderer;
import com.ombremoon.enderring.client.render.entity.mob.TranslucentLayer;
import com.ombremoon.enderring.client.render.layer.MidasShoulderGuardLayer;
import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.common.init.blocks.BlockEntityInit;
import com.ombremoon.enderring.common.init.entity.EntityInit;
import com.ombremoon.enderring.common.init.entity.MobInit;
import com.ombremoon.enderring.common.object.entity.NPCMob;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            CommonClientClass.getRenderers().forEach(
                    renderers -> {
                        event.registerEntityRenderer((EntityType<?>) renderers.type().get(), renderers.renderer());
                    }
            );
            event.registerBlockEntityRenderer(BlockEntityInit.GRACE_SITE.get(), context -> new GraceSiteRenderer());
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            CommonClientClass.getLayerDefinitions().forEach(
                    layerDefinitions -> event.registerLayerDefinition(layerDefinitions.layerLocation(), layerDefinitions::supplier)
            );
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.AddLayers event) {
            for (final String skin : event.getSkins()) {
                final LivingEntityRenderer<Player, PlayerModel<Player>> playerRenderer = event.getSkin(skin);
                if (playerRenderer == null)
                    continue;
                playerRenderer.addLayer(new MidasShoulderGuardLayer<>(playerRenderer));
            }
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinds.OPEN_QUICK_ACCESS_BINDING);
            event.register(KeyBinds.CYCLE_QUICK_ACCESS_BINDING);
            event.register(KeyBinds.OPEN_PLAYER_STATUS_BINDING);
            event.register(KeyBinds.HEAVY_ATTACK_BINDING);
            event.register(KeyBinds.ASH_OF_WAR_BINDING);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(MenuTypeInit.QUICK_ACCESS_MENU.get(), QuickAccessScreen::new);
            MenuScreens.register(MenuTypeInit.WONDROUS_PHYSICK_MENU.get(), WondrousPhysickScreen::new);
            MenuScreens.register(MenuTypeInit.MEMORIZE_SPELL_MENU.get(), MemorizeSpellScreen::new);
            MenuScreens.register(MenuTypeInit.GOLDEN_SEED_MENU.get(), GoldenSeedScreen::new);
            MenuScreens.register(MenuTypeInit.SACRED_TEAR_MENU.get(), SacredTearScreen::new);

            EntityDataSerializers.registerSerializer(NPCMob.NPC_DATA_SERIALIZER);
        }

        @SubscribeEvent
        public static void registerGUIOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("quick_access", QuickAccessOverlay.QUICK_ACCESS_OVERLAY);
        }

        @SubscribeEvent
        public static void onParticleProviderRegister(RegisterParticleProvidersEvent event) {
//            ParticleInit.registerParticleFactory(event);
        }
    }

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Player player = Minecraft.getInstance().player;
            if (KeyBinds.OPEN_QUICK_ACCESS_BINDING.consumeClick()) {
                ModNetworking.openQuickAccessMenu();
            }
            if (KeyBinds.CYCLE_QUICK_ACCESS_BINDING.consumeClick()) {
                ModNetworking.cycleQuickAccessItem();
            }
            if (KeyBinds.OPEN_PLAYER_STATUS_BINDING.consumeClick()) {
                Minecraft.getInstance().setScreen(new PlayerStatusScreen(Component.literal("Status")));
                /*if (!EntityStatusUtil.isUsingQuickAccess(player))
                    ModNetworking.useQuickAccessItem();*/
            }
        }

        @SubscribeEvent
        public static void onUseQuickAccess(InputEvent.MouseScrollingEvent event) {
            Player player = Minecraft.getInstance().player;
            if (EntityStatusUtil.isUsingQuickAccess(player)) {
                event.setCanceled(true);
            }
        }
    }
}
