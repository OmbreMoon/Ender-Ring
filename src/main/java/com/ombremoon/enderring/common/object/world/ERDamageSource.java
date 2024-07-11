package com.ombremoon.enderring.common.object.world;

import com.ombremoon.enderring.common.object.PhysicalDamageType;
import net.minecraft.core.Holder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ERDamageSource extends DamageSource {
    private final Set<PhysicalDamageType> damageTypes = new HashSet<>();

    public ERDamageSource(Holder<DamageType> pType, @Nullable Entity directEntity, @Nullable Entity ownerEntity) {
        super(pType, directEntity, ownerEntity);
    }

    public ERDamageSource addPhysicalDamage(PhysicalDamageType... damageTypes) {
        this.damageTypes.addAll(List.of(damageTypes));
        return this;
    }

    public Set<PhysicalDamageType> getDamageTypes() {
        return this.damageTypes;
    }

    public boolean isPhysicalDamage() {
        return typeHolder().is(ModDamageTypes.PHYSICAL) || typeHolder().is(ModDamageTypes.STRIKE) || typeHolder().is(ModDamageTypes.SLASH) || typeHolder().is(ModDamageTypes.PIERCE);
    }
}
