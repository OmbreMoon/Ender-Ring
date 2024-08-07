package com.ombremoon.enderring.common.object.item.equipment.weapon.magic;

import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import com.ombremoon.enderring.common.init.StatInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.Classification;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.ExtendedServerPlayerPatch;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CatalystWeapon extends MeleeWeapon {
    private final MagicType magicType;
    private Set<Classification> classifications;
    private float spellBoost;

    public CatalystWeapon(MagicType magicType, Properties properties, float spellBoost, Classification... classifications) {
        this(magicType, properties);
        this.classifications = new HashSet<>(Arrays.asList(classifications));
        this.spellBoost = spellBoost;
    }

    public CatalystWeapon(MagicType magicType, Properties properties) {
        super(-2.3F, properties);
        this.magicType = magicType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        SpellType<?> spell = EntityStatusUtil.getSelectedSpell(pPlayer);
        if (EntityStatusUtil.isChannelling(pPlayer)) {
            return InteractionResultHolder.pass(itemStack);
        }

        if (spell != null) {
            AbstractSpell abstractSpell = spell.createSpell();
            if (abstractSpell != null){
                itemStack.getOrCreateTag().putInt("CastTime", abstractSpell.getCastTime());

                pPlayer.startUsingItem(pUsedHand);
                return InteractionResultHolder.consume(itemStack);
            }
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (stack.getTag() != null) {
            float scaling = DamageUtil.calculateMagicScaling(this.getModifiedWeapon(stack), player, this.getWeaponLevel(stack), this.magicType.getWeaponDamage());
            stack.getTag().putInt(this.magicType.getName(), Mth.floor(scaling));
            return;
        }

        Constants.LOG.warn("Shouldn't be running every tick!");
        stack.getOrCreateTag();
    }

    public float getMagicScaling(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat(this.magicType.getName());
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return pStack.getOrCreateTag().getInt("CastTime");
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        Player player = pLivingEntity instanceof Player ? (Player) pLivingEntity : null;
        SpellType<?> spellType = EntityStatusUtil.getSelectedSpell(player);
        ScaledWeapon weapon = this.getModifiedWeapon(pStack);
        if (!pLevel.isClientSide && spellType != null) {
            AbstractSpell recentSpell = EntityStatusUtil.getRecentlyActivatedSpell(player);
            if (recentSpell != null && recentSpell.isChannelling())
                return pStack;

            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
            AbstractSpell spell = spellType.createSpell();
            if (spell != null && playerPatch != null) {
                if (this.canCastSpell((ServerPlayer) player, pStack, spell)) {
                    int i = this.getWeaponLevel(pStack);
                    WeaponDamage weaponDamage = this.magicType.getWeaponDamage();
                    spell.initSpell(playerPatch, pLevel, pLivingEntity.getOnPos(), this.getModifiedWeapon(pStack), DamageUtil.calculateMagicScaling(weapon, player, i, weaponDamage), spell.getCastType() == AbstractSpell.CastType.CHARGING, this.classifications, this.spellBoost);

                    if (spell.getCastType() == AbstractSpell.CastType.CHARGING)
                        spell.setChargeAmount(1.0F);

                    if (spell.getCastType() == AbstractSpell.CastType.CHANNEL)
                        EntityStatusUtil.setChannelling((ServerPlayer) player, true);

                    player.awardStat(Stats.ITEM_USED.get(this));
                    player.awardStat(this.magicType == MagicType.SORCERY ? StatInit.SORCERIES_CAST.get() : StatInit.INCANTATIONS_CAST.get());
                }
            }
        }
        return pStack;
    }

    //TODO: ADD CHARGE TICK CHECK
    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity, int pTimeCharged) {
        Player player = pLivingEntity instanceof Player ? (Player) pLivingEntity : null;
        SpellType<?> spellType = EntityStatusUtil.getSelectedSpell(player);
        ScaledWeapon weapon = this.getModifiedWeapon(pStack);
        if (!pLevel.isClientSide && spellType != null) {
            AbstractSpell recentSpell = EntityStatusUtil.getRecentlyActivatedSpell(player);
            if (recentSpell != null && recentSpell.getCastType() == AbstractSpell.CastType.CHANNEL)
                return;

            ExtendedServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ExtendedServerPlayerPatch.class);
            AbstractSpell spell = spellType.createSpell();
            if (playerPatch != null && spell != null && spell.getCastType() == AbstractSpell.CastType.CHARGING) {
                int i = this.getUseDuration(pStack) - pTimeCharged;
                if (i < 0) return;

                if (this.canCastSpell((ServerPlayer) player, pStack, spell)) {
                    int level = this.getWeaponLevel(pStack);
                    float f = AbstractSpell.getPowerForTime(spell, i);
                    WeaponDamage weaponDamage = this.magicType.getWeaponDamage();
                    if (!((double)f < 0.1D)) {
                        spell.initSpell(playerPatch, pLevel, pLivingEntity.getOnPos(), this.getModifiedWeapon(pStack), DamageUtil.calculateMagicScaling(weapon, player, level, weaponDamage), spell.getCastType() == AbstractSpell.CastType.CHARGING && i <= spell.getChargeTick(), this.classifications, this.spellBoost);
                        spell.setChargeAmount(f);

                        player.awardStat(Stats.ITEM_USED.get(this));
                        player.awardStat(this.magicType == MagicType.SORCERY ? StatInit.SORCERIES_CAST.get() : StatInit.INCANTATIONS_CAST.get());
                    }
                }
            }
        }
    }

    public MagicType getMagicType() {
        return this.magicType;
    }

    public boolean canCastSpell(ServerPlayer player, ItemStack itemStack, AbstractSpell abstractSpell) {
        ExtendedServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ExtendedServerPlayerPatch.class);
        if (ConfigHandler.REQUIRES_BATTLE_MODE.get() && !playerPatch.isBattleMode()) {
            return false;
        } else if (this.canCastSpell(player, abstractSpell) && this.magicType == abstractSpell.getMagicType() && EntityStatusUtil.consumeFP(player, abstractSpell.getFpCost(player), abstractSpell, false) && playerPatch.consumeStamina(abstractSpell.getStaminaCost(player), false) && this.getModifiedWeapon(itemStack).getRequirements().meetsRequirements(player)) {
            EntityStatusUtil.consumeFP(player, abstractSpell.getFpCost(player), abstractSpell, true);
            playerPatch.consumeStamina(abstractSpell.getStaminaCost(player), true);
            return true;
        } else {
            return false;
        }
    }

    private boolean canCastSpell(Player player, AbstractSpell abstractSpell) {
        var requirements = abstractSpell.getRequiredStats();
        for (var statPair : requirements) {
            if (EntityStatusUtil.getEntityAttribute(player, statPair.getFirst().getAttribute()) < statPair.getSecond()) {
                return false;
            }
        }
        return true;
    }
}
