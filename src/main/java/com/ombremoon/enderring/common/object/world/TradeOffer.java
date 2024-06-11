package com.ombremoon.enderring.common.object.world;

import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TradeOffer {
    private final ItemStack result;
    private final int cost;
    private int uses;
    private final int maxUses;

    public TradeOffer(CompoundTag nbt) {
        this.result = ItemStack.of(nbt.getCompound("result"));
        this.cost = nbt.getInt("cost");
        this.uses = nbt.getInt("uses");
        if (nbt.contains("maxUses", 99)) {
            this.maxUses = nbt.getInt("maxUses");
        } else {
            this.maxUses = 1;
        }
    }

    public TradeOffer(ItemStack result, int cost) {
        this(result, cost, 0, -1);
    }

    public TradeOffer(ItemStack result, int cost, int maxUses) {
        this(result, cost, 0, maxUses);
    }

    public TradeOffer(ItemStack result, int cost, int uses, int maxUses) {
        this.result = result;
        this.cost = cost;
        this.uses = uses;
        this.maxUses = maxUses;
    }

    public ItemStack getResult() {
        return this.result;
    }

    public int getCost() {
        return this.cost;
    }

    public ItemStack assemble() {
        return this.result.copy();
    }

    public int getUses() {
        return this.uses;
    }

    public void resetUses() {
        this.uses = 0;
    }

    public int getMaxUses() {
        return this.maxUses;
    }

    public void increaseUses() {
        this.uses++;
    }

    public boolean isOutOfStock() {
        return !this.hasInfiniteStock() && this.uses >= this.maxUses;
    }

    public void setOutOfStock() {
        this.uses = this.maxUses;
    }

    public boolean needsRestock() {
        return this.uses > 0;
    }

    public boolean hasInfiniteStock() {
        return this.maxUses == -1;
    }

    public CompoundTag serialize() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("result", this.result.save(new CompoundTag()));
        nbt.putInt("cost", this.cost);
        nbt.putInt("uses", this.uses);
        nbt.putInt("maxUses", this.maxUses);
        return nbt;
    }

    public boolean sellItem(Player player, ItemStack itemStack) {
        return EntityStatusUtil.consumeRunes(player, this.getCost(), true);
    }
}
