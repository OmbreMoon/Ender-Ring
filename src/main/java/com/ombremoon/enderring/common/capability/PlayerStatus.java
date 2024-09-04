package com.ombremoon.enderring.common.capability;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.object.entity.spirit.ISpiritAsh;
import com.ombremoon.enderring.event.custom.EventFactory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PlayerStatus extends EntityStatus<Player> {
    public static final EntityDataAccessor<Float> FP = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Integer> RUNES = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    private Player player;
    private boolean channelling;
    private EntityType<? extends ISpiritAsh> spiritSummon;
    private boolean isTorrentSpawned;
    private double torrentHealth = 77;
    private int talismanPouches;
    private int memoryStones;
    private int quickAccessSlot;
    private boolean usingQuickAccess;
    private ItemStack cachedItem;
    private int useItemTicks;

    public PlayerStatus() {
    }

    @Override
    public void initStatus(LivingEntity livingEntity) {
        this.player = (Player) livingEntity;
        super.initStatus(livingEntity);
    }

    @Override
    public void defineEntityData(LivingEntity livingEntity) {
        super.defineEntityData(livingEntity);
        livingEntity.getEntityData().define(FP, 0.0F);
        livingEntity.getEntityData().define(RUNES, 0);
    }

    public void setRunes(int runeAmount) {
        this.player.getEntityData().set(RUNES, runeAmount);
    }

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

    public int getRunes() {
        return this.player.getEntityData().get(RUNES);
    }

    public float getMaxFP() {
        AttributeInstance maxFP = this.player.getAttribute(EntityAttributeInit.MAX_FP.get());
        return (float)(maxFP == null ? 0.0 : maxFP.getValue());
    }

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

    public float getFP() {
        return this.getMaxFP() == 0.0F ? 0.0F : this.player.getEntityData().get(FP);
    }

    public void setFP(float value) {
        float fpAmount = Math.max(Math.min(value, this.getMaxFP()), 0.0F);
        this.player.getEntityData().set(FP, fpAmount);
    }

    public boolean isChannelling() {
        return this.channelling;
    }

    public void setChannelling(boolean channelling) {
        this.channelling = channelling;
    }

    public EntityType<? extends ISpiritAsh> getSpiritSummon() {
        return this.spiritSummon;
    }

    public void setSpiritSummon(EntityType<? extends ISpiritAsh> spiritSummon) {
        this.spiritSummon = spiritSummon;
    }
    
    public boolean isSpawnedTorrent() {
        return this.isTorrentSpawned;
    }

    public void setSpawnTorrent(boolean spawnTorrent) {
        this.isTorrentSpawned = spawnTorrent;
    }

    public double getTorrentHealth() {
        return this.torrentHealth;
    }

    
    public void setTorrentHealth(double torrentHealth) {
        this.torrentHealth = torrentHealth;
    }

    public int getTalismanPouches() {
        return this.talismanPouches;
    }
    
    public void increaseTalismanPouches() {
        this.talismanPouches = Math.min(this.talismanPouches + 1, 3);
    }

    public int getMemoryStones() {
        return this.memoryStones;
    }

    public void increaseMemoryStones() {
        this.memoryStones = Math.min(this.memoryStones + 1, 8);
    }
    
    public int getQuickAccessSlot() {
        return this.quickAccessSlot;
    }

    public void setQuickAccessSlot(int slot) {
        this.quickAccessSlot = slot;
    }
    
    public boolean isUsingQuickAccess() {
        return this.usingQuickAccess;
    }
    
    public void setUsingQuickAccess(boolean usingQuickAccess) {
        this.usingQuickAccess = usingQuickAccess;
    }

    public ItemStack getCachedItem() {
        return this.cachedItem;
    }

    public void setCachedItem(ItemStack cachedItem) {
        this.cachedItem = cachedItem;
    }

    public int getUseItemTicks() {
        return this.useItemTicks;
    }

    public void setUseItemTicks(int ticks) {
        this.useItemTicks = ticks;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = super.serializeNBT();
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
        super.deserializeNBT(nbt);
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
