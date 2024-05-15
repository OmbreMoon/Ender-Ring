package com.ombremoon.enderring.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.capability.PlayerStatusProvider;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import com.ombremoon.enderring.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class QuickAccessOverlay {
    private static final ResourceLocation QUICK_ACCESS = CommonClass.customLocation("textures/gui/overlay/quick_access_slot.png");

    public static final IGuiOverlay QUICK_ACCESS_OVERLAY = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, QUICK_ACCESS);
        int x = screenWidth / 2;
        int y = screenHeight / 2;
        guiGraphics.blit(QUICK_ACCESS, x - 200, y, 0, 0, 24, 24, 128, 128);

        renderQuickAccessItem(guiGraphics, gui, x, y);
    });

    private static void renderQuickAccessItem(GuiGraphics guiGraphics, ForgeGui gui, int x, int y) {
        Player player = gui.getMinecraft().player;
        if (player!= null && PlayerStatusProvider.isPresent(player)) {
            ItemStack itemStack = PlayerStatusUtil.getQuickAccessItem(player);
            if (!itemStack.isEmpty()) {
                RenderUtil.renderItem(gui.getMinecraft(), guiGraphics, itemStack, x - 196, y + 4, 16.0F);
            }
        }
    }
}
