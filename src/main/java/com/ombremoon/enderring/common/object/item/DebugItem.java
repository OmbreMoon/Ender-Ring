package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.data.Saturations;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.world.effect.StatusEffectInstance;
import com.ombremoon.enderring.event.FirstSpawnEvent;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.FlaskUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

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
                EntityStatusUtil.setSelectedSpell(serverPlayer, SpellInit.CATCH_FLAME.get());
                ModNetworking.selectOrigin(FirstSpawnEvent.CHARACTER_ORIGIN, (ServerPlayer) pPlayer);
//                this.setStats(pPlayer);
//                this.displayPlayerStats(pPlayer);
            }
            FlaskUtil.resetFlaskCooldowns(pPlayer);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return super.useOn(pContext);
    }

    private void displayPlayerStats(Player player) {
        player.sendSystemMessage(Component.literal("HP: " + player.getHealth()));
        player.sendSystemMessage(Component.literal("LEVEL: " + EntityStatusUtil.getEntityAttribute(player, EntityAttributeInit.RUNE_LEVEL.get())));
        player.sendSystemMessage(Component.literal("VIGOR: " + EntityStatusUtil.getEntityAttribute(player, EntityAttributeInit.VIGOR.get())));
        player.sendSystemMessage(Component.literal("MIND: " + EntityStatusUtil.getEntityAttribute(player, EntityAttributeInit.MIND.get())));
        player.sendSystemMessage(Component.literal("END: " + EntityStatusUtil.getEntityAttribute(player, EntityAttributeInit.ENDURANCE.get())));
        for (WeaponScaling weaponScaling : WeaponScaling.values()) {
            player.sendSystemMessage(Component.literal(weaponScaling.toString() + ": " + EntityStatusUtil.getEntityAttribute(player, weaponScaling.getAttribute())));
        }
    }

    private void setStats(Player player) {
//        EntityStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.VIGOR.get(), 16, true);
//        EntityStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.STRENGTH.get(), 30);
//        EntityStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.DEXTERITY.get(), 30);
//        EntityStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.INTELLIGENCE.get(), 2);
//        EntityStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.FAITH.get(), 30);
        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.ARCANE.get(), 20);
    }
}