package com.ombremoon.enderring.mixin;

import com.ombremoon.enderring.Constants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.events.engine.ControllEngine;

@Mixin(value = ControllEngine.class, remap = false)
public class ControllEngineMixin {

    @Inject(at = @At("HEAD"), method = "tck")
    private void tick() {
        Constants.LOG.info("Please Work");
    }
}
