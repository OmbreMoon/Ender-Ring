package com.ombremoon.enderring.common.capability;

import com.google.common.collect.Maps;
import com.ombremoon.enderring.CommonClass;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class EntityStatusProvider implements ICapabilityProvider, INBTSerializable<CompoundTag>, NonNullSupplier<EntityStatus<?>> {
    public static final Capability<EntityStatus<?>> ENTITY_STATUS = CapabilityManager.get(new CapabilityToken<>() {});
    public static final ResourceLocation CAPABILITY_LOCATION = CommonClass.customLocation("entity_status");
    private static final Map<EntityType<?>, Function<Entity, Supplier<EntityStatus<?>>>> STATUSES = Maps.newHashMap();

    private EntityStatus<?> entityStatus;
    private final LazyOptional<EntityStatus<?>> optional = LazyOptional.of(() -> entityStatus);

    public static void registerEntityStatuses() {
        Map<EntityType<?>, Function<Entity, Supplier<EntityStatus<?>>>> status = Maps.newHashMap();
        status.put(EntityType.PLAYER, entity -> PlayerStatus::new);
        STATUSES.putAll(status);
    }

    public EntityStatusProvider(LivingEntity livingEntity) {
        var provider = STATUSES.get(livingEntity.getType());
        if (provider != null) {
            this.entityStatus = provider.apply(livingEntity).get();
        } else {
            this.entityStatus = new EntityStatus<>();
        }
    }

    public static PlayerStatus get(Player player) {
        return (PlayerStatus) player.getCapability(ENTITY_STATUS).orElseThrow(NullPointerException::new);
    }

    public static EntityStatus<?> get(LivingEntity livingEntity) {
        return livingEntity.getCapability(ENTITY_STATUS).orElseThrow(NullPointerException::new);
    }

    public static boolean isPresent(LivingEntity livingEntity) {
        return livingEntity.getCapability(ENTITY_STATUS).isPresent();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ENTITY_STATUS.orEmpty(cap, this.optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.entityStatus.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.entityStatus.deserializeNBT(nbt);
    }

    @Override
    public @NotNull EntityStatus<?> get() {
        return this.entityStatus;
    }

    public boolean hasCap() {
        return this.entityStatus != null;
    }
}
