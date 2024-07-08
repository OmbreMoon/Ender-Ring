package com.ombremoon.enderring.mixin;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.CameraEngine;
import com.ombremoon.enderring.util.math.NoiseGenerator;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow protected abstract void setPosition(double pX, double pY, double pZ);

    @Inject(method = "setup", at = @At("TAIL"))
    private void setup(BlockGetter pLevel, Entity pEntity, boolean pDetached, boolean pThirdPersonReverse, float pPartialTick, CallbackInfo info) {
        handleScreenShake(self());
    }

    @Unique
    private void handleScreenShake(Camera camera) {
        CameraEngine cameraEngine = CameraEngine.getOrAssignEngine((Player) camera.getEntity());
        Vec3 vec3 = camera.getPosition();
        int time = camera.getEntity().tickCount;
        if (cameraEngine != null && cameraEngine.shouldShakeCamera()) {
            int seed = cameraEngine.getSeed();
            float intensity = cameraEngine.getShakeIntensity();
            float offset = cameraEngine.getMaxOffset();
            int freq = cameraEngine.getShakeFrequency();
            double d0 = getNoise(seed, offset, intensity, time * freq);
            double d1 = getNoise(seed + 1, offset, intensity, time * freq);
            double d2 = getNoise(seed + 2, offset, intensity, time * freq);
            this.setPosition(vec3.x + d0, vec3.y + d1, vec3.z + d2);
        }
    }

    private static double getNoise(int seed, float maxOffset, float intensity, int x) {
        NoiseGenerator noiseGenerator = new NoiseGenerator(seed);
        return maxOffset * intensity * noiseGenerator.noise(x);
    }

    @Unique
    public Camera self() {
        return (Camera) (Object) this;
    }
}
