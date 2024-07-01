package com.ombremoon.enderring.event.custom;

import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.AnimatedSpell;
import com.ombremoon.enderring.common.magic.spelltypes.ChanneledSpell;
import com.ombremoon.enderring.common.magic.spelltypes.ProjectileSpell;
import net.minecraftforge.eventbus.api.Event;

public class BuildSpellEvent extends Event {
    private final SpellType<?> spellType;
    private final AbstractSpell.Builder<?> builder;

    public BuildSpellEvent(SpellType<?> spellType, AbstractSpell.Builder<? extends AbstractSpell> builder) {
        this.spellType = spellType;
        this.builder = builder;
    }

    public SpellType<?> getSpellType() {
        return this.spellType;
    }

    public AbstractSpell.Builder<?> getBuilder() {
        return this.builder;
    }

    public static class Animated extends BuildSpellEvent {
        private final SpellType<?> spellType;
        private final AnimatedSpell.Builder<?> builder;

        public Animated(SpellType<?> spellType, AnimatedSpell.Builder<?> builder) {
            super(spellType, builder);
            this.spellType = spellType;
            this.builder = builder;
        }

        public SpellType<?> getSpellType() {
            return this.spellType;
        }

        public AnimatedSpell.Builder<?> getBuilder() {
            return this.builder;
        }
    }

    public static class Channeled extends BuildSpellEvent {
        private final SpellType<?> spellType;
        private final ChanneledSpell.Builder<ChanneledSpell> builder;

        public Channeled(SpellType<?> spellType, ChanneledSpell.Builder<ChanneledSpell> builder) {
            super(spellType, builder);
            this.spellType = spellType;
            this.builder = builder;
        }

        public SpellType<?> getSpellType() {
            return this.spellType;
        }

        public ChanneledSpell.Builder<ChanneledSpell> getBuilder() {
            return this.builder;
        }
    }

    public static class Projectile extends BuildSpellEvent {
        private final SpellType<?> spellType;
        private final ProjectileSpell.Builder<? extends ProjectileSpell<?, ?>> builder;

        public Projectile(SpellType<?> spellType, ProjectileSpell.Builder<? extends ProjectileSpell<?, ?>> builder) {
            super(spellType, builder);
            this.spellType = spellType;
            this.builder = builder;
        }

        public SpellType<?> getSpellType() {
            return this.spellType;
        }

        public ProjectileSpell.Builder<? extends ProjectileSpell<?, ?>> getBuilder() {
            return this.builder;
        }
    }
}
