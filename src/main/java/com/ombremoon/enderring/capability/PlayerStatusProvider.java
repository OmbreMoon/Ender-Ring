package com.ombremoon.enderring.capability;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.init.entity.EntityInit;
import com.ombremoon.enderring.common.init.entity.MobInit;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerStatusProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<IPlayerStatus> PLAYER_STATUS = CapabilityManager.get(new CapabilityToken<>() {});
    public static final ResourceLocation CAPABILITY_LOCATION = CommonClass.customLocation("player_status");

    private IPlayerStatus playerStatus = new PlayerStatus(77.0D);
    private final LazyOptional<IPlayerStatus> optional = LazyOptional.of(() -> playerStatus);

    public static @NotNull IPlayerStatus get(Player player) {
        return player.getCapability(PLAYER_STATUS).orElseThrow(NullPointerException::new);
    }

    public static @NotNull boolean isPresent(Player player) {
        return player.getCapability(PLAYER_STATUS).isPresent();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_STATUS.orEmpty(cap, this.optional);
    }

    @Override
    public CompoundTag serializeNBT() {
        return this.playerStatus.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.playerStatus.deserializeNBT(nbt);
    }
}
