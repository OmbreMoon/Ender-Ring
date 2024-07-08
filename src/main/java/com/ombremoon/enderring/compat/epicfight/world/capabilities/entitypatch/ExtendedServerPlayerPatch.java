package com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch;

import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class ExtendedServerPlayerPatch extends ServerPlayerPatch {

    public boolean consumeStamina(float amount, boolean forceConsume) {
        float currentStamina = this.getStamina();
        if (this.original.getAbilities().instabuild) {
            return true;
        } else if (currentStamina < amount) {
            return false;
        } else {
            if (forceConsume) {
                this.setStamina(currentStamina - amount);
                this.resetActionTick();
            }
            return true;
        }
    }
}
