package com.ombremoon.enderring.compat.epicfight.util;

import com.ombremoon.enderring.compat.epicfight.api.animation.types.SpellAnimation;
import com.ombremoon.enderring.compat.epicfight.api.collider.WorldPosOBBCollider;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class EFMUtil {

    public static ExtendedWeaponCapability getWeaponCapability(PlayerPatch<?> playerPatch) {
        CapabilityItem item = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        return item instanceof ExtendedWeaponCapability weaponCapability ? weaponCapability : null;
    }

    public static AABB getSpellColliderBB(LivingEntityPatch<?> livingEntityPatch, SpellAnimation spellAnimation) {
        return getSpellColliderBB(livingEntityPatch, spellAnimation, 0);
    }

    public static AABB getSpellColliderBB(LivingEntityPatch<?> livingEntityPatch, SpellAnimation spellAnimation, int index) {
        float elapsedTime = livingEntityPatch.getAnimator().getPlayerFor(spellAnimation).getElapsedTime();
        return ((WorldPosOBBCollider)spellAnimation.getPhaseByTime(elapsedTime).getColliders().get(index).getSecond()).getHitboxAABB();
    }
}
