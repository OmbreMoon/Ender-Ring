package com.ombremoon.enderring.common;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Function;
import java.util.function.Supplier;

public enum WeaponScaling {
    STR(EntityAttributeInit.STRENGTH),
    DEX(EntityAttributeInit.DEXTERITY),
    INT(EntityAttributeInit.INTELLIGENCE),
    FAI(EntityAttributeInit.FAITH),
    ARC(EntityAttributeInit.ARCANE);

    private final Supplier<Attribute> attribute;

    WeaponScaling(Supplier<Attribute> attribute) {
        this.attribute = attribute;
    }

    public Attribute getAttribute() {
        return this.attribute.get();
    }
}
