package com.ombremoon.enderring.client.gui.screen;

import com.mojang.datafixers.util.Pair;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.network.ModNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class CharacterBaseScreen extends Screen {
    private static final int MAX_PAGES = 9;
    private int pageIndex;
    private int pageIndexCopy = 0;
    /** Amount scrolled in Creative mode inventory (0 = left, 1 = right) */
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
        if (pageIndex == 0) {

        }
        if (inPageRange(pageIndex, Pair.of(0, 1))) {

        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.minecraft != null) {
            if (this.minecraft.player != null) {
                this.tryRefreshInvalidatedPage();
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        this.setCurrentPage(0);
        addRenderableWidget(Button.builder(Component.literal("<"), b -> setCurrentPage(Math.max(this.pageIndex - 1, 0))).pos(this.width / 2 - 200, this.height / 2).size(20, 20).build());
        addRenderableWidget(Button.builder(Component.literal(">"), b -> setCurrentPage(Math.min(this.pageIndex + 1, MAX_PAGES))).pos(this.width / 2 + 200, this.height / 2).size(20, 20).build());
    }

    private boolean inPageRange(int pageIndex, Pair<Integer, Integer> pageRange) {
        return pageIndex >= pageRange.getFirst() && pageIndex <= pageRange.getSecond();
    }

    public void setCurrentPage(int pageIndex) {
        this.pageIndex = pageIndex;
        Constants.LOG.info(String.valueOf(this.pageIndex));
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
