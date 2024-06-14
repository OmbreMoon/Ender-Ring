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
    public static final List<AttributesRegister<?>> SUPPLIERS = new ArrayList<>();

    public static void initEntityRegisters() {
        MobInit.init();
        ProjectileInit.init();
    }

    protected static <T extends Mob> RegistryObject<EntityType<T>> registerMob(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange, Supplier<AttributeSupplier.Builder> attributeSupplier) {
        return registerMob(name, factory, category, true, width, height, clientTrackingRange, attributeSupplier, true);
    }

    protected static <T extends Mob> RegistryObject<EntityType<T>> registerMob(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange, Supplier<AttributeSupplier.Builder> attributeSupplier, boolean hasLoot) {
        return registerMob(name, factory, category, true, width, height, clientTrackingRange, attributeSupplier, hasLoot);
    }

    protected static <T extends Mob> RegistryObject<EntityType<T>> registerMob(String name, EntityType.EntityFactory<T> factory, MobCategory mobCategory, boolean fireImmune, float width, float height, int clientTrackingRange, Supplier<AttributeSupplier.Builder> attributeSupplier, boolean hasLoot) {
        EntityType.Builder<T> builder = EntityType.Builder.of(factory, mobCategory).sized(width, height).clientTrackingRange(clientTrackingRange);

        if (fireImmune) {
            builder.fireImmune();
        }

        RegistryObject<EntityType<T>> registryObject = EntityInit.ENTITIES.register(name, () -> {
            EntityType<T> entityType = builder.build(name);
            if (attributeSupplier != null) {
                SUPPLIERS.add(new AttributesRegister<>(() -> entityType, attributeSupplier));
            }
            return entityType;
        });

        return registryObject;
    }

    public record AttributesRegister<E extends LivingEntity>(Supplier<EntityType<E>> entityTypeSupplier, Supplier<AttributeSupplier.Builder> attributeSupplier) {}

    public static void register( IEventBus eventBus) {
        initEntityRegisters();
        ENTITIES.register(eventBus);
    }
}
