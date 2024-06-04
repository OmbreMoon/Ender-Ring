package com.ombremoon.enderring.client.gui.screen;

import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;

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
                ModNetworking.passTime();
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
            InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, i / 2 + 95, j / 2 + 35, 50, (float)(i / 2 + 95) - mouseX, (float)(j / 2 + 35 - 50) - mouseY, this.minecraft.player);
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
            InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics, i / 2 + 95, j / 2 + 35, 50, (float)(i / 2 + 51) - mouseX, (float)(j / 2 + 75 - 50) - mouseY, this.minecraft.player);
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
            ModNetworking.openGraceSiteMenu(menuFlag);
        }).width(98).pos(screen.width / 2 - 187, screen.height / 2 - yOffset).build();
    }
}
