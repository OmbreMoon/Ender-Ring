package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.capability.PlayerStatusProvider;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.event.FirstSpawnEvent;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.FlaskUtil;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DebugItem extends Item {

    public DebugItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) pPlayer;
            if (pPlayer.isCrouching()) {
                ModNetworking.openGraceSiteScreen(Component.literal("Grace"),(ServerPlayer) pPlayer);
            } else {
//                ModNetworking.getInstance().selectOrigin(FirstSpawnEvent.CHARACTER_ORIGIN, (ServerPlayer) pPlayer);
                PlayerStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.VIGOR.get(), 27, true);
                PlayerStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.STRENGTH.get(), 45, true);
//                PlayerStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.DEXTERITY.get(), 16, true);
//                PlayerStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.INTELLIGENCE.get(), 2, true);
                PlayerStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.FAITH.get(), 30, true);
//                PlayerStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.ARCANE.get(), 2, true);
//                PlayerStatusUtil.setSelectedSpell((ServerPlayer) pPlayer, SpellInit.HEAL.get());
//                pPlayer.hurt(pLevel.damageSources().generic(), 10);
                this.displayPlayerStats(pPlayer);
                //TODO: SYNC ON RESPAWN
                Constants.LOG.info(String.valueOf(PlayerStatusUtil.getFP(pPlayer)));
            }
            FlaskUtil.resetFlaskCooldowns(pPlayer);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    private void displayPlayerStats(Player player) {
        player.sendSystemMessage(Component.literal("HP: " + player.getHealth()));
        player.sendSystemMessage(Component.literal("LEVEL: " + PlayerStatusUtil.getPlayerStat(player, EntityAttributeInit.RUNE_LEVEL.get())));
        player.sendSystemMessage(Component.literal("VIGOR: " + PlayerStatusUtil.getPlayerStat(player, EntityAttributeInit.VIGOR.get())));
        player.sendSystemMessage(Component.literal("MIND: " + PlayerStatusUtil.getPlayerStat(player, EntityAttributeInit.MIND.get())));
        player.sendSystemMessage(Component.literal("END: " + PlayerStatusUtil.getPlayerStat(player, EntityAttributeInit.ENDURANCE.get())));
        for (WeaponScaling weaponScaling : WeaponScaling.values()) {
            player.sendSystemMessage(Component.literal(weaponScaling.toString() + ": " + PlayerStatusUtil.getPlayerStat(player, weaponScaling.getAttribute())));
        }
    }
}