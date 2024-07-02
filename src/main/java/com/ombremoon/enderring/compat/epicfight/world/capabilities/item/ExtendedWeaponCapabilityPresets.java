package com.ombremoon.enderring.compat.epicfight.world.capabilities.item;

import com.ombremoon.enderring.compat.epicfight.gameassets.SkillInit;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

public class ExtendedWeaponCapabilityPresets {
    public static final Function<Item, CapabilityItem.Builder> STRAIGHT_SWORD = (item) ->
        straightSwordBuilder();
    public static final Function<Item, CapabilityItem.Builder> GREATSWORD = (item) ->
            greatSwordBuilder();

    protected static ExtendedWeaponCapability.Builder greatSwordBuilder() {
        return ExtendedWeaponCapability.builder()
                .category(ExtendedWeaponCategories.GREAT_SWORD)
                .styleProvider(playerPatch -> playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.GREATSWORD ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND)
                .collider(ColliderPreset.GREATSWORD)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH)
                .motionValues(CapabilityItem.Styles.ONE_HAND, 1.00F, 1.01F, 1.02F, 1.10F, 1.05F, 1.07F)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH)
                .motionValues(CapabilityItem.Styles.TWO_HAND, 1.03F, 1.04F, 1.05F, 1.15F, 1.10F, 1.10F)
                .newHeavyCombo(CapabilityItem.Styles.ONE_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH)
                .heavyMotionValues(CapabilityItem.Styles.ONE_HAND, 1.25F, 1.25F, 1.20F, 1.30F)
                .newHeavyCombo(CapabilityItem.Styles.TWO_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH)
                .heavyMotionValues(CapabilityItem.Styles.TWO_HAND, 1.30F, 1.30F, 1.25F, 1.35F)
                .defaultAshOfWar(SkillInit.SWEEPING_EDGE_NEW) //Temp
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_GREATSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_GREATSWORD)
                .weaponCombinationPredicator(entityPatch -> {
                    WeaponCategory category = EpicFightCapabilities.getItemStackCapability(entityPatch.getOriginal().getOffhandItem()).getWeaponCategory();
                    return category == ExtendedWeaponCategories.GREAT_SWORD || category == CapabilityItem.WeaponCategories.GREATSWORD;
                })
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .hitParticle(EpicFightParticles.HIT_BLADE.get());
    }

    protected static ExtendedWeaponCapability.Builder straightSwordBuilder() {
        return ExtendedWeaponCapability.builder()
                .category(ExtendedWeaponCategories.STRAIGHT_SWORD)
                .styleProvider(playerPatch -> playerPatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD ? CapabilityItem.Styles.TWO_HAND : CapabilityItem.Styles.ONE_HAND)
                .collider(ColliderPreset.SWORD)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
                .motionValues(CapabilityItem.Styles.ONE_HAND, 1.0F, 1.01F, 1.02F, 1.05F, 1.07F)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
                .newHeavyCombo(CapabilityItem.Styles.ONE_HAND, Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
                .newStyleCombo(CapabilityItem.Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                .defaultAshOfWar(SkillInit.SWEEPING_EDGE_NEW)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_DUAL_WEAPON)
                .weaponCombinationPredicator(entityPatch -> {
                    WeaponCategory category = EpicFightCapabilities.getItemStackCapability(entityPatch.getOriginal().getOffhandItem()).getWeaponCategory();
                    return category == ExtendedWeaponCategories.STRAIGHT_SWORD || category == CapabilityItem.WeaponCategories.SWORD;
                })
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .hitParticle(EpicFightParticles.HIT_BLADE.get());
    }
}
