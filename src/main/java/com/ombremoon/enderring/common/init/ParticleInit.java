package com.ombremoon.enderring.common.init;

import com.ombremoon.enderring.Constants;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleInit {
    public static DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Constants.MOD_ID);

/*    public static RegistryObject<LodestoneParticleType> FIRE_RING = registerLodestoneParticle("fire_ring", LodestoneParticleType::new);

    protected static RegistryObject<LodestoneParticleType> registerLodestoneParticle(String name, Supplier<LodestoneParticleType> particleType) {
        return PARTICLE_TYPES.register(name, particleType);
    }

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FIRE_RING.get(), LodestoneParticleType.Factory::new);
    }*/

    public static void register(IEventBus modEventBus) {
        PARTICLE_TYPES.register(modEventBus);
    }
}
