package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.common.init.entity.EntityInit;
import com.ombremoon.enderring.common.object.entity.mob.Torrent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MobInit {
    public static void init() {}
    public static final List<AttributesRegister<?>> attributeSuppliers = new ArrayList<>();

    public static RegistryObject<EntityType<Torrent>> TORRENT = registerMob("torrent", Torrent::new, MobCategory.CREATURE, 1.65F, 2.0F, 10, Torrent::createTorrentAttributes);

    private static <T extends Mob> RegistryObject<EntityType<T>> registerMob(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange, Supplier<AttributeSupplier.Builder> attributeSupplier) {
        return registerMob(name, factory, category, false, width, height, clientTrackingRange, attributeSupplier);
    }

    private static <T extends Mob> RegistryObject<EntityType<T>> registerMob(String name, EntityType.EntityFactory<T> factory, MobCategory mobCategory, boolean fireImmune, float width, float height, int clientTrackingRange, Supplier<AttributeSupplier.Builder> attributeSupplier) {
        EntityType.Builder<T> builder = EntityType.Builder.of(factory, mobCategory).sized(width, height).clientTrackingRange(clientTrackingRange);

        if (fireImmune) {
            builder.fireImmune();
        }

        RegistryObject<EntityType<T>> registryObject = EntityInit.ENTITIES.register(name, () -> {
            EntityType<T> entityType = builder.build(name);
            attributeSuppliers.add(new AttributesRegister<>(() -> entityType, attributeSupplier));
            return entityType;
        });

        return registryObject;
    }

    public record AttributesRegister<E extends LivingEntity>(Supplier<EntityType<E>> entityTypeSupplier, Supplier<AttributeSupplier.Builder> attributeSupplier) {}
}
