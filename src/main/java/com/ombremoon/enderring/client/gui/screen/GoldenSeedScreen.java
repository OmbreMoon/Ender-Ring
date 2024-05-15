package com.ombremoon.enderring.client.gui.screen;

import com.ombremoon.enderring.common.object.world.inventory.GoldenSeedMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GoldenSeedScreen extends UpgradeFlaskScreen<GoldenSeedMenu> {

    public GoldenSeedScreen(GoldenSeedMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
}
