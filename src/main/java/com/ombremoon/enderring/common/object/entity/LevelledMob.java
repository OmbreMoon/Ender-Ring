package com.ombremoon.enderring.common.object.entity;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.common.DamageInstance;
import com.ombremoon.enderring.common.StatusType;
import com.ombremoon.enderring.common.object.world.LevelledList;
import com.ombremoon.enderring.common.object.world.LevelledLists;
import com.ombremoon.enderring.util.EntityStatusUtil;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface LevelledMob {

    default boolean isImmuneTo(LivingEntity targetEntity, StatusType statusType, DamageSource damageSource) {
        return EntityStatusUtil.getEntityAttribute(targetEntity, statusType.getEntityResist()) == -1;
    }

    default void scaleStats(LivingEntity livingEntity, BiConsumer<LivingEntity, LevelledList> consumer) {
        Optional<ResourceKey<Biome>> optional = livingEntity.level().getBiome(livingEntity.getOnPos()).unwrapKey();
        if (optional.isPresent()) {
            ResourceKey<Biome> biome = optional.get();
            for (var levelledList : LevelledLists.values()) {
                if (levelledList.getBiome() == biome) {
                    consumer.accept(livingEntity, levelledList);
                    break;
                }
            }
        }
    }

    default boolean isPlayerEnemy() {
        return this instanceof IPlayerEnemy;
    }

    ObjectArrayList<Pair<StaticAnimation, List<DamageInstance>>> getAnimationDamage();
}
