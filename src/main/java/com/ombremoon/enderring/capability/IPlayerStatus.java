package com.ombremoon.enderring.capability;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.magic.SpellType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public interface IPlayerStatus extends INBTSerializable<CompoundTag> {

    double getFPAmount();

    void setFPAmount(double fpAmount);

    Map<UUID, AttributeModifier> getStatusAttributeModifiers();

    void addStatusAttributeModifiers(UUID uuid, AttributeModifier attributeModifier);

    LinkedHashSet<SpellType<?>> getSpellSet();

    Map<AbstractSpell, SpellInstance> getActiveSpells();

    SpellType<?> getSelectedSpell();

    void setSelectedSpell(SpellType<?> spellType);
}
