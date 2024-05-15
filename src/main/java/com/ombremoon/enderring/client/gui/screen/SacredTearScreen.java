package com.ombremoon.enderring.client.gui.screen;

import com.ombremoon.enderring.common.object.world.inventory.SacredTearMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SacredTearScreen extends UpgradeFlaskScreen<SacredTearMenu> {

    public SacredTearScreen(SacredTearMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
}
