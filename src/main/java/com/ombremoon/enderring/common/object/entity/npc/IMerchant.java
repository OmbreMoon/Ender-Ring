package com.ombremoon.enderring.common.object.entity.npc;

import com.ombremoon.enderring.common.object.world.TradeOffer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public interface IMerchant {
    void setTrader(@Nullable Player player);

    @Nullable
    Player getTrader();

    TradeList getTrades();

    void overrideTrades(TradeList tradeList);

    void notifyTrade(TradeOffer merchantOffer);

    SoundEvent getTradeSound();

    default void openMerchantMenu(Player player, Component menuName) {

    }

    boolean isClientSide();
}
