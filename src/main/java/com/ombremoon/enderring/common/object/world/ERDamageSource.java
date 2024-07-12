package com.ombremoon.enderring.common.object.world;

import com.ombremoon.enderring.common.object.PhysicalDamageType;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Scalable;
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
    private boolean handAnimation = false;
    private Scalable scalable;

    public ERDamageSource(Holder<DamageType> pType, @Nullable Entity directEntity, @Nullable Entity ownerEntity) {
        super(pType, directEntity, ownerEntity);
    }

    public ERDamageSource addPhysicalDamage(PhysicalDamageType... damageTypes) {
        this.damageTypes.addAll(List.of(damageTypes));
        return this;
    }

    public ERDamageSource addScalable(Scalable scalable) {
        this.handAnimation = true;
        this.scalable = scalable;
        return this;
    }

    public Set<PhysicalDamageType> getDamageTypes() {
        return this.damageTypes;
    }

    public boolean isHandAnimation() {
        return this.handAnimation;
    }

    public Scalable getScalable() {
        return this.scalable;
    }

    public boolean isPhysicalDamage() {
        return typeHolder().is(ModDamageTypes.PHYSICAL) || typeHolder().is(ModDamageTypes.STRIKE) || typeHolder().is(ModDamageTypes.SLASH) || typeHolder().is(ModDamageTypes.PIERCE);
    }

    public boolean isERDamage() {
        return isPhysicalDamage()
                || typeHolder().is(ModDamageTypes.MAGICAL)
                || typeHolder().is(ModDamageTypes.FIRE)
                || typeHolder().is(ModDamageTypes.LIGHTNING)
                || typeHolder().is(ModDamageTypes.HOLY);
    }
}
