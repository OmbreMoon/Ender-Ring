package com.ombremoon.enderring.common.object.item.equipment.weapon.melee;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.compat.epicfight.gameassets.SkillInit;
import com.ombremoon.enderring.compat.epicfight.util.EFMCapabilityUtil;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.Arrays;

public class MeleeWeapon extends AbstractWeapon {
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;

    public MeleeWeapon(float attackSpeed, Properties pProperties) {
        super(pProperties);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", attackSpeed, AttributeModifier.Operation.ADDITION));
        this.defaultModifiers = builder.build();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            ServerPlayerPatch serverPlayerPatch = EpicFightCapabilities.getEntityPatch(pPlayer, ServerPlayerPatch.class);
            ExtendedWeaponCapability weaponCapability = EFMCapabilityUtil.getWeaponCapability(serverPlayerPatch);
            itemStack.getOrCreateTag().putInt("WeaponLevel", 10);
            Constants.LOG.info(Arrays.toString(this.getModifiedWeapon(itemStack).getDamage().getPhysDamageTypes()));
            if (weaponCapability != null) {
                Constants.LOG.info(String.valueOf(weaponCapability.getAshOfWar(itemStack)));
                weaponCapability.swapAshOfWar(itemStack, weaponCapability.getAshOfWar(itemStack) == SkillInit.SWEEPING_EDGE_NEW ? SkillInit.GUILLOTINE_AXE_NEW : SkillInit.SWEEPING_EDGE_NEW);
            }

        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        float motionValue = pStack.getTag().getFloat("MotionValue");
        DamageUtil.conditionalHurt(pStack, this, this.getModifiedWeapon(pStack), pAttacker, pTarget, motionValue);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if (!level.isClientSide) {
            if (stack.getTag() != null) {
                for (WeaponDamage weaponDamage : WeaponDamage.values()) {
                    stack.getTag().putInt(weaponDamage.getCompoundName(), Mth.floor(weaponDamage.getDamageFunction().apply(this.getModifiedWeapon(stack), player, this.getWeaponLevel(stack))));
                }
                return;
            }

            Constants.LOG.warn("Shouldn't be running every tick!");
            stack.getOrCreateTag();
        }
    }

    public float getPhysDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("PhysicalDamage");
    }

    public float getMagicDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("MagicDamage");
    }

    public float getFireDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("FireDamage");
    }

    public float getLightDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("LightningDamage");
    }

    public float getHolyDamage(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getFloat("HolyDamage");
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getAttributeModifiers(slot, stack);
    }
}
