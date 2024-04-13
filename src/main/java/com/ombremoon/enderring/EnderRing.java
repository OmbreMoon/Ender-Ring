package com.ombremoon.enderring;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.MobInit;
import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Arrays;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@Mod(Constants.MOD_ID)
public class EnderRing {

    public EnderRing() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        CommonClass.init(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ModNetworking.registerPackets();
    }

    @SubscribeEvent
    public static void onEntityAttributeRegister(EntityAttributeCreationEvent e) {
        MobInit.attributeSuppliers.forEach(p -> e.put(p.entityTypeSupplier().get(), p.attributeSupplier().get().build()));
    }
}
