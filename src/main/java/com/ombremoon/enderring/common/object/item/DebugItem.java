package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.client.gui.screen.PlayerStatusScreen;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.MobInit;
import com.ombremoon.enderring.common.init.entity.ProjectileInit;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.object.entity.npc.TestDummy;
import com.ombremoon.enderring.common.object.entity.projectile.spell.GlintstoneArcEntity;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.event.FirstSpawnEvent;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import com.ombremoon.enderring.util.FlaskUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
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
                EntityStatusUtil.setSelectedSpell(serverPlayer, SpellInit.CATCH_FLAME.get());
//                pPlayer.hurt(DamageUtil.moddedDamageSource(pLevel, ModDamageTypes.MAGICAL), 10.0F);
//                ModNetworking.selectOrigin(FirstSpawnEvent.CHARACTER_ORIGIN, (ServerPlayer) pPlayer);
//                this.setStats(pPlayer);
//                this.displayPlayerStats(pPlayer);
            }
            FlaskUtil.resetFlaskCooldowns(pPlayer);
        } else {
            Minecraft.getInstance().setScreen(new PlayerStatusScreen(Component.literal("Status")));
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
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
        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.VIGOR.get(), 1, true);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.STRENGTH.get(), 99);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.MIND.get(), 99);
//        EntityStatusUtil.setBaseStat(pPlayer, EntityAttributeInit.DEXTERITY.get(), 30);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.INTELLIGENCE.get(), 9);
        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.ENDURANCE.get(), 35);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.FAITH.get(), 99);
        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.ARCANE.get(), 33);
        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.RUNE_LEVEL.get(), 132);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.PHYS_NEGATE.get(), 0);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.HOLY_NEGATE.get(), 0);
    }
}