package com.ombremoon.enderring.client;

import com.ombremoon.enderring.util.math.NoiseGenerator;
import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CameraEngine {

    public static void handleScreenShake(Camera camera) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int time = camera.getEntity().tickCount;
        Vec3 vec3 = camera.getPosition();
        Method method = Camera.class.getDeclaredMethod("setPosition", double.class, double.class, double.class);
        method.setAccessible(true);
        float intensity = 1.0F;
//        method.invoke(camera, vec3.x + getNoise(1, 0.25F, intensity, time * 3), vec3.y + getNoise(2, 0.25F, intensity, time * 3), vec3.z + getNoise(3, 0.25F, intensity, time * 3));
    }

    private static double getNoise(int seed, float maxOffset, float intensity, int x) {
        NoiseGenerator noiseGenerator = new NoiseGenerator(seed);
        return maxOffset * intensity * noiseGenerator.noise(x);
    }
//
//    public static boolean hasScreenShake()
}
