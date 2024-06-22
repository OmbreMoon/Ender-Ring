package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.Constants;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Constants.MOD_ID);

    public static void initEntityRegisters() {
        MobInit.init();
        ProjectileInit.init();
    }
    public static void register( IEventBus eventBus) {
        initEntityRegisters();
        ENTITIES.register(eventBus);
    }
}
