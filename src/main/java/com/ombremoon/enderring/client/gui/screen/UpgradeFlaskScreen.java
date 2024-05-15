package com.ombremoon.enderring.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.world.inventory.UpgradeFlaskMenu;
import com.ombremoon.enderring.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public abstract class UpgradeFlaskScreen <T extends UpgradeFlaskMenu> extends AbstractContainerScreen<T> {
    private static final ResourceLocation UPGRADE_FLASK = CommonClass.customLocation("textures/gui/container/upgrade_flask_gui.png");

    public UpgradeFlaskScreen(T pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        double d0 = pMouseX - (double)(x + 28);
        double d1 = pMouseX - (double)(x + 101);
        double d2 = pMouseY - (double)(y + 56);
        if (d0 >= 0 && d2 >= 0 && d0 < 48 && d2 < 19 && this.menu.clickMenuButton(this.minecraft.player, 0)) {
            this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
            return true;
        }
        if (d1 >= 0 && d2 >= 0 && d1 < 48 && d2 < 19 && this.menu.clickMenuButton(this.minecraft.player, 1)) {
            this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 1);
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        ResourceLocation texture = this.getTextureLocation();
        RenderUtil.setupScreen(texture);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);

        int y1 = y + 56;
        boolean canUpgrade = this.menu.canUpgrade();
        boolean canSwitch = this.menu.canSwitch();
        if (!(canUpgrade || canSwitch)) {
            pGuiGraphics.blit(texture, x + 28, y1, 0, 187, 48, 19);
            pGuiGraphics.blit(texture, x + 101, y1, 0, 187, 48, 19);
        } else {
            int i1 = pMouseX - (x + 28);
            int i2 = pMouseX - (x + 101);
            int j = pMouseY - y1;
            if (this.menu.canSwitch()) {
                if (i1 >= 0 && j >= 0 && i1 < 48 && j < 19) {
                    pGuiGraphics.blit(texture, x + 28, y1, 0, 206, 48, 19);
                } else {
                    pGuiGraphics.blit(texture, x + 28, y1, 0, 168, 48, 19);
                }
            } else {
                pGuiGraphics.blit(texture, x + 28, y1, 0, 187, 48, 19);
            }

            if (this.menu.canUpgrade()) {
                if (i2 >= 0 && j >= 0 && i2 < 48 && j < 19) {
                    pGuiGraphics.blit(texture, x + 101, y1, 0, 206, 48, 19);
                } else {
                    pGuiGraphics.blit(texture, x + 101, y1, 0, 168, 48, 19);
                }
            } else {
                pGuiGraphics.blit(texture, x + 101, y1, 0, 187, 48, 19);
            }
        }
    }

    protected ResourceLocation getTextureLocation() {
        return UPGRADE_FLASK;
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
