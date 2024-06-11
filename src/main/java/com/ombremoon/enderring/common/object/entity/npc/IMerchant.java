package com.ombremoon.enderring.common.object.entity.npc;

import com.ombremoon.enderring.common.object.world.TradeList;
import com.ombremoon.enderring.common.object.world.TradeOffer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public interface IMerchant {
    void setTrader(@Nullable Player player);

    @Nullable
    Player getTrader();

    TradeList getTrades();

    void overrideTrades(TradeList tradeList);

    void notifyTrade(TradeOffer merchantOffer);

    SoundEvent getTradeSound();

    default void openMerchantMenu(ServerPlayer player, Component displayName) {
        NetworkHooks.openScreen(player, new SimpleMenuProvider(((pContainerId, pPlayerInventory, pPlayer) -> {
            return null;
        }), displayName));
        TradeList tradeList = this.getTrades();
        if (!tradeList.isEmpty()) {
            //Send sync packet
        }
    }

    boolean isClientSide();
}
