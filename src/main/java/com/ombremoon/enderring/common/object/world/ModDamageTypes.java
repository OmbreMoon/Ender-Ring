package com.ombremoon.enderring.common.object.world;

import com.ombremoon.enderring.CommonClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageTypes {
    ResourceKey<DamageType> PHYSICAL = register("physical");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, CommonClass.customLocation(name));
    }

    static void bootstrap(BootstapContext<DamageType> context) {
        context.register(PHYSICAL, new DamageType("physical", 0.1F));
    }
}
