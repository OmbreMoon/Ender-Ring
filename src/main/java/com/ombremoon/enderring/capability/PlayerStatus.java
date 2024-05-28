package com.ombremoon.enderring.capability;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class PlayerStatus implements IPlayerStatus {
    public static final EntityDataAccessor<Float> FP = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> PHYSICAL_DEF = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> MAGICAL_DEF = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> FIRE_DEF = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> LIGHTNING_DEF = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Float> HOLY_DEF = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
    private final Player player;
    private LinkedHashSet<SpellType<?>> spellSet = new LinkedHashSet<>();
    private Map<AbstractSpell, SpellInstance> activeSpells = new HashMap<>();
    private SpellType<?> selectedSpell;
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
        this.player = player;
        player.getEntityData().define(FP, 0.0F);
        player.getEntityData().define(PHYSICAL_DEF, 0.0F);
        player.getEntityData().define(MAGICAL_DEF, 0.0F);
        player.getEntityData().define(FIRE_DEF, 0.0F);
        player.getEntityData().define(LIGHTNING_DEF, 0.0F);
        player.getEntityData().define(HOLY_DEF, 0.0F);
    }

    @Override
    public float getMaxFP() {
        AttributeInstance maxFP = this.player.getAttribute(EntityAttributeInit.MAX_FP.get());
        return (float)(maxFP == null ? 0.0 : maxFP.getValue());
    }

    @Override
    public boolean consumeFP(float amount, boolean forceConsume) {
        float currentFP = this.getFP();
        if (currentFP < amount) {
            return false;
        } else if (this.player.hasEffect(StatusEffectInit.CERULEAN_HIDDEN.get()) || this.player.getAbilities().instabuild) {
            return true;
        } else {
            if (forceConsume)
                this.setFP(currentFP - amount);
            return true;
        }
    }

    @Override
    public float getFP() {
        return this.getMaxFP() == 0.0F ? 0.0F : this.player.getEntityData().get(FP);
    }

    @Override
    public void setFP(float value) {
        float fpAmount = Math.max(Math.min(value, this.getMaxFP()), 0.0F);
        this.player.getEntityData().set(FP, fpAmount);
    }

    @Override
    public float getPhysDefense() {
        return this.player.getEntityData().get(PHYSICAL_DEF);
    }

    @Override
    public void setPhysDefense(float physDefense) {
        this.player.getEntityData().set(PHYSICAL_DEF, physDefense);
    }

    @Override
    public float getMagicDefense() {
        return this.player.getEntityData().get(MAGICAL_DEF);
    }

    @Override
    public void setMagicDefense(float magicDefense) {
        this.player.getEntityData().set(MAGICAL_DEF, magicDefense);
    }

    @Override
    public float getFireDefense() {
        return this.player.getEntityData().get(FIRE_DEF);
    }

    @Override
    public void setFireDefense(float fireDefense) {
        this.player.getEntityData().set(FIRE_DEF, fireDefense);
    }

    @Override
    public float getLightDefense() {
        return this.player.getEntityData().get(LIGHTNING_DEF);
    }

    @Override
    public void setLightDefense(float lightDefense) {
        this.player.getEntityData().set(LIGHTNING_DEF, lightDefense);
    }

    @Override
    public float getHolyDefense() {
        return this.player.getEntityData().get(HOLY_DEF);
    }

    @Override
    public void setHolyDefense(float holyDefense) {
        this.player.getEntityData().set(LIGHTNING_DEF, holyDefense);
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
        ListTag spellList = new ListTag();

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
