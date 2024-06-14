package com.ombremoon.enderring.mixin;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.item.equipment.weapon.melee.MeleeWeapon;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.skill.BasicAttack;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.SkillConsumeEvent;

import java.util.List;

@Mixin(BasicAttack.class)
public class BasicAttackMixin {

    @Inject(method = "executeOnServer", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isSprinting()Z"), to = @At(value = "INVOKE", target = "Lyesman/epicfight/skill/BasicAttack;setComboCounterWithEvent(Lyesman/epicfight/world/entity/eventlistener/ComboCounterHandleEvent$Causal;Lyesman/epicfight/world/capabilities/entitypatch/player/ServerPlayerPatch;Lyesman/epicfight/skill/SkillContainer;Lyesman/epicfight/api/animation/types/StaticAnimation;I)V")), locals = LocalCapture.CAPTURE_FAILHARD)
    private void executeOnServer(ServerPlayerPatch playerPatch, FriendlyByteBuf args, CallbackInfo info, SkillConsumeEvent event, CapabilityItem cap, StaticAnimation attackMotion, ServerPlayer player, SkillContainer skillContainer, SkillDataManager dataManager, int comboCounter) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem() instanceof MeleeWeapon && cap instanceof ExtendedWeaponCapability weaponCapability) {
            List<Float> motionValues = weaponCapability.getAutoMotionValues(playerPatch);
            itemStack.getOrCreateTag().putFloat("MotionValue", motionValues.get(comboCounter));
        }
    }
}
