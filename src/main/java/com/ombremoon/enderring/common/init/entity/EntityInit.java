package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Constants.MOD_ID);

    public static void initEntityRegisters() {
        MobInit.init();
    }

    public static void register( IEventBus eventBus) {
        initEntityRegisters();
        ENTITIES.register(eventBus);
    }
}
