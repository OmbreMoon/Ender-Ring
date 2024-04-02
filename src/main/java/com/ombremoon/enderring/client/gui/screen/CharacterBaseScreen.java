package com.ombremoon.enderring.client.gui.screen;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CharacterBaseScreen extends Screen {
    private static final int MAX_PAGES = 7;
    private static final int IMAGE_WIDTH = 119;
    private static final int IMAGE_HEIGHT = 238;
    private int pageIndex;
    private int pageIndexCopy = 0;
    /** Amount scrolled in Character Base Selection screen (0 = left, 1 = right) */
    private float scrollOffset;
    /** True if the scrollbar is being dragged */
    private boolean scrolling;

    public CharacterBaseScreen(Component pTitle) {
        super(pTitle);
    }

    private void tryRefreshInvalidatedPage() {
        if (this.pageIndexCopy != this.pageIndex) {
            this.pageIndexCopy = this.pageIndex;
            this.refreshCurrentPageContents(this.pageIndex);
        }
    }

    private void refreshCurrentPageContents(int pageIndex) {
        this.clearWidgets();
        this.generateButtons(pageIndex);
    }

    @Override
    protected void init() {
        super.init();
        this.setCurrentPage(0);

        this.generateButtons(this.pageIndex);
    }

    private void generateButtons(int pageIndex) {
        this.addRenderableWidget(Button.builder(Component.literal("<"), b -> setCurrentPage(Math.max(this.pageIndex - 1, 0))).pos(this.width / 2 - 201, this.height / 2 - 9).size(20, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal(">"), b -> setCurrentPage(Math.min(this.pageIndex + 1, MAX_PAGES))).pos(this.width / 2 + 180, this.height / 2 - 9).size(20, 20).build());

        for (int i = 0; i < 3; i ++) {
            int index = i;
            String resourceLocation = Base.values()[pageIndex + index].getResourceLocation();
            this.addRenderableWidget(Button.builder(Component.literal(resourceLocation), b -> {
                ModNetworking.getInstance().setBaseStats(Base.values()[pageIndex + index]);
                this.minecraft.setScreen((Screen) null);
            }).pos(this.width / 2 + (i - 1) * 119 - 8, this.height / 2 - 9).size(20, 20).build());
        }
    }

    public void setCurrentPage(int pageIndex) {
        this.pageIndex = pageIndex;
        this.tryRefreshInvalidatedPage();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public enum Base {
        VAGABOND("Vagabond", 9, 15, 10, 11 , 14, 13, 9, 9, 7),
        WARRIOR("Warrior", 8, 11, 12, 11, 10, 16, 10, 8, 9),
        HERO("Hero", 7, 14, 9, 12, 16, 9, 7, 8, 11),
        BANDIT("Bandit", 5, 10, 11, 10, 9, 13, 9, 8, 14),
        ASTROLOGER("Astrologer", 6, 9, 15, 9, 8, 12, 16, 7, 9),
        PROPHET("Prophet", 7, 10, 14, 8, 11, 10, 7, 16, 10),
        SAMURAI("Samurai", 9, 12, 11, 13, 12, 15, 9, 8, 8),
        PRISONER("Prisoner", 9, 11, 12, 11, 11, 14, 14, 6, 9),
        CONFESSOR("Confessor", 10, 10, 13, 10 ,12, 12, 9, 14, 9),
        WRETCH("Wretch", 1, 10, 10, 10, 10, 10, 10, 10, 10);

        private final String resourceLocation;
        private final int level;
        private final int vigor;
        private final int mind;
        private final int endurance;
        private final int strength;
        private final int dexterity;
        private final int intelligence;
        private final int faith;
        private final int arcane;

        Base(String resourceLocation, int level, int vigor, int mind, int endurance, int strength, int dexterity, int intelligence, int faith, int arcane) {
            this.resourceLocation = resourceLocation;
            this.level = level;
            this.vigor = vigor;
            this.mind = mind;
            this.endurance = endurance;
            this.strength = strength;
            this.dexterity = dexterity;
            this.intelligence = intelligence;
            this.faith = faith;
            this.arcane = arcane;
        }

        public String getResourceLocation() {
            return this.resourceLocation;
        }

        public int getLevel() {
            return this.level;
        }

        public int getVigor() {
            return this.vigor;
        }

        public int getMind() {
            return this.mind;
        }

        public int getEndurance() {
            return this.endurance;
        }

        public int getStrength() {
            return this.strength;
        }

        public int getDexterity() {
            return this.dexterity;
        }

        public int getIntelligence() {
            return this.intelligence;
        }

        public int getFaith() {
            return this.faith;
        }

        public int getArcane() {
            return this.arcane;
        }
    }
}
