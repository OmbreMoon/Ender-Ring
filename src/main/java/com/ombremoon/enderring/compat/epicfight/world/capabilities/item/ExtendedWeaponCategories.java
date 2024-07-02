package com.ombremoon.enderring.compat.epicfight.world.capabilities.item;

import yesman.epicfight.world.capabilities.item.WeaponCategory;

public enum ExtendedWeaponCategories implements WeaponCategory {
    STRAIGHT_SWORD;

    final int id;

    private ExtendedWeaponCategories() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
