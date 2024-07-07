package com.ombremoon.enderring.common.object.world.worldgen.structure.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.Optional;

//By TelepathicGrunt
public class AdvancedRandomSpreadOffset extends RandomSpreadStructurePlacement {
    public static final Codec<AdvancedRandomSpreadOffset> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(AdvancedRandomSpreadOffset::locateOffset),
            FrequencyReductionMethod.CODEC.optionalFieldOf("frequency_reduction_method", FrequencyReductionMethod.DEFAULT).forGetter(AdvancedRandomSpreadOffset::frequencyReductionMethod),
            Codec.floatRange(0.0F, 1.0F).optionalFieldOf("frequency", 1.0F).forGetter(AdvancedRandomSpreadOffset::frequency),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter(AdvancedRandomSpreadOffset::salt),
            ExclusionZone.CODEC.optionalFieldOf("exclusion_zone").forGetter(AdvancedRandomSpreadOffset::exclusionZone),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("spacing").forGetter(AdvancedRandomSpreadOffset::spacing),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("separation").forGetter(AdvancedRandomSpreadOffset::separation),
            RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(AdvancedRandomSpreadOffset::spreadType),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("xOffset").forGetter(AdvancedRandomSpreadOffset::xOffset),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("zOffset").forGetter(AdvancedRandomSpreadOffset::zOffset)
    ).apply(instance, instance.stable(AdvancedRandomSpreadOffset::new)));

    private final int spacing;
    private final int separation;
    private final int salt;
    private final RandomSpreadType spreadType;
    private final int xOffset;
    private final int zOffset;

    public AdvancedRandomSpreadOffset(Vec3i locationOffset,
                                      FrequencyReductionMethod frequencyReductionMethod,
                                      float frequency,
                                      int salt,
                                      Optional<ExclusionZone> exclusionZone,
                                      int spacing,
                                      int separation,
                                      RandomSpreadType spreadType,
                                      int xOffset,
                                      int zOffset
    ) {
        super(locationOffset, frequencyReductionMethod, frequency, salt, exclusionZone, spacing, separation, spreadType);
        this.spacing = spacing;
        this.separation = separation;
        this.spreadType = spreadType;
        this.salt = salt;
        this.xOffset = xOffset;
        this.zOffset = zOffset;
    }

    @Override
    public int spacing() {
        return 1;
    }

    @Override
    public int separation() {
        return 0;
    }

    public int xOffset() {
        return this.xOffset;
    }

    public int zOffset() {
        return this.zOffset;
    }

    @Override
    public ChunkPos getPotentialStructureChunk(long seed, int x, int z) {
        return new ChunkPos(x, z);
    }

    @Override
    protected boolean isPlacementChunk(ChunkGeneratorStructureState chunkGeneratorStructureState, int x, int z) {
        int newXPos = x + xOffset();
        int newZPos = z + zOffset();

        ChunkPos chunkpos = this.getOriginalPotentialStructureChunk(chunkGeneratorStructureState.getLevelSeed(), newXPos, newZPos);
        return chunkpos.x == newXPos && chunkpos.z == newZPos;
    }

    public ChunkPos getOriginalPotentialStructureChunk(long l, int i, int j) {
        int k = Math.floorDiv(i, this.spacing);
        int m = Math.floorDiv(j, this.spacing);
        WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
        worldgenRandom.setLargeFeatureWithSalt(l, k, m, this.salt());
        int n = this.spacing - this.separation;
        int o = this.spreadType.evaluate(worldgenRandom, n);
        int p = this.spreadType.evaluate(worldgenRandom, n);
        return new ChunkPos(k * this.spacing + o, m * this.spacing + p);
    }

/*    @Override
    public StructurePlacementType<?> type() {
        return RSStructurePlacementType.ADVANCED_RANDOM_SPREAD_OFFSETS.get();
    }*/
}