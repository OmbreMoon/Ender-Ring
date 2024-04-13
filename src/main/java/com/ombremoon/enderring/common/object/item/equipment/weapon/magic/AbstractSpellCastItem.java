package com.ombremoon.enderring.common.object.item.equipment.weapon.magic;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

//TODO: Add spells cast stat

public class AbstractSpellCastItem extends AbstractWeapon {
    private final MagicType magicType;

    public AbstractSpellCastItem(MagicType magicType, Properties properties) {
        super(properties);
        this.magicType = magicType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (pUsedHand == InteractionHand.MAIN_HAND && PlayerStatusUtil.getSelectedSpell(pPlayer) != null) {
            return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (stack.getTag() != null) {
            stack.getTag().putString("Spell", PlayerStatusUtil.getSelectedSpell(player) != null ? PlayerStatusUtil.getSelectedSpell(player).getResourceLocation().toString() : "Empty");
            return;
        }

        Constants.LOG.info("Shouldn't be running every tick!");
        stack.getOrCreateTag();
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 1;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        Player player = pLivingEntity instanceof Player ? (Player) pLivingEntity : null;
        SpellType<?> spellType = PlayerStatusUtil.getSelectedSpell(player);
        if (!pLevel.isClientSide && spellType != null) {
            AbstractSpell spell = spellType.getSpell();
            if (spell.getMagicType() == this.magicType && PlayerStatusUtil.canCastSpell(player, spell)) {
                spell.activateSpellEffect(new SpellInstance(spellType), player, pLevel, pLivingEntity.getOnPos());
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild && spellType != null && PlayerStatusUtil.canCastSpell(player, spellType.getSpell())) {
                PlayerStatusUtil.decreaseSpellFP(player, spellType);
            }
        }

        if (player != null && spellType != null) {
            AbstractSpell spell = spellType.getSpell();
            ItemCooldowns cooldowns = player.getCooldowns();
            if (!cooldowns.isOnCooldown(this) && PlayerStatusUtil.canCastSpell(player, spell)) {
                cooldowns.addCooldown(this, spell.isInstantSpell() ? 10 : spell.getCastTime());
            }
        }
        return pStack;
    }

    public MagicType getMagicType() {
        return this.magicType;
    }
}
