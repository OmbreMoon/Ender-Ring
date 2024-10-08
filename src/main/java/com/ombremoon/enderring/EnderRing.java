package com.ombremoon.enderring;

import com.ombremoon.enderring.client.render.ERArmorRenderer;
import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import com.ombremoon.enderring.common.data.Saturation;
import com.ombremoon.enderring.common.data.Saturations;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.ai.MemoryTypeInit;
import com.ombremoon.enderring.common.init.entity.ai.SensorsInit;
import com.ombremoon.enderring.common.magic.Classification;
import com.ombremoon.enderring.common.object.item.equipment.ModdedArmor;
import com.ombremoon.enderring.common.object.world.LevelledList;
import com.ombremoon.enderring.compat.epicfight.gameassets.SkillInit;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedSkillCategories;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedSkillSlots;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedStyles;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCategories;
import com.ombremoon.enderring.event.FirstSpawnEvent;
import com.ombremoon.enderring.mixin.DuckRangedAttribute;
import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

@Mod.EventBusSubscriber
@Mod(Constants.MOD_ID)
public class EnderRing {

    public EnderRing() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::loadComplete);
        MinecraftForge.EVENT_BUS.register(this);
        CommonClass.init(modEventBus);

        SkillInit.registerSkills();
        MemoryTypeInit.init();
        SensorsInit.init();

        SkillCategory.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, ExtendedSkillCategories.class);
        SkillSlot.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, ExtendedSkillSlots.class);
        Style.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, ExtendedStyles.class);
        WeaponCategory.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, ExtendedWeaponCategories.class);
        Saturation.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, Saturations.class);
        LevelledList.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, LevelledList.class);
        Classification.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, Classification.class);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_CONFIG, "enderring-common.toml");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModNetworking::registerPackets);
        event.enqueueWork(EntityStatusProvider::registerEntityStatuses);
        event.enqueueWork(() -> {
            EntityAttributeInit.ATTRIBUTE_MAP.put(EntityAttributeInit.VIGOR.get(), EntityAttributeInit.IMMUNITY.get());
            EntityAttributeInit.ATTRIBUTE_MAP.put(EntityAttributeInit.ENDURANCE.get(), EntityAttributeInit.ROBUSTNESS.get());
            EntityAttributeInit.ATTRIBUTE_MAP.put(EntityAttributeInit.MIND.get(), EntityAttributeInit.FOCUS.get());
            EntityAttributeInit.ATTRIBUTE_MAP.put(EntityAttributeInit.ARCANE.get(), EntityAttributeInit.VITALITY.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.RUNE_LEVEL.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.MAX_FP.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.VIGOR.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.MIND.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.ENDURANCE.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.STRENGTH.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.DEXTERITY.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.INTELLIGENCE.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.FAITH.get());
            EntityAttributeInit.PLAYER_ATTRIBUTES.add(EntityAttributeInit.ARCANE.get());
        });
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(EnderRing::fixHealth);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onFirstSpawn(EntityJoinLevelEvent event) {
        FirstSpawnEvent.onServerSpawn(event.getLevel(), event.getEntity());
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent e) {
        if (!e.phase.equals(TickEvent.Phase.END)) return;
        FirstSpawnEvent.onServerTick(e.getServer());
    }

    private static void fixHealth() {
        final Attribute attribute = Attributes.MAX_HEALTH;
        final DuckRangedAttribute newAttr = (DuckRangedAttribute) attribute;
        newAttr.setMaxValue(5120);
    }
}
