package com.ombremoon.enderring.common.object.item.equipment.weapon.magic;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import com.ombremoon.enderring.common.init.StatInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class AbstractCatalystItem extends MeleeWeapon {
    private final MagicType magicType;

    public AbstractCatalystItem(MagicType magicType, Properties properties) {
        super(-2.3F, properties);
        this.magicType = magicType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        SpellType<?> spell = EntityStatusUtil.getSelectedSpell(pPlayer);
        if (pUsedHand == InteractionHand.MAIN_HAND && spell != null && spell.createSpell().getMagicType() == this.magicType) {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (stack.getTag() != null) {
            if (EntityStatusProvider.isPresent(player)) {
                SpellType<?> spellType = EntityStatusUtil.getSelectedSpell(player);
                stack.getTag().putString("Spell", spellType != null ? spellType.getResourceLocation().toString() : "Empty");
                return;
            }
        }

        Constants.LOG.warn("Shouldn't be running every tick!");
        stack.getOrCreateTag();
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        SpellType<?> spellType = EntityStatusUtil.getSpellByName(EntityStatusUtil.getSpellId(pStack.getTag(), "Spell"));
        return spellType != null ? spellType.createSpell().getCastTime() : 1;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        Player player = pLivingEntity instanceof Player ? (Player) pLivingEntity : null;
        SpellType<?> spellType = EntityStatusUtil.getSelectedSpell(player);
        ScaledWeapon weapon = this.getModifiedWeapon(pStack);
        if (!pLevel.isClientSide && spellType != null) {
            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
            AbstractSpell spell = spellType.createSpell();
            if (spell != null) {
                if (playerPatch != null && this.canCastSpell(player, pStack, spell, true)) {
                    int i = this.getWeaponLevel(pStack);
                    WeaponDamage weaponDamage = this.magicType == MagicType.SORCERY ? WeaponDamage.MAGICAL : WeaponDamage.HOLY;
                    spell.initSpell(playerPatch, pLevel, pLivingEntity.getOnPos(), pStack, this.getModifiedWeapon(pStack), DamageUtil.calculateMagicScaling(weapon, player, i, weaponDamage));

                    player.awardStat(Stats.ITEM_USED.get(this));
                    player.awardStat(this.magicType == MagicType.SORCERY ? StatInit.SORCERIES_CAST.get() : StatInit.INCANTATIONS_CAST.get());
                }

                if (player != null) {
                    ItemCooldowns cooldowns = player.getCooldowns();
                    if (!cooldowns.isOnCooldown(this) && this.canCastSpell(player, pStack, spell, false)) {
                        cooldowns.addCooldown(this, spell.isInstantSpell() ? 10 : spell.getCastTime());
                    }
                }
            }
        }
        return pStack;
    }

    public MagicType getMagicType() {
        return this.magicType;
    }

    public boolean canCastSpell(Player player, ItemStack itemStack, AbstractSpell abstractSpell, boolean forceConsume) {
        return this.canCastSpell(player, abstractSpell) && EntityStatusUtil.consumeFP(player, abstractSpell.getFpCost(), forceConsume) && this.getModifiedWeapon(itemStack).getRequirements().meetsRequirements(player);
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
