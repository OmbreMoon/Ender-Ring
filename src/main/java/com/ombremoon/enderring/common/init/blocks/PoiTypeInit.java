package com.ombremoon.enderring.common.init.blocks;

import com.google.common.collect.ImmutableSet;
import com.ombremoon.enderring.Constants;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PoiTypeInit {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Constants.MOD_ID);

//    public static final RegistryObject<PoiType> HEWG_POI = POI_TYPES.register("hewg_poi", () -> new PoiType(ImmutableSet.copyOf(Blocks.ANVIL.getStateDefinition().getPossibleStates()), 1, 1));

    public static void register(IEventBus modEventBus) {
        POI_TYPES.register(modEventBus);
    }
}
