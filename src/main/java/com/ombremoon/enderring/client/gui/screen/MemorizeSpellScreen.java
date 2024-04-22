package com.ombremoon.enderring.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.object.world.inventory.MemorizeSpellMenu;
import com.ombremoon.enderring.common.object.world.inventory.WondrousPhysickMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MemorizeSpellScreen extends AbstractContainerScreen<MemorizeSpellMenu> {
    private static final ResourceLocation TEXTURE = CommonClass.customLocation("textures/gui/container/memorize_spell_gui.png");

    public MemorizeSpellScreen(MemorizeSpellMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, TEXTURE);


    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
