package com.ombremoon.enderring.event.custom;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.AnimatedSpell;
import com.ombremoon.enderring.common.magic.spelltypes.ChanneledSpell;
import com.ombremoon.enderring.common.magic.spelltypes.ProjectileSpell;
import net.minecraftforge.common.MinecraftForge;

public class EventFactory {

    public static AbstractSpell.Builder<?> getBuilder(SpellType<?> spellType, AbstractSpell.Builder<?> builder) {
        BuildSpellEvent event = new BuildSpellEvent(spellType, builder);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return new AbstractSpell.Builder<>();
        }
        return event.getBuilder();
    }

    public static AnimatedSpell.Builder<?> getAnimatedBuilder(SpellType<?> spellType, AnimatedSpell.Builder<?> builder) {
        BuildSpellEvent.Animated event = new BuildSpellEvent.Animated(spellType, builder);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return new AnimatedSpell.Builder<>();
        }
        return event.getBuilder();
    }

    public static ChanneledSpell.Builder<ChanneledSpell> getChanneledBuilder(SpellType<?> spellType, ChanneledSpell.Builder<ChanneledSpell> builder) {
        BuildSpellEvent.Channeled event = new BuildSpellEvent.Channeled(spellType, builder);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return new ChanneledSpell.Builder<>();
        }
        return event.getBuilder();
    }

    public static ProjectileSpell.Builder<? extends ProjectileSpell<?, ?>> getProjectileBuilder(SpellType<?> spellType, ProjectileSpell.Builder<? extends ProjectileSpell<?, ?>> builder) {
        BuildSpellEvent.Projectile event = new BuildSpellEvent.Projectile(spellType, builder);
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return new ProjectileSpell.Builder<>();
        }
        return event.getBuilder();
    }
}
