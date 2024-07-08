package com.ombremoon.enderring.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Resistance;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Scalable;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import com.ombremoon.enderring.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class PlayerStatusScreen extends Screen {
    private static final ResourceLocation TEXTURE = CommonClass.customLocation("textures/gui/player_status_gui.png");
    private final int imageWidth = 360;
    private final int imageHeight = 247;

    public PlayerStatusScreen(Component pTitle) {
        super(pTitle);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        this.renderBg(pGuiGraphics, pPartialTick, pMouseX, pMouseY);
        RenderSystem.disableDepthTest();
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        RenderSystem.enableDepthTest();
    }

    private void renderBg(GuiGraphics pGuiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderUtil.setupScreen(TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 512, 512);

        Player player = Minecraft.getInstance().player;
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        int textColor = 0xc3b091;
        int x1 = x + 107;
        int x2 = x + 113;
        int x3 = x + 119;
        int x4 = x + 171;
        int x5 = x + 182;
        int x6 = x + 188;
        int x7 = x + 300;
        int x8 = x + 305;
        int x9 = x + 320;

        pGuiGraphics.drawCenteredString(this.font, player.getName(), x + 48, y + 42, textColor);

        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.VIGOR.get())), getProperLocation(EntityAttributeInit.VIGOR.get(), x2, x1), y + 88, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.MIND.get())), getProperLocation(EntityAttributeInit.MIND.get(), x2, x1), y + 98, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.ENDURANCE.get())), getProperLocation(EntityAttributeInit.ENDURANCE.get(), x2, x1), y + 108, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.STRENGTH.get())), getProperLocation(EntityAttributeInit.STRENGTH.get(), x2, x1), y + 119, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.DEXTERITY.get())), getProperLocation(EntityAttributeInit.DEXTERITY.get(), x2, x1), y + 129, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.INTELLIGENCE.get())), getProperLocation(EntityAttributeInit.INTELLIGENCE.get(), x2, x1), y + 140, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.FAITH.get())), getProperLocation(EntityAttributeInit.FAITH.get(), x2, x1), y + 152, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.ARCANE.get())), getProperLocation(EntityAttributeInit.ARCANE.get(), x2, x1), y + 162, textColor);

        pGuiGraphics.drawString(this.font, Component.literal(Mth.floor(player.getHealth()) + "/" + Mth.floor(player.getMaxHealth())), getProperLocation(Mth.floor(player.getHealth()), player.getMaxHealth() > 99 ? x6 - 6 : x6, player.getMaxHealth() > 99 ? x5 - 6 : x5, x4 - 1), y + 62, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(Mth.floor(EntityStatusUtil.getFP(player)) + "/" + getAttribute(EntityAttributeInit.MAX_FP.get())), getProperLocation(Mth.floor(EntityStatusUtil.getFP(player)), getAttributeAmount(EntityAttributeInit.MAX_FP.get()) > 99 ? x6 - 6 : x6, getAttributeAmount(EntityAttributeInit.MAX_FP.get()) > 99 ? x5 - 6 : x5, x4), y + 74, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(Mth.floor(playerPatch.getStamina()) + "/" + getAttribute(EpicFightAttributes.MAX_STAMINA.get())), getProperLocation(Mth.floor(playerPatch.getStamina()), x6, x5, x4), y + 85, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.RUNE_LEVEL.get())), getProperLocation(EntityAttributeInit.RUNE_LEVEL.get(), x6 + 18, x6 + 12, x6 + 6), y + 98, textColor);

        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.PHYS_DEFENSE.get()) + " / " + getNegation(EntityAttributeInit.PHYS_NEGATE.get())), getProperLocation(EntityAttributeInit.PHYS_DEFENSE.get(), x8, x7), y + 62, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.PHYS_DEFENSE.get()) + " / " + getNegation(EntityAttributeInit.STRIKE_NEGATE.get())), getProperLocation(EntityAttributeInit.PHYS_DEFENSE.get(), x8, x7), y + 74, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.PHYS_DEFENSE.get()) + " / " + getNegation(EntityAttributeInit.SLASH_NEGATE.get())), getProperLocation(EntityAttributeInit.PHYS_DEFENSE.get(), x8, x7), y + 85, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.PHYS_DEFENSE.get()) + " / " + getNegation(EntityAttributeInit.PIERCE_NEGATE.get())), getProperLocation(EntityAttributeInit.PHYS_DEFENSE.get(), x8, x7), y + 98, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.MAGIC_DEFENSE.get()) + " / " + getNegation(EntityAttributeInit.MAGIC_NEGATE.get())), getProperLocation(EntityAttributeInit.MAGIC_DEFENSE.get(), x8, x7), y + 113, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.FIRE_DEFENSE.get()) + " / " + getNegation(EntityAttributeInit.FIRE_NEGATE.get())), getProperLocation(EntityAttributeInit.FIRE_DEFENSE.get(), x8, x7), y + 124, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.LIGHT_DEFENSE.get()) + " / " + getNegation(EntityAttributeInit.LIGHT_NEGATE.get())), getProperLocation(EntityAttributeInit.LIGHT_DEFENSE.get(), x8, x7), y + 135, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.HOLY_DEFENSE.get()) + " / " + getNegation(EntityAttributeInit.HOLY_NEGATE.get())), getProperLocation(EntityAttributeInit.HOLY_DEFENSE.get(), x8 - 1, x7 - 1), y + 147, textColor);

        //TODO: FIX FIRST RESIST POSITION
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.IMMUNITY.get()) + " / " + getArmorResistance(EntityAttributeInit.IMMUNITY.get())), getProperLocation(EntityAttributeInit.IMMUNITY.get(), 0, x8 - 4, x7 - 5), y + 178, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.ROBUSTNESS.get()) + " / " + getArmorResistance(EntityAttributeInit.ROBUSTNESS.get())), getProperLocation(EntityAttributeInit.ROBUSTNESS.get(), 0, x8 - 4, x7 - 5), y + 190, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.FOCUS.get()) + " / " + getArmorResistance(EntityAttributeInit.FOCUS.get())), getProperLocation(EntityAttributeInit.FOCUS.get(), 0, x8 - 4, x7 - 5), y + 201, textColor);
        pGuiGraphics.drawString(this.font, Component.literal(getAttribute(EntityAttributeInit.VITALITY.get()) + " / " + getArmorResistance(EntityAttributeInit.VITALITY.get())), getProperLocation(EntityAttributeInit.VITALITY.get(), 0, x8 - 4, x7 - 5), y + 212, textColor);

//        Constants.LOG.info(SpellInit.COMET_AZUR.get().createSpell().getSpellTexture().toString());

        ItemStack itemStack = player.getMainHandItem();
        ItemStack itemStack1 = player.getOffhandItem();
        if (itemStack.getItem() instanceof Scalable scalable) {
            ScaledWeapon mainWeapon = scalable.getModifiedWeapon(itemStack);
            int mainWeaponAP = Mth.floor(DamageUtil.getWeaponAP(mainWeapon, player, scalable.getWeaponLevel(itemStack)));
            pGuiGraphics.drawString(this.font, Component.literal(String.valueOf(mainWeaponAP)), getProperLocation(mainWeaponAP, x2, x1, x3), y + 198, textColor);
        } else {
            pGuiGraphics.drawString(this.font, Component.literal("0"), x2, y + 198, textColor);
        }
        if (itemStack1.getItem() instanceof Scalable scalable) {
            ScaledWeapon offWeapon = scalable.getModifiedWeapon(itemStack1);
            int offWeaponAP = Mth.floor(DamageUtil.getWeaponAP(offWeapon, player, scalable.getWeaponLevel(itemStack1)));
            pGuiGraphics.drawString(this.font, Component.literal(String.valueOf(offWeaponAP)), getProperLocation(offWeaponAP, x2, x1, x3), y + 216, textColor);
        } else {
            pGuiGraphics.drawString(this.font, Component.literal("0"), x2, y + 215, textColor);
        }

        pGuiGraphics.blit(TEXTURE, x + 135, y + 163, 288, 247, 34, 34, 512, 512);
        pGuiGraphics.blit(TEXTURE, x + 171, y + 163, 288, 247, 34, 34, 512, 512);
        pGuiGraphics.blit(TEXTURE, x + 127, y + 163, 231, 247, 7, 9, 512, 512);

        pGuiGraphics.blit(SpellInit.COMET_AZUR.get().createSpell().getSpellTexture(), x + 136, y + 164, 0, 0, 32, 32, 32, 32);
    }

    private String getAttribute(Attribute attribute) {
        Player player = Minecraft.getInstance().player;
        return String.valueOf(Mth.floor(EntityStatusUtil.getEntityAttribute(player, attribute)));
    }

    private int getAttributeAmount(Attribute attribute) {
        Player player = Minecraft.getInstance().player;
        return Mth.floor(EntityStatusUtil.getEntityAttribute(player, attribute));
    }

    private String getNegation(Attribute attribute) {
        Player player = Minecraft.getInstance().player;
        double d0 = EntityStatusUtil.getEntityAttribute(player, attribute);
        return String.format("%.1f", d0);
    }

    private int getProperLocation(Attribute attribute, int loc1, int loc2, int loc3) {
        Player player = Minecraft.getInstance().player;
        int i = Mth.floor(EntityStatusUtil.getEntityAttribute(player, attribute));
        return i < 10 ? loc1 : i < 100 ? loc2 : loc3;
    }

    private int getProperLocation(Attribute attribute, int loc1, int loc2) {
        return getProperLocation(attribute, loc1, loc2, 0);
    }

    private int getProperLocation(int attributeAmount, int loc1, int loc2, int loc3) {
        return attributeAmount < 10 ? loc1 : attributeAmount < 100 ? loc2 : loc3;
    }

    private float getArmorNegation(Attribute attribute) {
        Player player = Minecraft.getInstance().player;
        float f = 0;
        for (ItemStack itemStack : player.getArmorSlots()) {
            if (itemStack.getItem() instanceof Resistance resistance) {
                f += resistance.getArmor().getNegation().getNegationMap().get(attribute);
            }
        }
        return f;
    }

    private int getArmorResistance(Attribute attribute) {
        Player player = Minecraft.getInstance().player;
        int i = 0;
        for (ItemStack itemStack : player.getArmorSlots()) {
            if (itemStack.getItem() instanceof Resistance resistance) {
                i += resistance.getArmor().getResistance().getResistanceMap().get(attribute);
            }
        }
        return i;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
