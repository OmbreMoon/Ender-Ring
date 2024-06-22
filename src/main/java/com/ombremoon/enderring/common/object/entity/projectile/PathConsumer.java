package com.ombremoon.enderring.common.object.entity.projectile;

import com.ombremoon.enderring.common.object.entity.projectile.spell.SpellProjectileEntity;

import java.util.function.Consumer;

@FunctionalInterface
public interface PathConsumer extends Consumer<SpellProjectileEntity<?>> {

}
