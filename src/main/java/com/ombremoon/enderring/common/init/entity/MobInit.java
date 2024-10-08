package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.common.object.entity.mob.creature.SpiritJellyfish;
import com.ombremoon.enderring.common.object.entity.npc.TestDummy;
import com.ombremoon.enderring.common.object.entity.mob.creature.Torrent;
import com.ombremoon.enderring.common.object.entity.npc.Hewg;
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
    public static final List<AttributesRegister<?>> SUPPLIERS = new ArrayList<>();
    public static final List<RegistryObject<? extends EntityType<?>>> MOBS = new ArrayList<>();

    public static void init() {
        TEST_DUMMY = registerMob("test_dummy", TestDummy::new, MobCategory.MISC, 0.6F, 1.95F, 4, TestDummy::createMadPumpkinHeadAttributes);
    }

    public static RegistryObject<EntityType<TestDummy>> TEST_DUMMY;

    //Spirit Ashes
    public static RegistryObject<EntityType<SpiritJellyfish>> SPIRIT_JELLYFISH = registerMob("spirit_jellyfish", SpiritJellyfish::new, MobCategory.CREATURE, 3f, 3f, 10, SpiritJellyfish::createSpiritJellyfishAttributes, false);

    //Creatures
    public static RegistryObject<EntityType<Torrent>> TORRENT = registerMob("torrent", Torrent::new, MobCategory.CREATURE, 1.65F, 2.0F, 10, Torrent::createTorrentAttributes, false);

    //NPCs
    public static RegistryObject<EntityType<Hewg>> HEWG = registerMob("hewg", Hewg::new, MobCategory.MISC, 0.6F, 1.95F, 4, Hewg::createHewgAttributes);

    //Hostile Mobs
    //Soldier of Godrick
    //Margit
    //Mad Pumpkin Head
    //Beastman of Farum Azula

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
        MOBS.add(registryObject);

        return registryObject;
    }

    public record AttributesRegister<E extends LivingEntity>(Supplier<EntityType<E>> entityTypeSupplier, Supplier<AttributeSupplier.Builder> attributeSupplier) {}

}
