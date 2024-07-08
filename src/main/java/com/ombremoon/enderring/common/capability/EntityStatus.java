package com.ombremoon.enderring.common.capability;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.event.custom.EventFactory;
import com.ombremoon.enderring.util.EntityStatusUtil;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;

public class EntityStatus implements IEntityStatus {
    public static final EntityDataAccessor<Integer> POISON = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SCARLET_ROT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> BLOOD_LOSS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FROSTBITE = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SLEEP = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MADNESS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DEATH_BLIGHT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    protected final LivingEntity livingEntity;
    protected LinkedHashSet<SpellType<?>> spellSet = new LinkedHashSet<>();
    protected ObjectOpenHashSet<AbstractSpell> activeSpells = new ObjectOpenHashSet<>();
    protected SpellType<?> selectedSpell;
    protected AbstractSpell recentlyActivatedSpell;

    public EntityStatus(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
        livingEntity.getEntityData().define(POISON, 0);
        livingEntity.getEntityData().define(SCARLET_ROT, 0);
        livingEntity.getEntityData().define(BLOOD_LOSS, 0);
        livingEntity.getEntityData().define(FROSTBITE, 0);
        livingEntity.getEntityData().define(SLEEP, 0);
        livingEntity.getEntityData().define(MADNESS, 0);
        livingEntity.getEntityData().define(DEATH_BLIGHT, 0);
    }

    @Override
    public LinkedHashSet<SpellType<?>> getSpellSet() {
        return this.spellSet;
    }

    @Override
    public ObjectOpenHashSet<AbstractSpell> getActiveSpells() {
        return this.activeSpells;
    }

    @Override
    public AbstractSpell getRecentlyActivatedSpell() {
        return this.recentlyActivatedSpell;
    }

    @Override
    public void setRecentlyActivatedSpell(AbstractSpell recentlyActivatedSpell) {
        this.recentlyActivatedSpell = recentlyActivatedSpell;
    }

    @Override
    public SpellType<?> getSelectedSpell() {
        return this.selectedSpell;
    }

    @Override
    public void setSelectedSpell(SpellType<?> selectedSpell) {
        this.selectedSpell = selectedSpell;
    }

    @Override
    public void defineEntityData(LivingEntity livingEntity) {

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
