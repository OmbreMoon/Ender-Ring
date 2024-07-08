package com.ombremoon.enderring.common.capability;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.LinkedHashSet;

public interface IEntityStatus extends INBTSerializable<CompoundTag> {

    LinkedHashSet<SpellType<?>> getSpellSet();

    ObjectOpenHashSet<AbstractSpell> getActiveSpells();

    AbstractSpell getRecentlyActivatedSpell();

    void setRecentlyActivatedSpell(AbstractSpell spellType);

    SpellType<?> getSelectedSpell();

    void setSelectedSpell(SpellType<?> spellType);

    void defineEntityData(LivingEntity livingEntity);
}
