package com.ombremoon.enderring.capability;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.Map;
import java.util.UUID;

public class PlayerStatus implements IPlayerStatus {
    private final Map<UUID, AttributeModifier> PLAYER_STATS = new Object2ObjectArrayMap<>();

    @Override
    public Map<UUID, AttributeModifier> getStatusAttributeModifiers() {
        return PLAYER_STATS;
    }

    @Override
    public void addStatusAttributeModifiers(UUID uuid, AttributeModifier attributeModifier) {
        PLAYER_STATS.put(uuid, attributeModifier);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        ListTag listTag = new ListTag();
        for (Map.Entry<UUID, AttributeModifier> entry : PLAYER_STATS.entrySet()) {
            AttributeModifier attributeModifier = entry.getValue();
            listTag.add(attributeModifier.save());
        }
        compoundTag.put("Modifiers", listTag);

        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("Modifiers", 9)) {
            ListTag listTag = nbt.getList("Modifiers", 10);
            for (int i = 0; i < listTag.size(); i++) {
                AttributeModifier attributeModifier = AttributeModifier.load(listTag.getCompound(i));
                this.PLAYER_STATS.put(attributeModifier.getId(), attributeModifier);
            }
        }
    }
}
