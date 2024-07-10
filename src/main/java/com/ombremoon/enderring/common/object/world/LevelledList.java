package com.ombremoon.enderring.common.object.world;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import yesman.epicfight.api.utils.ExtendableEnum;
import yesman.epicfight.api.utils.ExtendableEnumManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//TODO: CHANGE CHECK TO SIZE CHECK
public interface LevelledList extends ExtendableEnum {
    ExtendableEnumManager<LevelledList> ENUM_MANAGER = new ExtendableEnumManager("levelled_effect") {
        @Override
        public void registerEnumCls(String modid, Class cls) {
            super.registerEnumCls(modid, cls);
            Collection<ResourceKey<Biome>> biomes = ENUM_MANAGER.universalValues().stream().map(LevelledList::getBiome).toList();
            Set<ResourceKey<Biome>> biomeSet = new HashSet<>();
            for (var biome : biomes) {
                if (!biomeSet.add(biome)) {
                    throw new IllegalStateException("Biome " + biome + " has already been registered as a levelled list.");
                }
            }
        }
    };

    ResourceKey<Biome> getBiome();

    float getMaxHPMult();

    float getDamageMult();
}
