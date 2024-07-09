package com.ombremoon.enderring.compat.epicfight.util;

import com.ombremoon.enderring.compat.epicfight.api.animation.types.SpellAttackAnimation;
import com.ombremoon.enderring.compat.epicfight.api.collider.WorldPosOBBCollider;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.AABB;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class EFMUtil {

    public static ExtendedWeaponCapability getWeaponCapability(PlayerPatch<?> playerPatch) {
        CapabilityItem item = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        return item instanceof ExtendedWeaponCapability weaponCapability ? weaponCapability : null;
    }

    public static AABB getSpellColliderBB(LivingEntityPatch<?> livingEntityPatch, SpellAttackAnimation spellAttackAnimation) {
        return getSpellColliderBB(livingEntityPatch, spellAttackAnimation, 0);
    }

    public static AABB getSpellColliderBB(LivingEntityPatch<?> livingEntityPatch, SpellAttackAnimation spellAttackAnimation, int index) {
        float elapsedTime = livingEntityPatch.getAnimator().getPlayerFor(spellAttackAnimation).getElapsedTime();
        return ((WorldPosOBBCollider) spellAttackAnimation.getPhaseByTime(elapsedTime).getColliders().get(index).getSecond()).getHitboxAABB();
    }

    public static ServerPlayerPatch getServerPlayerPatch(ServerPlayer player) {
        return EpicFightCapabilities.getEntityPatch(player, ServerPlayerPatch.class);
    }
}
