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
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashSet;

public class PlayerStatus implements IPlayerStatus {
    public static final EntityDataAccessor<Float> FP = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> RUNES = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> POISON = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SCARLET_ROT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> BLOOD_LOSS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> FROSTBITE = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> SLEEP = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> MADNESS = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> DEATH_BLIGHT = SynchedEntityData.defineId(ERMob.class, EntityDataSerializers.INT);
    private final Player player;
    private LinkedHashSet<SpellType<?>> spellSet = new LinkedHashSet<>();
    private ObjectOpenHashSet<AbstractSpell> activeSpells = new ObjectOpenHashSet<>();
    private SpellType<?> selectedSpell;
    private AbstractSpell recentlyActivatedSpell;
    private boolean channelling;
    private EntityType<?> spiritSummon;
    private boolean isTorrentSpawned;
    private double torrentHealth = 77;
    private int talismanPouches;
    private int memoryStones;
    private int quickAccessSlot;
    private boolean usingQuickAccess;
    private ItemStack cachedItem;
    private int useItemTicks;
    private boolean initialized;

    public PlayerStatus(Player player) {
        this.player = player;
//        if (!initialized) {
        player.getEntityData().define(FP, 0.0F);
        player.getEntityData().define(RUNES, 0);
        player.getEntityData().define(POISON, 0);
        player.getEntityData().define(SCARLET_ROT, 0);
        player.getEntityData().define(BLOOD_LOSS, 0);
        player.getEntityData().define(FROSTBITE, 0);
        player.getEntityData().define(SLEEP, 0);
        player.getEntityData().define(MADNESS, 0);
        player.getEntityData().define(DEATH_BLIGHT, 0);
//            this.initialized = true;
//        }
    }

    @Override
    public void setRunes(int runeAmount) {
        this.player.getEntityData().set(RUNES, runeAmount);
    }

    @Override
    public boolean consumeRunes(int amount, boolean forceConsume) {
        int runesHeld = this.getRunes();
        if (runesHeld < amount) {
            return false;
        } else {
            if (forceConsume)
                this.setRunes(runesHeld - amount);

            return true;
        }
    }

    @Override
    public int getRunes() {
        return this.player.getEntityData().get(RUNES);
    }

    @Override
    public float getMaxFP() {
        AttributeInstance maxFP = this.player.getAttribute(EntityAttributeInit.MAX_FP.get());
        return (float)(maxFP == null ? 0.0 : maxFP.getValue());
    }

    @Override
    public boolean consumeFP(float amount, AbstractSpell abstractSpell, boolean forceConsume) {
        float currentFP = this.getFP();
        if (this.player.hasEffect(StatusEffectInit.CERULEAN_HIDDEN.get()) || this.player.getAbilities().instabuild) {
            return true;
        } else if (currentFP < amount) {
            return false;
        } else {
            if (forceConsume) {
                amount = EventFactory.getFPConsumption(this.player, amount, abstractSpell);
                float fpCost = currentFP - amount;
                this.setFP(fpCost);
            }
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
    public boolean isChannelling() {
        return this.channelling;
    }

    @Override
    public void setChannelling(boolean channelling) {
        this.channelling = channelling;
    }

    @Override
    public EntityType<?> getSpiritSummon() {
        return this.spiritSummon;
    }

    @Override
    public void setSpiritSummon(EntityType<?> spiritSummon) {
        this.spiritSummon = spiritSummon;
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
            spellList.add(EntityStatusUtil.storeSpell(spellType));
        }
        compoundTag.put("Spells", spellList);
        compoundTag.putBoolean("Channelling", this.channelling);
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
            this.selectedSpell = EntityStatusUtil.getSpellByName(EntityStatusUtil.getSpellId(nbt, "SelectedSpell"));
        }
        if (nbt.contains("Spells", 9)) {
            ListTag spellList = nbt.getList("Spells", 10);
            for (int i = 0; i < spellList.size(); i++) {
                CompoundTag compoundTag = spellList.getCompound(i);
                this.spellSet.add(EntityStatusUtil.getSpellByName(EntityStatusUtil.getSpellId(compoundTag, "Spell")));
            }
        }
        if (nbt.contains("Channelling", 99)) {
            this.channelling = nbt.getBoolean("Channelling");
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
