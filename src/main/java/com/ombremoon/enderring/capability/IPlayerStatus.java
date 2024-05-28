package com.ombremoon.enderring.capability;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.magic.SpellType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.*;

public interface IPlayerStatus extends INBTSerializable<CompoundTag> {

    float getMaxFP();

    float getFP();

    void setFP(float fpAmount);

    boolean consumeFP(float amount, boolean forceConsume);

    float getPhysDefense();

    void setPhysDefense(float physDefense);

    float getMagicDefense();

    void setMagicDefense(float magicDefense);

    float getFireDefense();

    void setFireDefense(float fireDefense);

    float getLightDefense();

    void setLightDefense(float lightDefense);

    float getHolyDefense();

    void setHolyDefense(float holyDefense);

    LinkedHashSet<SpellType<?>> getSpellSet();

    Map<AbstractSpell, SpellInstance> getActiveSpells();

    SpellType<?> getSelectedSpell();

    void setSelectedSpell(SpellType<?> spellType);

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

//    int getCachedSlot();

//    void setCachedSlot(int slot);

    int getUseItemTicks();

    void setUseItemTicks(int ticks);

//    void resetTalismanPouches();
}
