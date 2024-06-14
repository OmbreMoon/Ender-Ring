package com.ombremoon.enderring.common.object.world;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public enum LevelledLists implements LevelledList {
    PLAINS(Biomes.PLAINS, 1.0F),
    DESERT(Biomes.DESERT, 1.414F);

    final ResourceKey<Biome> biome;
    final float maxHPMult;
    final int id;

    LevelledLists(ResourceKey<Biome> biome, float maxHPMult) {
        this.id = LevelledList.ENUM_MANAGER.assign(this);
        this.biome = biome;
        this.maxHPMult = maxHPMult;
    }

    @Override
    public ResourceKey<Biome> getBiome() {
        return this.biome;
    }

    @Override
    public float getMaxHPMult() {
        return this.maxHPMult;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
