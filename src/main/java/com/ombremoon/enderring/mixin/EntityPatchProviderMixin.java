package com.ombremoon.enderring.mixin;

import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.ExtendedServerPlayerPatch;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.provider.EntityPatchProvider;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@Mixin(value = EntityPatchProvider.class, remap = false)
public class EntityPatchProviderMixin {

    @Shadow @Final private static Map<EntityType<?>, Function<Entity, Supplier<EntityPatch<?>>>> CAPABILITIES;

    /**
     * @author
     * OmbreMoon
     *
     * @reason
     * Added functionality to ServerPlayerPatch; Nothing is removed, just extended in the lambda
     */
    @Overwrite
    public static void registerEntityPatchesClient() {
        CAPABILITIES.put(EntityType.PLAYER, (entity) -> {
            if (entity instanceof LocalPlayer) {
                return LocalPlayerPatch::new;
            } else if (entity instanceof RemotePlayer) {
                return AbstractClientPlayerPatch<RemotePlayer>::new;
            } else if (entity instanceof ServerPlayer) {
                return ExtendedServerPlayerPatch::new;
            } else {
                return () -> null;
            }
        });
    }
}
