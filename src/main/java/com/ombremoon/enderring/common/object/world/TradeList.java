package com.ombremoon.enderring.common.object.world;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class TradeList extends ArrayList<TradeOffer> {

    public TradeList() {
    }

    private TradeList(int capacity) {
        super(capacity);
    }

    public TradeList(CompoundTag nbt) {
        ListTag listTag = nbt.getList("TradeList", 10);

        for (int i = 0; i < listTag.size(); i++) {
            this.add(new TradeOffer(listTag.getCompound(i)));
        }
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeCollection(this, ((buffer, tradeOffer) -> {
            buffer.writeItem(tradeOffer.getResult());
            buffer.writeBoolean(tradeOffer.isOutOfStock());
            buffer.writeInt(tradeOffer.getCost());
            buffer.writeInt(tradeOffer.getUses());
            buffer.writeInt(tradeOffer.getMaxUses());
        }));
    }

    public static TradeList decode(FriendlyByteBuf buf) {
        return buf.readCollection(TradeList::new, reader -> {
            ItemStack result = reader.readItem();
            boolean outOfStock = reader.readBoolean();
            int cost = reader.readInt();
            int uses = reader.readInt();
            int maxUses = reader.readInt();
            TradeOffer merchantOffer = new TradeOffer(result, cost, uses, maxUses);
            if (outOfStock) {
                merchantOffer.setOutOfStock();
            }
            return merchantOffer;
        });
    }

    public CompoundTag serialize() {
        CompoundTag nbt = new CompoundTag();
        ListTag listTag = new ListTag();

        for (int i = 0; i < this.size(); i++) {
            TradeOffer merchantOffer = this.get(i);
            listTag.add(merchantOffer.serialize());
        }

        nbt.put("TradeList", listTag);
        return nbt;
    }
}
