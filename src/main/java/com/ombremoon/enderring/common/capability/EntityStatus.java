package com.ombremoon.enderring.common.capability;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.util.EntityStatusUtil;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.LinkedHashSet;

public class EntityStatus<T extends LivingEntity> implements INBTSerializable<CompoundTag> {
    public static final EntityDataAccessor<Integer> POISON = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SCARLET_ROT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> BLOOD_LOSS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FROSTBITE = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SLEEP = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MADNESS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DEATH_BLIGHT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);

    protected T livingEntity;
    protected LinkedHashSet<SpellType<?>> spellSet = new LinkedHashSet<>();
    protected ObjectOpenHashSet<AbstractSpell> activeSpells = new ObjectOpenHashSet<>();
    protected SpellType<?> selectedSpell;
    protected AbstractSpell recentlyActivatedSpell;
    protected boolean initialized = false;

    public EntityStatus() {
    }

    @SuppressWarnings("unchecked")
    public void initStatus(LivingEntity livingEntity) {
        this.livingEntity = (T) livingEntity;
    }

    public boolean isInitialized() {
        return this.initialized = true;
    }

    public LinkedHashSet<SpellType<?>> getSpellSet() {
        return this.spellSet;
    }

    public ObjectOpenHashSet<AbstractSpell> getActiveSpells() {
        return this.activeSpells;
    }

    public AbstractSpell getRecentlyActivatedSpell() {
        return this.recentlyActivatedSpell;
    }

    public void setRecentlyActivatedSpell(AbstractSpell recentlyActivatedSpell) {
        this.recentlyActivatedSpell = recentlyActivatedSpell;
    }

    public SpellType<?> getSelectedSpell() {
        return this.selectedSpell;
    }

    public void setSelectedSpell(SpellType<?> selectedSpell) {
        this.selectedSpell = selectedSpell;
    }

    public void defineEntityData(LivingEntity livingEntity) {
        this.initialized = true;
        livingEntity.getEntityData().define(POISON, 0);
        livingEntity.getEntityData().define(SCARLET_ROT, 0);
        livingEntity.getEntityData().define(BLOOD_LOSS, 0);
        livingEntity.getEntityData().define(FROSTBITE, 0);
        livingEntity.getEntityData().define(SLEEP, 0);
        livingEntity.getEntityData().define(MADNESS, 0);
        livingEntity.getEntityData().define(DEATH_BLIGHT, 0);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        ListTag spellList = new ListTag();

        if (this.selectedSpell != null)
            compoundTag.putString("SelectedSpell", this.selectedSpell.getResourceLocation().toString());

        for (SpellType<?> spellType : spellSet) {
            spellList.add(EntityStatusUtil.storeSpell(spellType));
        }
        compoundTag.put("Spells", spellList);

        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("SelectedSpell", 8)) {
            this.selectedSpell = EntityStatusUtil.getSpellByName(EntityStatusUtil.getSpellId(nbt, "SelectedSpell"));
        }
        if (nbt.contains("Spells", 9)) {
            ListTag spellList = nbt.getList("Spells", 10);
            for (int i = 0; i < spellList.size(); i++) {
                CompoundTag compoundTag = spellList.getCompound(i);
                this.spellSet.add(EntityStatusUtil.getSpellByName(EntityStatusUtil.getSpellId(compoundTag, "Spell")));
            }
        }
    }
}
