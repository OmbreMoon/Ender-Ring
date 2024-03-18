package com.ombremoon.ommodtemplate;

import com.ombremoon.ommodtemplate.common.init.MobInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(Constants.MOD_ID)
public class ModTemplate {

    public ModTemplate() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        CommonClass.init(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public static void onEntityAttributeRegister(EntityAttributeCreationEvent e) {
        MobInit.attributeSuppliers.forEach(p -> e.put(p.entityTypeSupplier().get(), p.attributeSupplier().get().build()));
    }
}
