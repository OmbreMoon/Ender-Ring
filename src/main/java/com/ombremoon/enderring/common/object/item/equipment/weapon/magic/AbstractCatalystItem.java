package com.ombremoon.enderring.common.object.item.equipment.weapon.magic;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.chat.Component;
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

//TODO: Add spells cast stat

public class AbstractCatalystItem extends MeleeWeapon {
    private final MagicType magicType;

    public AbstractCatalystItem(MagicType magicType, Properties properties) {
        super(-2.3F, properties);
        this.magicType = magicType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        SpellType<?> spell = PlayerStatusUtil.getSelectedSpell(pPlayer);
        itemStack.getTag().putInt("WeaponLevel", 10);
        pPlayer.sendSystemMessage(Component.literal(String.valueOf(itemStack.getOrCreateTag().getInt("WeaponLevel"))));
        if (pUsedHand == InteractionHand.MAIN_HAND && spell != null && spell.getSpell().getMagicType() == this.magicType) {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (stack.getTag() != null) {
            SpellType<?> spellType = PlayerStatusUtil.getSelectedSpell(player);
            stack.getTag().putString("Spell", spellType != null ? spellType.getResourceLocation().toString() : "Empty");
            return;
        }

        Constants.LOG.warn("Shouldn't be running every tick!");
        stack.getOrCreateTag();
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        SpellType<?> spellType = PlayerStatusUtil.getSpellByName(PlayerStatusUtil.getSpellId(pStack.getTag(), "Spell"));
        return spellType != null ? spellType.getSpell().getCastTime() : 1;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        Player player = pLivingEntity instanceof Player ? (Player) pLivingEntity : null;
        SpellType<?> spellType = PlayerStatusUtil.getSelectedSpell(player);
        ScaledWeapon weapon = this.getWeapon();
        if (!pLevel.isClientSide && spellType != null) {
            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
            AbstractSpell spell = spellType.getSpell();
            if (playerPatch != null && PlayerStatusUtil.canCastSpell(player, spell, weapon)) {
                spell.activateSpellEffect(new SpellInstance(spellType, this.getModifiedWeapon(pStack)), playerPatch, pLevel, pLivingEntity.getOnPos());
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild && spellType != null && PlayerStatusUtil.canCastSpell(player, spellType.getSpell(), weapon)) {
                PlayerStatusUtil.decreaseSpellFP(player, spellType);
            }
        }

        if (player != null && spellType != null) {
            AbstractSpell spell = spellType.getSpell();
            ItemCooldowns cooldowns = player.getCooldowns();
            if (!cooldowns.isOnCooldown(this) && PlayerStatusUtil.canCastSpell(player, spell, weapon)) {
                cooldowns.addCooldown(this, spell.isInstantSpell() ? 10 : spell.getCastTime());
            }
        }
        return pStack;
    }

    public MagicType getMagicType() {
        return this.magicType;
    }
}
