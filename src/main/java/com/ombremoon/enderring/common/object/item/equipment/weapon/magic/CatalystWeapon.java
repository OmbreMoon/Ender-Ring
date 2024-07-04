package com.ombremoon.enderring.common.object.item.equipment.weapon.magic;

import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import com.ombremoon.enderring.common.init.StatInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.Classification;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import com.ombremoon.enderring.compat.epicfight.util.EFMUtil;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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
        if (pUsedHand == InteractionHand.MAIN_HAND && spell != null && spell.createSpell().getMagicType() == this.magicType) {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (stack.getTag() != null) {
            float scaling = DamageUtil.calculateMagicScaling(this.getModifiedWeapon(stack), player, this.getWeaponLevel(stack), this.magicType.getWeaponDamage());
            stack.getTag().putInt(this.magicType.getName(), Mth.floor(scaling));
            if (EntityStatusProvider.isPresent(player)) {
                SpellType<?> spellType = EntityStatusUtil.getSelectedSpell(player);
                stack.getTag().putString("Spell", spellType != null ? spellType.getResourceLocation().toString() : "Empty");
            }
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
        SpellType<?> spellType = EntityStatusUtil.getSpellByName(EntityStatusUtil.getSpellId(pStack.getTag(), "Spell"));
        return spellType != null ? spellType.createSpell().getCastTime() : 1;
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
        if (!pLevel.isClientSide) {
            if (pRemainingUseDuration == this.getUseDuration(pStack)) {
                LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(pLivingEntity, LivingEntityPatch.class);
                livingEntityPatch.playAnimationSynchronized(livingEntityPatch.getHitAnimation(StunType.SHORT), 0.0F);
            }
        }
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
                if (this.canCastSpell(player, pStack, spell, true)) {
                    int i = this.getWeaponLevel(pStack);
                    WeaponDamage weaponDamage = this.magicType.getWeaponDamage();
                    spell.initSpell(playerPatch, pLevel, pLivingEntity.getOnPos(), this.getModifiedWeapon(pStack), DamageUtil.calculateMagicScaling(weapon, player, i, weaponDamage), spell.canCharge(), this.classifications, this.spellBoost);

                    if (spell.canCharge())
                        spell.setChargeAmount(1.0F);

                    if (spell.requiresConcentration())
                        EntityStatusUtil.setChannelling((ServerPlayer) player, true);

                    player.awardStat(Stats.ITEM_USED.get(this));
                    player.awardStat(this.magicType == MagicType.SORCERY ? StatInit.SORCERIES_CAST.get() : StatInit.INCANTATIONS_CAST.get());

                    ItemCooldowns cooldowns = player.getCooldowns();
                    if (!cooldowns.isOnCooldown(this) && this.canCastSpell(player, pStack, spell, false)) {
                        cooldowns.addCooldown(this, spell.isInstantSpell() ? 10 : spell.getCastTime());
                    }
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
//            if (recentSpell != null && recentSpell.wasCharged())
            if (recentSpell != null && recentSpell.requiresConcentration())
                return;

            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
            AbstractSpell spell = spellType.createSpell();
            if (playerPatch != null && spell != null && spell.canCharge()) {
                int i = this.getUseDuration(pStack) - pTimeCharged;
                if (i < 0) return;

                if (this.canCastSpell(player, pStack, spell, true)) {
                    int level = this.getWeaponLevel(pStack);
                    float f = AbstractSpell.getPowerForTime(spell, i);
                    WeaponDamage weaponDamage = this.magicType.getWeaponDamage();
                    if (!((double)f < 0.1D)) {
                        spell.initSpell(playerPatch, pLevel, pLivingEntity.getOnPos(), this.getModifiedWeapon(pStack), DamageUtil.calculateMagicScaling(weapon, player, level, weaponDamage), false, this.classifications, this.spellBoost);
                        spell.setChargeAmount(f);

                        player.awardStat(Stats.ITEM_USED.get(this));
                        player.awardStat(this.magicType == MagicType.SORCERY ? StatInit.SORCERIES_CAST.get() : StatInit.INCANTATIONS_CAST.get());

                        ItemCooldowns cooldowns = player.getCooldowns();
                        if (!cooldowns.isOnCooldown(this) && this.canCastSpell(player, pStack, spell, false)) {
                            cooldowns.addCooldown(this, spell.isInstantSpell() ? 10 : spell.getCastTime());
                        }
                    }
                }
            }
        }
    }

    public MagicType getMagicType() {
        return this.magicType;
    }

    public boolean canCastSpell(Player player, ItemStack itemStack, AbstractSpell abstractSpell, boolean forceConsume) {
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (ConfigHandler.REQUIRES_BATTLE_MODE.get() && !playerPatch.isBattleMode()) {
            return false;
        }
        return this.canCastSpell(player, abstractSpell) && EntityStatusUtil.consumeFP(player, abstractSpell.getFpCost(player), abstractSpell, forceConsume) && playerPatch.consumeStamina(abstractSpell.getStaminaCost(player)) && this.getModifiedWeapon(itemStack).getRequirements().meetsRequirements(player);
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
