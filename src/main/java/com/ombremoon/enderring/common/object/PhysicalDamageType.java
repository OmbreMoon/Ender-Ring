package com.ombremoon.enderring.common.object;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

public enum PhysicalDamageType {
    STANDARD("standard", EntityAttributeInit.PHYS_NEGATE),
    STRIKE("strike", EntityAttributeInit.STRIKE_NEGATE),
    SLASH("slash", EntityAttributeInit.SLASH_NEGATE),
    PIERCE("pierce", EntityAttributeInit.PIERCE_NEGATE);

    private final String name;
    private final Supplier<Attribute> attribute;

    PhysicalDamageType(String name, Supplier<Attribute> attribute) {
        this.name = name;
        this.attribute = attribute;
    }

    public static PhysicalDamageType getTypeById(int ordinal) {
        return PhysicalDamageType.values()[ordinal];
    }

    public String getName() {
        return this.name;
    }

    public Attribute getAttribute() {
        return this.attribute.get();
    }
}
