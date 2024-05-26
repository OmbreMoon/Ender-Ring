package com.ombremoon.enderring.mixin;

import org.spongepowered.asm.mixin.Mixin;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

@Mixin(value = WeaponCapability.class, remap = false)
public class WeaponCapabilityMixin {

/*    @Inject(method = "getInnateSkill", at = @At("HEAD"), cancellable = true)
    private void getInnateSkill(PlayerPatch<?> playerPatch, ItemStack itemStack, CallbackInfoReturnable<Skill> cir) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        if (nbt.contains("AshOfWar", 8)) {
            Skill skill = SkillManager.getSkill(nbt.getString("AshOfWar"));
            if (skill != null) {
                cir.setReturnValue(skill);
            }
        }
    }*/
}
