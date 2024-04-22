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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerStatus implements IPlayerStatus {
    private final Map<UUID, AttributeModifier> PLAYER_STATS = new Object2ObjectArrayMap<>();
    private LinkedHashSet<SpellType<?>> spellSet = new LinkedHashSet<>();
    private Map<AbstractSpell, SpellInstance> activeSpells = new HashMap<>();
    private SpellType<?> selectedSpell;
    private double fpAmount;
    private boolean isTorrentSpawned;
    private double torrentHealth;
    private int talismanPouches;
    private int memoryStones;
    private boolean graceSiteFlag;

    public PlayerStatus(double torrentHealth) {
        this.torrentHealth = torrentHealth;
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
    public Map<UUID, AttributeModifier> getStatusAttributeModifiers() {
        return PLAYER_STATS;
    }

    @Override
    public void addStatusAttributeModifiers(UUID uuid, AttributeModifier attributeModifier) {
        PLAYER_STATS.put(uuid, attributeModifier);
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
    public boolean getGraceSiteFlag() {
        return this.graceSiteFlag;
    }

    @Override
    public void setGraceSiteFlag(boolean flag) {
        this.graceSiteFlag = flag;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        ListTag modifierList = new ListTag();
        ListTag spellList = new ListTag();

        compoundTag.putDouble("FP", this.fpAmount);

        if (this.selectedSpell != null)
            compoundTag.putString("SelectedSpell", this.selectedSpell.getResourceLocation().toString());

        for (Map.Entry<UUID, AttributeModifier> entry : PLAYER_STATS.entrySet()) {
            AttributeModifier attributeModifier = entry.getValue();
            modifierList.add(attributeModifier.save());
        }
        for (SpellType<?> spellType : spellSet) {
            spellList.add(PlayerStatusUtil.storeSpell(spellType));
        }
        compoundTag.put("Modifiers", modifierList);
        compoundTag.put("Spells", spellList);
        compoundTag.putBoolean("Torrent", this.isTorrentSpawned);
        compoundTag.putDouble("TorrentHealth", this.torrentHealth);
        compoundTag.putInt("TalismanPouches", this.talismanPouches);
        compoundTag.putInt("MemoryStones", this.memoryStones);

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
        if (nbt.contains("Modifiers", 9)) {
            ListTag modifierList = nbt.getList("Modifiers", 10);
            for (int i = 0; i < modifierList.size(); i++) {
                AttributeModifier attributeModifier = AttributeModifier.load(modifierList.getCompound(i));
                this.PLAYER_STATS.put(attributeModifier.getId(), attributeModifier);
            }
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
        if (nbt.contains("TalismanPouches", 3)) {
            this.talismanPouches = nbt.getInt("TalismanPouches");
        }
        if (nbt.contains("MemoryStones", 3)) {
            this.memoryStones = nbt.getInt("MemoryStones");
        }
        Constants.LOG.info(String.valueOf(nbt));
    }
}
