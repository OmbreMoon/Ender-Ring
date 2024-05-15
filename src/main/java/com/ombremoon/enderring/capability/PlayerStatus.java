package com.ombremoon.enderring.capability;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerStatus implements IPlayerStatus {
    private LinkedHashSet<SpellType<?>> spellSet = new LinkedHashSet<>();
    private Map<AbstractSpell, SpellInstance> activeSpells = new HashMap<>();
    private SpellType<?> selectedSpell;
    private double fpAmount;
    private boolean isTorrentSpawned;
    private double torrentHealth = 77;
    private int talismanPouches;
    private int memoryStones;
    private ItemStack quickAccessItem;
    private int quickAccessSlot;
    private boolean usingQuickAccess;
    private ItemStack cachedItem;
    private int useItemTicks;

    public PlayerStatus(Player player) {
//        player.getEntityData().define();
    }

    @Override
    public double getFPAmount() {
        return this.fpAmount;
    }

    @Override
    public void setFPAmount(double fpAmount) {
        this.fpAmount = fpAmount;
    }

    @Override
    public LinkedHashSet<SpellType<?>> getSpellSet() {
        return this.spellSet;
    }

    @Override
    public Map<AbstractSpell, SpellInstance> getActiveSpells() {
        return this.activeSpells;
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
    public boolean isSpawnedTorrent() {
        return this.isTorrentSpawned;
    }

    @Override
    public void setSpawnTorrent(boolean spawnTorrent) {
        this.isTorrentSpawned = spawnTorrent;
    }

    @Override
    public double getTorrentHealth() {
        return this.torrentHealth;
    }

    @Override
    public void setTorrentHealth(double torrentHealth) {
        this.torrentHealth = torrentHealth;
    }

    @Override
    public int getTalismanPouches() {
        return this.talismanPouches;
    }

    @Override
    public void increaseTalismanPouches() {
        this.talismanPouches = Math.min(this.talismanPouches + 1, 3);
    }

    @Override
    public int getMemoryStones() {
        return this.memoryStones;
    }

    @Override
    public void increaseMemoryStones() {
        this.memoryStones = Math.min(this.memoryStones + 1, 8);
    }

    @Override
    public int getQuickAccessSlot() {
        return this.quickAccessSlot;
    }

    @Override
    public void setQuickAccessSlot(int slot) {
        this.quickAccessSlot = slot;
    }

    @Override
    public boolean isUsingQuickAccess() {
        return this.usingQuickAccess;
    }

    @Override
    public void setUsingQuickAccess(boolean usingQuickAccess) {
        this.usingQuickAccess = usingQuickAccess;
    }

    @Override
    public ItemStack getCachedItem() {
        return this.cachedItem;
    }

    @Override
    public void setCachedItem(ItemStack cachedItem) {
        this.cachedItem = cachedItem;
    }

    @Override
    public int getUseItemTicks() {
        return this.useItemTicks;
    }

    @Override
    public void setUseItemTicks(int ticks) {
        this.useItemTicks = ticks;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        CompoundTag itemStackTag = new CompoundTag();
        ListTag spellList = new ListTag();

        compoundTag.putDouble("FP", this.fpAmount);

        if (this.selectedSpell != null)
            compoundTag.putString("SelectedSpell", this.selectedSpell.getResourceLocation().toString());

        for (SpellType<?> spellType : spellSet) {
            spellList.add(PlayerStatusUtil.storeSpell(spellType));
        }
        compoundTag.put("Spells", spellList);
        compoundTag.putBoolean("Torrent", this.isTorrentSpawned);
        compoundTag.putDouble("TorrentHealth", this.torrentHealth);
        compoundTag.putInt("TalismanPouches", this.talismanPouches);
        compoundTag.putInt("MemoryStones", this.memoryStones);
        compoundTag.putInt("SelectedSlot", this.quickAccessSlot);

        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("FP", 99)) {
            this.fpAmount = nbt.getDouble("FP");
        }
        if (nbt.contains("SelectedSpell", 8)) {
            this.selectedSpell = PlayerStatusUtil.getSpellByName(PlayerStatusUtil.getSpellId(nbt, "SelectedSpell"));
        }
        if (nbt.contains("Spells", 9)) {
            ListTag spellList = nbt.getList("Spells", 10);
            for (int i = 0; i < spellList.size(); i++) {
                CompoundTag compoundTag = spellList.getCompound(i);
                this.spellSet.add(PlayerStatusUtil.getSpellByName(PlayerStatusUtil.getSpellId(compoundTag, "Spell")));
            }
        }
        if (nbt.contains("Torrent", 99)) {
            this.isTorrentSpawned = nbt.getBoolean("Torrent");
        }
        if (nbt.contains("TorrentHealth", 6)) {
            this.torrentHealth = nbt.getDouble("TorrentHealth");
        }
        if (nbt.contains("TalismanPouches", 99)) {
            this.talismanPouches = nbt.getInt("TalismanPouches");
        }
        if (nbt.contains("MemoryStones", 99)) {
            this.memoryStones = nbt.getInt("MemoryStones");
        }
        if (nbt.contains("SelectedSlot", 99)) {
            this.quickAccessSlot = nbt.getInt("SelectedSlot");
        }
        Constants.LOG.info(String.valueOf(nbt));
    }
}
