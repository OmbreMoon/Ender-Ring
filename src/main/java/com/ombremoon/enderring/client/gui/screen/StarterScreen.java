package com.ombremoon.enderring.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.item.ModArmorMaterial;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.event.FirstSpawnEvent;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

//TODO: ADD SCROLL FUNCTIONALITY
//TODO: ADD KEEPSAKE DESCRIPTIONS
//TODO: ADD ORIGIN RENDERS

public class StarterScreen extends Screen {
    private static final Component KEEPSAKE = Component.translatable(Constants.MOD_ID + ".menu.keepsake");
    private static final Component CONFIRMATION = Component.translatable(Constants.MOD_ID + ".menu.starter_confirm");
    private static final Component CONFIRM = Component.translatable(Constants.MOD_ID + ".menu.confirm");
    private static final int imageHeight = 166;

    protected StarterScreen(Component pTitle) {
        super(pTitle);
    }

    public static class CharacterBaseScreen extends StarterScreen {
        private static final int MAX_PAGES = 7;
        private static final int IMAGE_WIDTH = 119;
        private static final int IMAGE_HEIGHT = 238;
        private int pageIndex;
        private int pageIndexCopy = 0;
        /**
         * Amount scrolled in Character Base Selection screen (0 = left, 1 = right)
         */
        private float scrollOffset;
        /**
         * True if the scrollbar is being dragged
         */
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

            for (int i = 0; i < 3; i++) {
                int index = i;
                Base characterBase = Base.values()[pageIndex + index];
                String name = characterBase.getName();
                this.addRenderableWidget(Button.builder(Component.translatable(Constants.MOD_ID + ".origin." + name), b -> {
                    ModNetworking.setPlayerOrigin(characterBase, Keepsake.NONE, false);
                    this.minecraft.setScreen(new KeepsakeScreen(KEEPSAKE, characterBase));
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
    }

    static class KeepsakeScreen extends StarterScreen {
        private static final ResourceLocation KEEPSAKE_TEXTURE = CommonClass.customLocation("textures/gui/keepsake_gui.png");
        private final Base characterBase;
        private int imageWidth = 256;
        private int leftPos;
        private int topPos;

        public KeepsakeScreen(Component pTitle, Base characterBase) {
            super(pTitle);
            this.characterBase = characterBase;
        }

        @Override
        protected void init() {
            this.leftPos = (this.width - this.imageWidth) / 2;
            this.topPos = (this.height - imageHeight) / 2;

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 5; j++) {
                    Keepsake keepsake = Keepsake.values()[j + i * 5];
                    Item item = keepsake.getItem();
                    this.addRenderableWidget(Button.builder(item != null ? item.getName(new ItemStack(item)) : Component.literal(""), pButton -> {
                        this.minecraft.setScreen(new ConfirmationScreen(CONFIRMATION, this.characterBase, keepsake));
                    }).pos(this.leftPos + 5 + (83 * i), this.topPos + 7 + (j * 32)).width(80).build());
                }
            }
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            int i = this.leftPos;
            int j = this.topPos;
            this.renderBackground(pGuiGraphics);
            this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
            RenderSystem.disableDepthTest();
            super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate((float)i, (float)j, 0.0F);
            pGuiGraphics.pose().popPose();
            RenderSystem.enableDepthTest();
        }

        private void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
            RenderUtil.setupScreen(KEEPSAKE_TEXTURE);
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;
            pGuiGraphics.blit(KEEPSAKE_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 5; j++) {
                    int x1 = pMouseX - (x + 5 + (83 * i));
                    int y1 = pMouseY - (y + 7 + (32 * j));
                    if (x1 >= 0 && y1 >= 0 && x1 < 80 && y1 < 20) {
                        Keepsake keepsake = Keepsake.values()[j + i * 5];
                        RenderUtil.renderItem(this.minecraft, pGuiGraphics, keepsake.getItem() != null ? new ItemStack(keepsake.getItem()) : ItemStack.EMPTY, x + 204, y + 35, 25.0F);
                        pGuiGraphics.drawWordWrap(this.font, Component.literal(keepsake.getDescriptionId() != null ? keepsake.getDescriptionId() : ""), x + 178, y + 85, 70, 0);
                    }
                }
            }
        }
    }

    static class ConfirmationScreen extends StarterScreen {
        private static final ResourceLocation CONFIRM_TEXTURE = CommonClass.customLocation("textures/gui/confirmation_gui.png");
        private Base characterBase;
        private Keepsake keepsake;
        private int leftPos;
        private int topPos;
        private int imageWidth = 176;

        public ConfirmationScreen(Component pTitle, Base characterBase, Keepsake keepsake) {
            super(pTitle);
            this.characterBase = characterBase;
            this.keepsake = keepsake;
        }

        @Override
        protected void init() {
            this.leftPos = (this.width - this.imageWidth) / 2;
            this.topPos = (this.height - imageHeight) / 2;

            this.addRenderableWidget(Button.builder(FirstSpawnEvent.CHARACTER_ORIGIN, pButton -> {
                //TODO: CLEAR EQUIP SLOTS
                this.minecraft.setScreen(new CharacterBaseScreen(KEEPSAKE));
            }).pos(this.leftPos + 81, this.topPos + 5).size(86, 18).build());
            this.addRenderableWidget(Button.builder(KEEPSAKE, pButton -> {
                this.minecraft.setScreen(new KeepsakeScreen(KEEPSAKE, this.characterBase));
            }).pos(this.leftPos + 81, this.topPos + 27).width(61).build());
            this.addRenderableWidget(Button.builder(CONFIRM, pButton -> {
                ModNetworking.setPlayerOrigin(this.characterBase, this.keepsake, true);
                this.minecraft.setScreen(null);
            }).pos(this.leftPos + 7, this.topPos + 141).width(72).build());
        }

        @Override
        public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            int i = this.leftPos;
            int j = this.topPos;
            this.renderBackground(pGuiGraphics);
            this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
            RenderSystem.disableDepthTest();
            super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

            int x1 = i + 150;
            int y1 = j + 29;
            Keepsake chosenKeepsake = this.keepsake;
            if (chosenKeepsake != null && chosenKeepsake.item.get() != null) {
                ItemStack itemStack = new ItemStack(chosenKeepsake.item.get());
                pGuiGraphics.renderItem(itemStack, x1, y1);
                if (this.isHovering(150, 29, 16, 16, pMouseX, pMouseY)) {
                    this.renderTooltip(pGuiGraphics, itemStack, pMouseX, pMouseY);
                }
            }


            for (int k = 0; k < characterBase.getStarterItems().size(); k++) {
                Item item = characterBase.getStarterItems().get(k).get();
                if (item != null) {
                    ItemStack itemStack = new ItemStack(item);
                    if (this.isHovering(82 + (k * 18), 58, 16, 16, pMouseX, pMouseY)) {
                        this.renderTooltip(pGuiGraphics, itemStack, pMouseX, pMouseY);
                    }
                }
            }

            List<ItemStack> stackList = StreamSupport.stream(this.minecraft.player.getArmorSlots().spliterator(), false).filter(itemStack -> !itemStack.isEmpty()).collect(Collectors.toList());
            for (int l = 0; l < stackList.size(); l++) {
                pGuiGraphics.renderItem(stackList.get(l), i + 82 + (l * 18), j + 77);
                if (this.isHovering(82 + (l * 18), 77, 16, 16, pMouseX, pMouseY)) {
                    this.renderTooltip(pGuiGraphics, stackList.get(l), pMouseX, pMouseY);
                }
            }
        }

        private void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
            RenderUtil.setupScreen(CONFIRM_TEXTURE);
            int textColor = 16777215;
            int x = (width - imageWidth) / 2;
            int y = (height - imageHeight) / 2;
            pGuiGraphics.blit(CONFIRM_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
            pGuiGraphics.drawCenteredString(this.font, Component.translatable(Constants.MOD_ID + ".origin." + this.characterBase.name), x + 43, y + 10, textColor);
            InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics, x + 43, y + 135, 50, (float)(x + 43) - pMouseX, (float)(y + 135 - 50) - pMouseY, this.minecraft.player);

            for (int i = 0; i < characterBase.starterItems.size(); i++) {
                int x1 = x + 81 + (i * 18);
                int y1 = y + 58;
                pGuiGraphics.blit(CONFIRM_TEXTURE, x1, y1, 149, 28, 18, 18);
                ItemStack itemStack = new ItemStack(characterBase.starterItems.get(i).get());
                pGuiGraphics.renderItem(itemStack, x + 82 + (i * 18), y + 59);
            }

            int x1 = x + 84;
            int x2 = x + 130;
            pGuiGraphics.drawString(this.font, Component.literal("VIG: " + this.characterBase.vigor), x1, y + 115, textColor);
            pGuiGraphics.drawString(this.font, Component.literal("MND: " + this.characterBase.mind), x1, y + 126, textColor);
            pGuiGraphics.drawString(this.font, Component.literal("END: " + this.characterBase.endurance), x1, y + 137, textColor);
            pGuiGraphics.drawString(this.font, Component.literal("STR: " + this.characterBase.strength), x1, y + 148, textColor);
            pGuiGraphics.drawString(this.font, Component.literal("DEX: " + this.characterBase.dexterity), x2, y + 115, textColor);
            pGuiGraphics.drawString(this.font, Component.literal("INT: " + this.characterBase.intelligence), x2, y + 126, textColor);
            pGuiGraphics.drawString(this.font, Component.literal("FAI: " + this.characterBase.faith), x2, y + 137, textColor);
            pGuiGraphics.drawString(this.font, Component.literal("ARC: " + this.characterBase.arcane), x2, y + 148, textColor);
            pGuiGraphics.drawString(this.font, Component.literal("LVL: " + this.characterBase.level), x + 107, y + 104, textColor);
        }

        protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
            int i = this.leftPos;
            int j = this.topPos;
            double d0 = pMouseX - (double)(i + pX);
            double d1 = pMouseY - (double)(j + pY);
            return d0 >= 0 && d1 >= 0 && d0 < pWidth && d1 < pHeight;
        }

        protected void renderTooltip(GuiGraphics pGuiGraphics, ItemStack itemStack, int pX, int pY) {
                pGuiGraphics.renderTooltip(this.font, this.getTooltipFromContainerItem(itemStack), itemStack.getTooltipImage(), itemStack, pX, pY);
        }

        protected List<Component> getTooltipFromContainerItem(ItemStack pStack) {
            return getTooltipFromItem(this.minecraft, pStack);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public enum Base {
        VAGABOND("vagabond", 9, 15, 10, 11, 14, 13, 9, 9, 7, List.of(EquipmentInit.LONGSWORD, EquipmentInit.HALBERD, EquipmentInit.HEATER_SHIELD), null),
        WARRIOR("warrior", 8, 11, 12, 11, 10, 16, 10, 8, 9, List.of(EquipmentInit.SCIMITAR, EquipmentInit.SCIMITAR, EquipmentInit.RIVETED_WOODEN_SHIELD), ModArmorMaterial.BLUE_CLOTH),
        HERO("hero", 7, 14, 9, 12, 16, 9, 7, 8, 11, List.of(EquipmentInit.BATTLE_AXE, EquipmentInit.LARGE_LEATHER_SHIELD), null),
        BANDIT("bandit", 5, 10, 11, 10, 9, 13, 9, 8, 14, List.of(EquipmentInit.GREAT_KNIFE, EquipmentInit.SHORTBOW, EquipmentInit.BUCKLER), null),
        ASTROLOGER("astrologer", 6, 9, 15, 9, 8, 12, 16, 7, 9, List.of(EquipmentInit.SHORT_SWORD, EquipmentInit.ASTROLOGER_STAFF, EquipmentInit.SCRIPTURE_WOODEN_SHIELD), null),
        PROPHET("prophet", 7, 10, 14, 8, 11, 10, 7, 16, 10, List.of(EquipmentInit.SHORT_SPEAR, EquipmentInit.FINGER_SEAL, EquipmentInit.RICKETY_SHIELD), null),
        SAMURAI("samurai", 9, 12, 11, 13, 12, 15, 9, 8, 8, List.of(EquipmentInit.UCHIGATANA, EquipmentInit.LONGBOW, EquipmentInit.RED_THORN_ROUNDSHIELD), null),
        PRISONER("prisoner", 9, 11, 12, 11, 11, 14, 14, 6, 9, List.of(EquipmentInit.GLINTSTONE_STAFF, EquipmentInit.ESTOC, EquipmentInit.RIFT_SHIELD), null),
        CONFESSOR("confessor", 10, 10, 13, 10, 12, 12, 9, 14, 9, List.of(EquipmentInit.BROADSWORD, EquipmentInit.BLUE_CREST_HEATER_SHIELD, EquipmentInit.FINGER_SEAL), null),
        WRETCH("wretch", 1, 10, 10, 10, 10, 10, 10, 10, 10, List.of(EquipmentInit.CLUB), null);

        private final String name;
        private final int level;
        private final int vigor;
        private final int mind;
        private final int endurance;
        private final int strength;
        private final int dexterity;
        private final int intelligence;
        private final int faith;
        private final int arcane;
        private final List<Supplier<? extends Item>> starterItems;
        private final ModArmorMaterial starterArmor;

        Base(String name, int level, int vigor, int mind, int endurance, int strength, int dexterity, int intelligence, int faith, int arcane, List<Supplier<? extends Item>> starterItems, ModArmorMaterial starterArmor) {
            this.name = name;
            this.level = level;
            this.vigor = vigor;
            this.mind = mind;
            this.endurance = endurance;
            this.strength = strength;
            this.dexterity = dexterity;
            this.intelligence = intelligence;
            this.faith = faith;
            this.arcane = arcane;
            this.starterItems = starterItems;
            this.starterArmor = starterArmor;
        }

        public String getName() {
            return this.name;
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

        public List<Supplier<? extends Item>> getStarterItems() {
            return this.starterItems;
        }

        public ModArmorMaterial getStarterArmor() {
            return this.starterArmor;
        }
    }

    public enum Keepsake {
        NONE(() -> null, null),
        MEDALLION(ItemInit.CRIMSON_AMBER_MEDALLION, "crimson_amber_medallion"),
        RUNE(ItemInit.BLOCKS_BETWEEN_RUNE, "blocks_between_rune"),
        SEED(ItemInit.GOLDEN_SEED, "golden_seed"),
        IMP(() -> null, "fanged_imp_ashes"),
        POT(ItemInit.CRACKED_POT, "cracked_pot"),
        STONESWORD(ItemInit.STONESWORD_KEY, "stonesword_key"),
        BEWITCHING(ItemInit.BEWITCHING_BRANCH, "bewitching_branch"),
        PRAWN(ItemInit.BOILED_PRAWN, "boiled_prawn"),
        SHABRIRI(ItemInit.SHABRIRIS_WOE, "shabriris_woe");

        private final Supplier<Item> item;
        private final String descriptionId;

        Keepsake(@Nullable Supplier<Item> item, @Nullable String descriptionId) {
            this.item = item;
            this.descriptionId = descriptionId;
        }

        public Item getItem() {
            return this.item.get();
        }

        public String getDescriptionId() {
            return this.descriptionId;
        }
    }
}
