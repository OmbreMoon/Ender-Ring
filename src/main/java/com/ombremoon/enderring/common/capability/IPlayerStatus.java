package com.ombremoon.enderring.common.capability;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public interface IPlayerStatus extends INBTSerializable<CompoundTag> {

    float getMaxFP();

    float getFP();

    void setFP(float fpAmount);

    boolean consumeFP(float amount, boolean forceConsume);

    LinkedHashSet<SpellType<?>> getSpellSet();

    ObjectOpenHashSet<AbstractSpell> getActiveSpells();

    SpellType<?> getSelectedSpell();

    void setSelectedSpell(SpellType<?> spellType);

    EntityType<?> getSpiritSummon();

    void setSpiritSummon(EntityType<?> spiritSummon);

    boolean isSpawnedTorrent();

    void setSpawnTorrent(boolean spawnTorrent);

    double getTorrentHealth();

    void setTorrentHealth(double torrentHealth);

    int getTalismanPouches();

    void increaseTalismanPouches();

    int getMemoryStones();

    void increaseMemoryStones();

    int getQuickAccessSlot();

    void setQuickAccessSlot(int slot);

    boolean isUsingQuickAccess();

    void setUsingQuickAccess(boolean usingQuickAccess);

    ItemStack getCachedItem();

    void setCachedItem(ItemStack cachedItem);

    int getUseItemTicks();

    void setUseItemTicks(int ticks);

//    void resetTalismanPouches();
}
