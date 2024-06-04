package com.ombremoon.enderring.common.object.world.particle;

import com.ombremoon.enderring.common.init.ParticleInit;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;

public class ExampleEffects {
    public static void fireBlastSpell(Level level, Vec3 pos) {
        WorldParticleBuilder.create(ParticleInit.FIRE_RING)
                .setScaleData(GenericParticleData.create(1.0F, 0.3F).setCoefficient(1.3F).build())
                .setTransparencyData(GenericParticleData.create(0.20F, 0.15F).build())
                .setLifetime(8)
                .enableNoClip()
                .spawn(level, pos.x, pos.y, pos.z);
    }
}
