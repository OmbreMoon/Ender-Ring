package com.ombremoon.enderring.mixin;

import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.AirAttack;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

@Mixin(value = AirAttack.class, remap = false)
public class AirAttackMixin {

    @Inject(method = "executeOnServer", at = @At("TAIL"))
    private void executeOnServer(ServerPlayerPatch playerPatch, FriendlyByteBuf args, CallbackInfo info) {
        ItemStack itemStack = playerPatch.getOriginal().getItemInHand(InteractionHand.MAIN_HAND);
        CapabilityItem cap = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        float bonus = playerPatch.getOriginal().hasEffect(StatusEffectInit.CLAW_TALISMAN.get()) ? 1.15F : 1.0F;
        if (itemStack.getItem() instanceof MeleeWeapon && cap instanceof ExtendedWeaponCapability weaponCapability) {
            List<Float> motionValues = weaponCapability.getAutoMotionValues(playerPatch);
            itemStack.getOrCreateTag().putFloat("MotionValue", motionValues.get(motionValues.size() - 1) * bonus);
        }
    }
}
