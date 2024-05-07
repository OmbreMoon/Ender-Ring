package com.ombremoon.enderring.client.gui.screen;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nullable;
import java.util.function.Supplier;

//TODO: TEST ON SERVER

public class GraceSiteScreen {
    private static final Component FLASK = Component.translatable("menu.enderring.flask_screen");
    private static final Component LEVEL_UP = Component.translatable("menu.enderring.level_up_screen");

    public static class Base extends Screen {
        public Base(Component pTitle) {
            super(pTitle);
        }

        @Override
        protected void init() {
            super.init();

            this.generateButtons();
        }

        private void generateButtons() {
            this.addRenderableWidget(Button.builder(Component.translatable("menu.enderring.pass_time"), pButton -> {
                ModNetworking.getInstance().passTime();
                this.minecraft.setScreen((Screen) null);
                this.minecraft.mouseHandler.grabMouse();
            }).pos(this.width / 2 - 187, this.height / 2 - 59).width(98).build());
            this.addRenderableWidget(this.openScreenButton(Component.translatable("menu.enderring.level_up"), () -> new GraceSiteScreen.LevelUp(LEVEL_UP), 34));
            this.addRenderableWidget(this.openScreenButton(Component.translatable("menu.enderring.flasks"), () -> new GraceSiteScreen.Flask(FLASK), 9));
            this.addRenderableWidget(openMenuButton(Component.translatable("menu.enderring.memorize_spell"), 1,-16, this));
            this.addRenderableWidget(openMenuButton(Component.translatable("menu.enderring.mix_physick"), 0,-41, this));
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            this.renderBackground(pGuiGraphics);
            this.renderBg(pGuiGraphics, pMouseX, pMouseY);
            super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }


        protected void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY) {
            int i = this.width;
            int j = this.height;
//            guiGraphics.blit();
            renderPlayerFollowsMouse(guiGraphics, i / 2 + 95, j / 2 + 35, 50, (float)(i / 2 + 51) - mouseX, (float)(j / 2 + 75 - 50) - mouseY, this.minecraft.player);
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }

        private Button openScreenButton(Component pMessage, Supplier<Screen> pScreenSupplier, int yOffset) {
            return Button.builder(pMessage, (p_280817_) -> {
                this.minecraft.setScreen(pScreenSupplier.get());
            }).width(98).pos(this.width / 2 - 187, this.height / 2 - yOffset).build();
        }

        /*private Button openMenuButton(Component pMessage, boolean flag, int menuFlag, int yOffset) {
            Player player = Minecraft.getInstance().player;
            return Button.builder(pMessage, (p_280817_) -> {
                PlayerStatusUtil.setGraceSiteFlag(player, flag);
                ModNetworking.getInstance().openGraceSiteMenu(flag, menuFlag);
            }).width(98).pos(this.width / 2 - 187, this.height / 2 - yOffset).build();
        }*/
    }

    private static class Flask extends Screen {
        public Flask(Component pTitle) {
            super(pTitle);
        }

        @Override
        protected void init() {
            super.init();

            this.generateButtons();
        }

        private void generateButtons() {
            this.addRenderableWidget(openMenuButton(Component.translatable("menu.enderring.golden_seed"), 2, 34, this));
            this.addRenderableWidget(openMenuButton(Component.translatable("menu.enderring.sacred_tear"), 3, 9, this));
            this.addRenderableWidget(openMenuButton(Component.translatable("menu.enderring.allocate_charges"), 4,-16, this));
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            this.renderBackground(pGuiGraphics);
            this.renderBg(pGuiGraphics, pMouseX, pMouseY);
            super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }


        protected void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY) {
            int i = this.width;
            int j = this.height;
//            guiGraphics.blit();
            renderPlayerFollowsMouse(guiGraphics, i / 2 + 95, j / 2 + 35, 50, (float)(i / 2 + 51) - mouseX, (float)(j / 2 + 75 - 50) - mouseY, this.minecraft.player);
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }
    }

    private static class LevelUp extends Screen {
        public LevelUp(Component pTitle) {
            super(pTitle);
        }
    }

    private static class PassTime extends Screen {
        public PassTime(Component pTitle) {
            super(pTitle);
        }

        @Override
        protected void init() {
            super.init();
            this.addRenderableWidget(Button.builder(Component.translatable("menu.enderring.morning"), pButton -> {

            }).width(98).pos(this.width / 2 - 187, this.height / 2 - 34).build());
            this.addRenderableWidget(Button.builder(Component.translatable("menu.enderring.noon"), pButton -> {

            }).width(98).pos(this.width / 2 - 187, this.height / 2 - 9).build());
            this.addRenderableWidget(Button.builder(Component.translatable("menu.enderring.evening"), pButton -> {

            }).width(98).pos(this.width / 2 - 187, this.height / 2 + 16).build());
        }
    }

    private static Button openMenuButton(Component pMessage, int menuFlag, int yOffset, Screen screen) {
        return Button.builder(pMessage, (p_280817_) -> {
            ModNetworking.getInstance().openGraceSiteMenu(menuFlag);
        }).width(98).pos(screen.width / 2 - 187, screen.height / 2 - yOffset).build();
    }

    private static void renderPlayerFollowsMouse(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, float p_275604_, float p_275546_, LivingEntity pEntity) {
        float f = (float)Math.atan((double)(p_275604_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_275546_ / 40.0F));
        renderPlayerFollowsAngle(pGuiGraphics, pX, pY, pScale, f, f1, pEntity);
    }

    private static void renderPlayerFollowsAngle(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, float angleXComponent, float angleYComponent, LivingEntity pEntity) {
        float f = angleXComponent;
        float f1 = angleYComponent;
        Quaternionf quaternionf = (new Quaternionf()).rotateZ((float)Math.PI);
        Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f1 * 20.0F * ((float)Math.PI / 180F));
        quaternionf.mul(quaternionf1);
        float f2 = pEntity.yBodyRot;
        float f3 = pEntity.getYRot();
        float f4 = pEntity.getXRot();
        float f5 = pEntity.yHeadRotO;
        float f6 = pEntity.yHeadRot;
        pEntity.yBodyRot = 180.0F + f * 20.0F;
        pEntity.setYRot(180.0F + f * 40.0F);
        pEntity.setXRot(-f1 * 20.0F);
        pEntity.yHeadRot = pEntity.getYRot();
        pEntity.yHeadRotO = pEntity.getYRot();
        renderPlayer(pGuiGraphics, pX, pY, pScale, quaternionf, quaternionf1, pEntity);
        pEntity.yBodyRot = f2;
        pEntity.setYRot(f3);
        pEntity.setXRot(f4);
        pEntity.yHeadRotO = f5;
        pEntity.yHeadRot = f6;
    }

    private static void renderPlayer(GuiGraphics pGuiGraphics, int pX, int pY, int pScale, Quaternionf p_281880_, @Nullable Quaternionf pCameraOrientation, LivingEntity pEntity) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((double)pX, (double)pY, 50.0D);
        pGuiGraphics.pose().mulPoseMatrix((new Matrix4f()).scaling((float)pScale, (float)pScale, (float)(-pScale)));
        pGuiGraphics.pose().mulPose(p_281880_);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (pCameraOrientation != null) {
            pCameraOrientation.conjugate();
            entityrenderdispatcher.overrideCameraOrientation(pCameraOrientation);
        }

        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(pEntity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, pGuiGraphics.pose(), pGuiGraphics.bufferSource(), 15728880);
        });
        pGuiGraphics.flush();
        entityrenderdispatcher.setRenderShadow(true);
        pGuiGraphics.pose().popPose();
        Lighting.setupFor3DItems();
    }
}
