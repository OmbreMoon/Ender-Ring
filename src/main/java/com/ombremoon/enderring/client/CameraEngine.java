package com.ombremoon.enderring.client;

import com.ombremoon.enderring.Constants;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.Random;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class CameraEngine {
    private static final Object2ObjectOpenHashMap<UUID, CameraEngine> ENGINE = new Object2ObjectOpenHashMap<>();
    private static final float MAX_OFFSET = 0.25F;
    private static final float MAX_INTENSITY = 1.0F;
    private static final Logger LOGGER = Constants.LOG;
    private final Player player;
    private int seed;
    private int shakeDuration;
    private float shakeIntensity;
    private int shakeFrequency;
    private float maxOffset;

    public CameraEngine(Player player) {
        this.player = player;
    }

    public static CameraEngine getOrAssignEngine(Player player) {
        if (!ENGINE.containsKey(player.getUUID())) {
            ENGINE.put(player.getUUID(), new CameraEngine(player));
        }
        return ENGINE.get(player.getUUID());
    }

    public int getSeed() {
        return this.seed;
    }

    public int getShakeDuration() {
        return this.shakeDuration;
    }

    public float getShakeIntensity() {
        return this.shakeIntensity;
    }

    public int getShakeFrequency() {
        return this.shakeFrequency;
    }

    public float getMaxOffset() {
        return this.maxOffset;
    }

    public boolean shouldShakeCamera() {
        return this.shakeDuration > 0;
    }

    private void tickDuration() {
        this.shakeDuration--;
        if (shakeDuration <= 0) {
            this.shakeDuration = 0;
        }
    }

    public void shakeScreen() {
        shakeScreen(player.getRandom().nextInt());
    }

    public void shakeScreen(int seed) {
        shakeScreen(seed, 10);
    }

    public void shakeScreen(int seed, int duration) {
        shakeScreen(seed, duration, MAX_INTENSITY);
    }

    public void shakeScreen(int seed, int duration, float intensity) {
        shakeScreen(seed, duration, intensity, 10);
    }

    public void shakeScreen(int seed, int duration, float intensity, int freq) {
        shakeScreen(seed, duration, intensity, freq, MAX_OFFSET);
    }

    public void shakeScreen(int seed, int duration, float intensity, int freq, float maxOffset) {
        this.seed = seed;
        this.shakeDuration = duration;
        this.shakeIntensity = Mth.clamp(intensity, 0.0F, MAX_INTENSITY);
        this.shakeFrequency = freq;
        this.maxOffset = Mth.clamp(maxOffset, 0, MAX_OFFSET);
    }

    @SubscribeEvent
    public static void onScreenShake(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase != TickEvent.Phase.END) return;
        if (!player.level().isClientSide) return;

        CameraEngine cameraEngine = CameraEngine.getOrAssignEngine(player);
        if (cameraEngine.shouldShakeCamera()) {
            cameraEngine.tickDuration();
        }
    }
}
