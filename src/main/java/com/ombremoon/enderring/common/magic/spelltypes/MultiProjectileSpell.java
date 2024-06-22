package com.ombremoon.enderring.common.magic.spelltypes;

import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.entity.projectile.spell.SpellProjectileEntity;

public abstract class MultiProjectileSpell<S extends MultiProjectileSpell<S, T>, T extends SpellProjectileEntity<S>> extends ProjectileSpell<S, T> {
    public MultiProjectileSpell(SpellType<?> spellType, ProjectileFactory<S, T> factory, Builder<S> builder) {
        super(spellType, factory, builder);
    }
}
