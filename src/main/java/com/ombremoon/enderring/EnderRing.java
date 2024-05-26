package com.ombremoon.enderring;

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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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

        SkillCategory.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, ExtendedSkillCategories.class);
        SkillSlot.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, ExtendedSkillSlots.class);
        Style.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, ExtendedStyles.class);
        WeaponCategory.ENUM_MANAGER.registerEnumCls(Constants.MOD_ID, ExtendedWeaponCategories.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModNetworking.registerPackets();
    }

    private void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(EnderRing::fixHealth);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onFirstSpawn(EntityJoinLevelEvent event) {
        FirstSpawnEvent.onSpawn(event.getLevel(), event.getEntity());
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
