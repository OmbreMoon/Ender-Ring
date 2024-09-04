package com.ombremoon.enderring.common.object.item;

import com.mojang.logging.LogUtils;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.gui.screen.PlayerStatusScreen;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.capability.EntityStatus;
import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.Saturations;
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
import com.ombremoon.enderring.util.math.NoiseGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
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
                ModNetworking.selectOrigin(FirstSpawnEvent.CHARACTER_ORIGIN, (ServerPlayer) pPlayer);
            } else {
                this.setStats(pPlayer);
                EntityStatusUtil.setSelectedSpell(serverPlayer, SpellInit.CATCH_FLAME.get());
                EntityStatusUtil.setSpiritSummon(serverPlayer, MobInit.TEST_DUMMY.get());
                LogUtils.getLogger().debug(EntityStatusUtil.getSpiritSummon(serverPlayer).getDescriptionId());
                LogUtils.getLogger().debug(String.valueOf(EntityStatusUtil.getFP(serverPlayer)));
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    private void setStats(Player player) {
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.VIGOR.get(), 1, true);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.MIND.get(), 1);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.ENDURANCE.get(), 1);
        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.STRENGTH.get(), 60);
        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.DEXTERITY.get(), 60);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.INTELLIGENCE.get(), 2);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.FAITH.get(), 1);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.ARCANE.get(), 1);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.RUNE_LEVEL.get(), 1);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.PHYS_NEGATE.get(), 0);
//        EntityStatusUtil.setBaseStat(player, EntityAttributeInit.HOLY_NEGATE.get(), 0);
    }
}