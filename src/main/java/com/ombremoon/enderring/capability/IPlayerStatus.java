package com.ombremoon.enderring.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;
import java.util.UUID;

public interface IPlayerStatus extends INBTSerializable<CompoundTag> {

    Map<UUID, AttributeModifier> getStatusAttributeModifiers();

    void addStatusAttributeModifiers(UUID uuid, AttributeModifier attributeModifier);
}
