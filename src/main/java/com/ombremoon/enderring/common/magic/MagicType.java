package com.ombremoon.enderring.common.magic;

import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

public enum MagicType {
    SORCERY("Sorcery", EntityAttributeInit.SORCERY_SCALING, WeaponDamage.MAGICAL),
    INCANTATION("Incantation", EntityAttributeInit.INCANT_SCALING, WeaponDamage.HOLY);

    private final String name;
    private final Supplier<Attribute> attribute;
    private final WeaponDamage weaponDamage;

    MagicType(String name, Supplier<Attribute> attribute, WeaponDamage weaponDamage) {
        this.name = name;
        this.attribute = attribute;
        this.weaponDamage = weaponDamage;
    }

    public String getName() {
        return this.name;
    }

    public Attribute getAttribute() {
        return this.attribute.get();
    }

    public WeaponDamage getWeaponDamage() {
        return this.weaponDamage;
    }
}
