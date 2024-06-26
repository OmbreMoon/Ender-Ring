package com.ombremoon.enderring.mixin;

import com.ombremoon.enderring.common.object.world.effect.StatusEffect;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EffectRenderingInventoryScreen.class)
public class InventoryEffectRenderMixin {

    @Inject(method = "getEffectName", at = @At("RETURN"), cancellable = true)
    public void getEffectName(MobEffectInstance instance, CallbackInfoReturnable<Component> callback) {
        MobEffect effect = instance.getEffect();
        int amp = instance.getAmplifier();

        if (effect instanceof StatusEffect statusEffect) {
            if (statusEffect.translationKeys != null && statusEffect.translationKeys.get(amp) != null) {
                callback.setReturnValue(Component
                        .translatable(statusEffect.translationKeys.get(amp)));
            } else if (amp > 0) {
                MutableComponent comp = effect.getDisplayName().copy();
                comp = comp.append(CommonComponents.SPACE).append("+" + amp);
                callback.setReturnValue(comp);
            }
        } else callback.cancel();
    }
}
