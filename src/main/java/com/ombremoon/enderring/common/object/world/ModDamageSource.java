package com.ombremoon.enderring.common.object.world;

import com.ombremoon.enderring.common.object.PhysicalDamageType;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

import java.util.Set;

public class ModDamageSource extends DamageSource {
    private final Set<PhysicalDamageType> damageTypes;

    public ModDamageSource(Holder<DamageType> pType) {
        this(pType, (PhysicalDamageType) null);
    }

    public ModDamageSource(Holder<DamageType> pType, PhysicalDamageType... damageTypes) {
        super(pType);
        this.damageTypes = Set.of(damageTypes);
    }

    public Set<PhysicalDamageType> getDamageTypes() {
        return this.damageTypes;
    }

    public boolean isPhysicalDamage() {
        return typeHolder().is(ModDamageTypes.PHYSICAL) || typeHolder().is(ModDamageTypes.STRIKE) || typeHolder().is(ModDamageTypes.SLASH) || typeHolder().is(ModDamageTypes.PIERCE);
    }
}
