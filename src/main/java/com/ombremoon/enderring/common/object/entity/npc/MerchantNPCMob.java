package com.ombremoon.enderring.common.object.entity.npc;

import com.ombremoon.enderring.common.object.entity.NPCMob;
import com.ombremoon.enderring.common.object.world.TradeOffer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class MerchantNPCMob extends NPCMob<MerchantNPCMob> implements IMerchant {
    @Nullable
    private Player tradingPlayer;
    @Nullable
    protected TradeList tradeList;

    protected MerchantNPCMob(EntityType<? extends MerchantNPCMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        TradeList tradeList = this.getTrades();
        if (!tradeList.isEmpty()) {
            pCompound.put("Trades", tradeList.serialize());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("Trades", 10)) {
            this.tradeList = new TradeList(pCompound.getCompound("Trades"));
        }
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!this.level().isClientSide && !this.isAngryAt(pPlayer)) {
//            this.startTrading(pPlayer);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(pPlayer, pHand);
    }

    private void startTrading(Player player) {
        this.setTrader(player);
        this.openMerchantMenu(player, this.getDisplayName());
    }

    protected void stopTrading() {
        this.setTrader(null);
    }

    protected void updateTrades() {
        NPCData npcData = this.getNPCData();
        Object2ObjectOpenHashMap<Predicate<MerchantNPCMob>, MerchantTrades.Trade[]> objectMap = MerchantTrades.TRADES.get(npcData.getMerchant());
        if (objectMap != null && !objectMap.isEmpty()) {
            MerchantTrades.Trade[] trades = new MerchantTrades.Trade[]{};
            for (var entry : objectMap.entrySet()) {
                if (entry.getKey().test(this)) {
                    trades = this.updateTrades(trades, entry.getValue());
                }
            }
            if (trades.length != 0) {
                TradeList tradeList = this.getTrades();
                this.addTradeFromList(tradeList, trades);
            }
        }
    }

    private MerchantTrades.Trade[] updateTrades(MerchantTrades.Trade[] trade1, MerchantTrades.Trade[] trade2) {
        return Stream.concat(Arrays.stream(trade1), Arrays.stream(trade2)).toArray(size -> (MerchantTrades.Trade[]) Array.newInstance(trade1.getClass().getComponentType(), size));
    }

    protected void addTradeFromList(TradeList tradeList, MerchantTrades.Trade[] trades) {
        tradeList.clear();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < trades.length; i++) {
            set.add(i);
        }

        for (Integer integer : set) {
            MerchantTrades.Trade trade = trades[integer];
            TradeOffer tradeOffer = trade.getOffer(this);
            if (tradeOffer != null) {
                tradeList.add(tradeOffer);
            }
        }
    }

    @Override
    public void setNPCData(NPCData data) {
        NPCData npcData = this.getNPCData();
        if (npcData.getMerchant() != data.getMerchant()) {
            this.tradeList = null;
        }
        super.setNPCData(data);
    }

    @Override
    public void setTrader(@Nullable Player player) {
        this.tradingPlayer = player;
    }

    @Nullable
    @Override
    public Player getTrader() {
        return this.tradingPlayer;
    }

    public boolean isTrading() {
        return this.tradingPlayer != null;
    }

    public void setTrades(TradeList trades) {
        this.tradeList = trades;
    }

    @Override
    public TradeList getTrades() {
        if (this.tradeList == null) {
            this.tradeList = new TradeList();
            this.updateTrades();
        }
        return this.tradeList;
    }

    @Override
    public void overrideTrades(TradeList tradeList) {
    }

    //TODO: ADD TRADE EVENT
    @Override
    public void notifyTrade(TradeOffer merchantOffer) {
        merchantOffer.increaseUses();
        if (this.tradingPlayer instanceof ServerPlayer) {
            //TRADE CRITERIA TRIGGER
        }
        //POST EVENT
    }

    @Override
    public SoundEvent getTradeSound() {
        return SoundEvents.AMETHYST_BLOCK_RESONATE;
    }

    @Override
    public boolean isClientSide() {
        return this.level().isClientSide;
    }
}
