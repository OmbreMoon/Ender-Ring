package com.ombremoon.enderring.common.object.world;

import com.ombremoon.enderring.CommonClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageTypes {
    ResourceKey<DamageType> PHYSICAL = register("physical");
    ResourceKey<DamageType> STRIKE = register("strike");
    ResourceKey<DamageType> SLASH = register("slash");
    ResourceKey<DamageType> PIERCE = register("pierce");
    ResourceKey<DamageType> MAGICAL = register("magical");
    ResourceKey<DamageType> FIRE = register("fire");
    ResourceKey<DamageType> LIGHTNING = register("lightning");
    ResourceKey<DamageType> HOLY = register("holy");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, CommonClass.customLocation(name));
    }

    static void bootstrap(BootstapContext<DamageType> context) {
        context.register(PHYSICAL, new DamageType("physical", 0.1F));
        context.register(STRIKE, new DamageType("strike", 0.1F));
        context.register(SLASH, new DamageType("slash", 0.1F));
        context.register(PIERCE, new DamageType("pierce", 0.1F));
        context.register(MAGICAL, new DamageType("magical", 0.1F));
        context.register(FIRE, new DamageType("fire", 0.1F));
        context.register(LIGHTNING, new DamageType("lightning", 0.1F));
        context.register(HOLY, new DamageType("holy", 0.1F));
    }
}
