package com.ombremoon.enderring.mixin;

import com.ombremoon.enderring.util.math.NoiseGenerator;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
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
        int time = camera.getEntity().tickCount;
        Vec3 vec3 = camera.getPosition();
        float intensity = 0F;
//        this.setPosition(vec3.x + getNoise(1, 0.25F, intensity, time * 10), vec3.y + getNoise(2, 0.25F, intensity, time * 10), vec3.z + getNoise(3, 0.25F, intensity, time * 10));
    }

    private static double getNoise(int seed, float maxOffset, float intensity, int x) {
        NoiseGenerator noiseGenerator = new NoiseGenerator(seed);
        return maxOffset * intensity * noiseGenerator.noise(x);
    }

//
//    public static boolean hasScreenShake()

    @Unique
    public Camera self() {
        return (Camera) (Object) this;
    }
}
