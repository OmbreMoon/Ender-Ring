package com.ombremoon.enderring.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.object.world.inventory.WondrousPhysickMenu;
import com.ombremoon.enderring.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

//TODO: ADD BACK BUTTON
//TODO: ADD TEXT
//TODO: ADD INFO WIDGET

public class WondrousPhysickScreen extends AbstractContainerScreen<WondrousPhysickMenu> {
    private static final ResourceLocation WONDROUS_PHYSICK = CommonClass.customLocation("textures/gui/container/wondrous_physick_gui.png");

    public WondrousPhysickScreen(WondrousPhysickMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        double d0 = pMouseX - (double)(x + 118);
        double d1 = pMouseY - (double)(y + 36);
        double d2 = pMouseY - (double)(y + 59);
        if (d0 >= 0 && d1 >= 0 && d0 < 48 && d1 < 19 && this.menu.clickMenuButton(this.minecraft.player, 0)) {
            this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
            return true;
        }
        if (d0 >= 0 && d2 >= 0 && d0 < 48 && d2 < 19 && this.menu.clickMenuButton(this.minecraft.player, 1)) {
            this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 1);
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderUtil.setupScreen(WONDROUS_PHYSICK);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(WONDROUS_PHYSICK, x, y, 0, 0, imageWidth, imageHeight);

        int x1 = x + 118;
        boolean flag = this.menu.canExtract() || this.menu.canInsert();
        if (!flag) {
            pGuiGraphics.blit(WONDROUS_PHYSICK, x1, y + 36, 0, 187, 48, 19);
            pGuiGraphics.blit(WONDROUS_PHYSICK, x1, y + 59, 0, 187, 48, 19);
        } else {
            int i = pMouseX - x1;
            int j1 = pMouseY - (y + 36);
            int j2 = pMouseY - (y + 59);
            if (this.menu.canInsert()) {
                if (i >= 0 && j1 >= 0 && i < 48 && j1 < 19) {
                    pGuiGraphics.blit(WONDROUS_PHYSICK, x1, y + 36, 0, 206, 48, 19);
                } else {
                    pGuiGraphics.blit(WONDROUS_PHYSICK, x1, y + 36, 0, 168, 48, 19);
                }
            } else {
                pGuiGraphics.blit(WONDROUS_PHYSICK, x1, y + 36, 0, 187, 48, 19);
            }

            if (this.menu.canExtract()) {
                if (i >= 0 && j2 >= 0 && i < 48 && j2 < 19) {
                    pGuiGraphics.blit(WONDROUS_PHYSICK, x1, y + 59, 0, 206, 48, 19);
                } else {
                    pGuiGraphics.blit(WONDROUS_PHYSICK, x1, y + 59, 0, 168, 48, 19);
                }
            } else {
                pGuiGraphics.blit(WONDROUS_PHYSICK, x1, y + 59, 0, 187, 48, 19);
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
