package com.ombremoon.enderring.compat.epicfight.world.capabilities.item;

import yesman.epicfight.world.capabilities.item.Style;

public enum ExtendedStyles implements Style {
    BOTH_HANDS(false);

    final boolean canUseOffhand;
    final int id;

    private ExtendedStyles(boolean canUseOffhand) {
        this.id = Style.ENUM_MANAGER.assign(this);
        this.canUseOffhand = this.canUseOffhand();
    }

    @Override
    public boolean canUseOffhand() {
        return this.canUseOffhand;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
