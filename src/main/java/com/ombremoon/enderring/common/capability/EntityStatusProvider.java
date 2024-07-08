package com.ombremoon.enderring.common.capability;

import com.ombremoon.enderring.CommonClass;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityStatusProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<IEntityStatus> ENTITY_STATUS = CapabilityManager.get(new CapabilityToken<>() {});
//    public static final Capability<IPlayerStatus> PLAYER_STATUS = CapabilityManager.get(new CapabilityToken<>() {});
    public static final ResourceLocation CAPABILITY_LOCATION = CommonClass.customLocation("entity_status");
//    public static final ResourceLocation CAPABILITY_LOCATION = CommonClass.customLocation("player_status");

    private IEntityStatus entityStatus;
//    private IPlayerStatus playerStatus;
    private final LazyOptional<IEntityStatus> optional = LazyOptional.of(() -> entityStatus);
//    private final LazyOptional<IPlayerStatus> optional = LazyOptional.of(() -> playerStatus);

    public EntityStatusProvider(LivingEntity livingEntity) {
        this.entityStatus = new PlayerStatus((Player) livingEntity);
    }
/*

    public EntityStatusProvider(Player player) {
        this.playerStatus = new PlayerStatus(player);
    }
*//*

    public static @NotNull IEntityStatus get(Player player) {
        return player.getCapability(ENTITY_STATUS).orElseThrow(NullPointerException::new);
    }*/

    public static @NotNull IPlayerStatus get(Player player) {
        return (IPlayerStatus) player.getCapability(ENTITY_STATUS).orElseThrow(NullPointerException::new);
    }

    public static @NotNull boolean isPresent(Player player) {
        return player.getCapability(ENTITY_STATUS).isPresent();
    }
/*    public static @NotNull boolean isPresent(Player player) {
        return player.getCapability(PLAYER_STATUS).isPresent();
    }*/

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ENTITY_STATUS.orEmpty(cap, this.optional);
    }
/*    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_STATUS.orEmpty(cap, this.optional);
    }*/

/*    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_STATUS.orEmpty(cap, this.optional);
    }*/

    @Override
    public CompoundTag serializeNBT() {
        return this.entityStatus.serializeNBT();
    }
    /*public CompoundTag serializeNBT() {
        return this.playerStatus.serializeNBT();
    }*/

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.entityStatus.deserializeNBT(nbt);
    }
    /*public void deserializeNBT(CompoundTag nbt) {
        this.playerStatus.deserializeNBT(nbt);
    }*/
}
