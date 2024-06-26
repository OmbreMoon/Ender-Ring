package com.ombremoon.enderring.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nullable;

public class RenderUtil {

    public static void setupScreen(ResourceLocation resourceLocation) {
        setupScreen(resourceLocation, 1.0F);
    }

    public static void setupScreen(ResourceLocation resourceLocation, float alpha) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.setShaderTexture(0, resourceLocation);
    }

    public static void renderItem(Minecraft minecraft, GuiGraphics guiGraphics, ItemStack pStack, int pX, int pY, float scale) {
        renderItem(minecraft, guiGraphics, minecraft.player, minecraft.level, pStack, pX, pY, scale, 0, 0);
    }

    private static void renderItem(Minecraft minecraft, GuiGraphics guiGraphics, @org.jetbrains.annotations.Nullable LivingEntity pEntity, @org.jetbrains.annotations.Nullable Level pLevel, ItemStack pStack, int pX, int pY, float scale, int pSeed, int p_281995_) {
        if (!pStack.isEmpty()) {
            BakedModel bakedmodel = minecraft.getItemRenderer().getModel(pStack, pLevel, pEntity, pSeed);
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((float) (pX + 8), (float) (pY + 8), (float) (150 + (bakedmodel.isGui3d() ? p_281995_ : 0)));

            try {
                guiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                guiGraphics.pose().scale(scale, scale, scale);
                boolean flag = !bakedmodel.usesBlockLight();
                if (flag) {
                    Lighting.setupForFlatItems();
                }

                minecraft.getItemRenderer().render(pStack, ItemDisplayContext.GUI, false, guiGraphics.pose(), guiGraphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
                guiGraphics.flush();
                if (flag) {
                    Lighting.setupFor3DItems();
                }
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.forThrowable(throwable, "Rendering item");
                CrashReportCategory crashreportcategory = crashreport.addCategory("Item being rendered");
                crashreportcategory.setDetail("Item Type", () -> {
                    return String.valueOf((Object) pStack.getItem());
                });
                crashreportcategory.setDetail("Registry Name", () -> String.valueOf(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(pStack.getItem())));
                crashreportcategory.setDetail("Item Damage", () -> {
                    return String.valueOf(pStack.getDamageValue());
                });
                crashreportcategory.setDetail("Item NBT", () -> {
                    return String.valueOf((Object) pStack.getTag());
                });
                crashreportcategory.setDetail("Item Foil", () -> {
                    return String.valueOf(pStack.hasFoil());
                });
                throw new ReportedException(crashreport);
            }

            guiGraphics.pose().popPose();
        }
    }
}
