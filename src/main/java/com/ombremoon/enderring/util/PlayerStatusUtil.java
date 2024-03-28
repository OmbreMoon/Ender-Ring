package com.ombremoon.enderring.util;

import com.ombremoon.enderring.capability.PlayerStatusProvider;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;

public class PlayerStatusUtil {
    public static final UUID RUNES_HELD = UUID.randomUUID();
    public static final UUID VIGOR = UUID.fromString("2489d650-cd33-4bb1-a3c5-95610555ec2c");
    public static final UUID MIND = UUID.fromString("d8b0623b-787d-46f8-999a-66226d336f7e");
    public static final UUID STRENGTH = UUID.fromString("f6a50f0d-18aa-4f8a-be95-fe6e05141be0");
    public static final UUID DEXTERITY = UUID.fromString("4a3bb0a1-95df-4c86-90f3-3db09a473747");
    public static final UUID INTELLIGENCE = UUID.fromString("a7b53dcf-0c86-42be-a2d2-f3ab215cbf1b");
    public static final UUID FAITH = UUID.fromString("9f51d3ae-2b49-4128-8163-023a22399053");
    public static final UUID ARCANE = UUID.fromString("d30470e4-7d8b-4884-9e67-53dc0d645669");

    public static int getRuneLevel(Player player) {
        return (int) player.getAttributes().getInstance(EntityAttributeInit.RUNE_LEVEL.get()).getValue();
    }

    public static int getRunesHeld(Player player) {
        return (int) player.getAttributes().getInstance(EntityAttributeInit.RUNES_HELD.get()).getValue();
    }

    public static int getPlayerStat(Player player, Attribute attribute) {
        return (int) player.getAttributes().getInstance(attribute).getValue();
    }

    public static int getRunesNeeded(Player player) {
        float f = ((getRuneLevel(player) + 81) - 92) * 0.02F;
        return Mth.floor(((f + 0.1) * ((getRuneLevel(player) + 81) ^ 2)) + 1);
    }

    public static void increaseRunes(Player player, int increaseAmount) {
        AttributeInstance attributeInstance = player.getAttributes().getInstance(EntityAttributeInit.RUNES_HELD.get());
        AttributeModifier attributemodifier = attributeInstance.getModifier(RUNES_HELD);
        if (attributemodifier != null) {
            attributeInstance.removeModifier(attributemodifier);
            attributeInstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), "runes_held", attributemodifier.getAmount() + increaseAmount, attributemodifier.getOperation()));
        } else {
            attributeInstance.addPermanentModifier(new AttributeModifier(RUNES_HELD, "runes_held", increaseAmount, AttributeModifier.Operation.ADDITION));
        }
    }

    public static void increaseStat(Player player, Attribute attribute, UUID uuid, double increaseAmount) {
        AttributeInstance attributeInstance = player.getAttributes().getInstance(attribute);
        AttributeModifier attributemodifier = attributeInstance.getModifier(uuid);
        if (attributemodifier != null) {
            attributeInstance.removeModifier(attributemodifier);
            attributemodifier = new AttributeModifier(uuid, attribute.getDescriptionId(), attributemodifier.getAmount() + increaseAmount, attributemodifier.getOperation());
            attributeInstance.addPermanentModifier(attributemodifier);
        } else {
            attributemodifier = new AttributeModifier(uuid, attribute.getDescriptionId(), increaseAmount, AttributeModifier.Operation.ADDITION);
            attributeInstance.addPermanentModifier(attributemodifier);
        }
        addStatusAttributeModifiers(player, uuid, attributemodifier);
    }

    public static Map<UUID, AttributeModifier> getStatusAttributeModifiers(Player player) {
        return PlayerStatusProvider.get(player).getStatusAttributeModifiers();
    }

    public static void addStatusAttributeModifiers(Player player, UUID uuid, AttributeModifier attributeModifier) {
        PlayerStatusProvider.get(player).addStatusAttributeModifiers(uuid, attributeModifier);
    }
}
