package com.ombremoon.enderring.common.magic;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class SpellType<T extends AbstractSpell> {
    private final ResourceLocation resourceLocation;
    private final Supplier<T> supplier;

    public SpellType(ResourceLocation resourceLocation, Supplier<T> supplier) {
        this.resourceLocation = resourceLocation;
        this.supplier = supplier;
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public T getSpell() {
        return this.supplier.get();
    }
}
