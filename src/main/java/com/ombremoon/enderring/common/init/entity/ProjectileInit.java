package com.ombremoon.enderring.common.init.entity;

import com.ombremoon.enderring.common.object.entity.projectile.spell.GlintstoneArcEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.registries.RegistryObject;

public class ProjectileInit extends EntityInit {
    public static void init() {}

    public static final RegistryObject<EntityType<GlintstoneArcEntity>> GLINTSTONE_ARC = registerProjectile("glintstone_arc", GlintstoneArcEntity::new, 1.5F, 0.5F);

    protected static <T extends Projectile> RegistryObject<EntityType<T>> registerProjectile(String name, EntityType.EntityFactory<T> factory, float width, float height) {
        EntityType.Builder<T> builder = EntityType.Builder.of(factory, MobCategory.MISC).sized(width, height).clientTrackingRange(4);

        return EntityInit.ENTITIES.register(name, () -> {
            return builder.build(name);
        });
    }
}
