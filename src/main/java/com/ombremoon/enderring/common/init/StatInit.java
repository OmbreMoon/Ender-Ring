package com.ombremoon.enderring.common.init;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class StatInit {
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT, Constants.MOD_ID);

    public static final RegistryObject<ResourceLocation> SORCERIES_CAST = registerStat("sorceries_cast");
    public static final RegistryObject<ResourceLocation> INCANTATIONS_CAST = registerStat("incantations_cast");

    public static RegistryObject<ResourceLocation> registerStat(String name) {
        return STATS.register(name, () -> CommonClass.customLocation(name));
    }

    public static void register(IEventBus modEventBus) {
        STATS.register(modEventBus);
    }
}
