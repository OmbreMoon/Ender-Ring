package com.ombremoon.enderring.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.object.world.inventory.QuickAccessMenu;
import com.ombremoon.enderring.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class QuickAccessScreen extends AbstractContainerScreen<QuickAccessMenu> {
    private static final ResourceLocation QUICK_ACCESS = CommonClass.customLocation("textures/gui/container/quick_access_gui.png");

    public QuickAccessScreen(QuickAccessMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderUtil.setupScreen(QUICK_ACCESS);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(QUICK_ACCESS, x, y, 0, 0, imageWidth, imageHeight);

        int j = this.menu.getTalismanPouches();
        int k = 3 - j;
        for (int i = 0; i < k; i++) {
            pGuiGraphics.blit(QUICK_ACCESS, x + 107 - 18 * i, y + 18, 0, 168, 16, 16);
        }
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
