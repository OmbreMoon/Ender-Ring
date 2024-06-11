package com.ombremoon.enderring.common.object.entity.npc;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.world.TradeOffer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Predicate;

public class MerchantTrades {
    private static final int INFINITE_SUPPLY = -1;
    public static final Map<Merchant, Object2ObjectOpenHashMap<Predicate<MerchantNPCMob>, Trade[]>> TRADES = Util.make(Maps.newHashMap(), map -> {
        map.put(Merchant.TEST, predicateMap(ImmutableMap.of(MerchantNPCMob::shouldDropRunes, new Trade[]{new KeyItemForRunes(ItemInit.STONESWORD_KEY.get(), 4000, 1), new KeyItemForRunes(EquipmentInit.BONE_BOLT.get(), 40, INFINITE_SUPPLY)}, merchantNPCMob -> merchantNPCMob instanceof TestDummy dummy && dummy.isSmithing(), new Trade[]{new KeyItemForRunes(ItemInit.MISSIONARY_COOKBOOK_ONE.get(), 1000, 1), new KeyItemForRunes(ItemInit.CRACKED_POT.get(), 300, 3)})));
    });

    private static Object2ObjectOpenHashMap<Predicate<MerchantNPCMob>, Trade[]> predicateMap(ImmutableMap<Predicate<MerchantNPCMob>, Trade[]> map) {
        return new Object2ObjectOpenHashMap<>(map);
    }

    static class KeyItemForRunes implements Trade {
        private final Item item;
        private final int cost;
        private final int maxUses;

        public KeyItemForRunes(ItemLike item, int cost, int maxUses) {
            this.item = item.asItem();
            this.cost = cost;
            this.maxUses = maxUses;
        }

        @Override
        public TradeOffer getOffer(Entity merchant) {
            ItemStack itemStack = new ItemStack(this.item);
            return new TradeOffer(itemStack, cost, maxUses);
        }
    }

    public interface Trade {
        @Nullable
        TradeOffer getOffer(Entity merchant);
    }
}
