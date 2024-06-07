package com.ombremoon.enderring.util;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.capability.IPlayerStatus;
import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.network.ModNetworking;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class EntityStatusUtil {
    public static final UUID RUNES_HELD = UUID.randomUUID();
    public static final String VIGOR = "2489d650-cd33-4bb1-a3c5-95610555ec2c";
    public static final UUID MIND = UUID.fromString("d8b0623b-787d-46f8-999a-66226d336f7e");
    public static final UUID ENDURANCE = UUID.fromString("13c4e562-d177-41da-8391-f3864333d899");
    public static final UUID STRENGTH = UUID.fromString("f6a50f0d-18aa-4f8a-be95-fe6e05141be0");
    public static final UUID DEXTERITY = UUID.fromString("4a3bb0a1-95df-4c86-90f3-3db09a473747");
    public static final UUID INTELLIGENCE = UUID.fromString("a7b53dcf-0c86-42be-a2d2-f3ab215cbf1b");
    public static final UUID FAITH = UUID.fromString("9f51d3ae-2b49-4128-8163-023a22399053");
    public static final UUID ARCANE = UUID.fromString("d30470e4-7d8b-4884-9e67-53dc0d645669");
    public static final UUID FP = UUID.fromString("2031ddaf-832a-4a2b-a091-2ccaf65e6cbb");

    public static int getRuneLevel(Player player) {
        return (int) player.getAttributeValue(EntityAttributeInit.RUNE_LEVEL.get());
    }

    public static int getRunesHeld(Player player) {
        return (int) player.getAttributeValue(EntityAttributeInit.RUNES_HELD.get());
    }

    public static double getEntityAttribute(LivingEntity livingEntity, Attribute attribute) {
        return livingEntity.getAttributeValue(attribute);
    }

    private static int getRuneNeeded(Player player) {
        float f = Math.max(((getRuneLevel(player) + 81) - 92) * 0.02F, 0);
        return Mth.floor(((f + 0.1) * ((getRuneLevel(player) + 81) ^ 2)) + 1);
    }

    public static void increaseBaseStatWithLevel(Player player, Attribute attribute, int increaseAmount) {
        increaseBaseStat(player, attribute, increaseAmount, true);
        Objects.requireNonNull(player.getAttributes().getInstance(EntityAttributeInit.RUNE_LEVEL.get())).setBaseValue(player.getAttributeValue(EntityAttributeInit.RUNE_LEVEL.get()) + increaseAmount);
    }

    public static void increaseBaseStat(Player player, Attribute attribute, int increaseAmount, boolean setMax) {
        setBaseStat(player, attribute, (int) (player.getAttributeValue(attribute) + increaseAmount), setMax);
    }

    public static void setBaseStat(Player player, Attribute attribute, int bastStat) {
        setBaseStat(player, attribute, bastStat, false);
    }

    public static void setBaseStat(Player player, Attribute attribute, int bastStat, boolean setMax) {
        Objects.requireNonNull(player.getAttributes().getInstance(attribute)).setBaseValue(bastStat);
        updateDefense(player, attribute);
        updateMainAttributes(setMax);
    }

    public static void increaseRunes(Player player, double increaseAmount) {
        AttributeInstance attributeInstance = player.getAttributes().getInstance(EntityAttributeInit.RUNES_HELD.get());
        AttributeModifier attributemodifier = attributeInstance.getModifier(RUNES_HELD);
        if (attributemodifier != null) {
            attributeInstance.removeModifier(attributemodifier);
            attributeInstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), "runes_held", attributemodifier.getAmount() + increaseAmount, attributemodifier.getOperation()));
        } else {
            attributeInstance.addPermanentModifier(new AttributeModifier(RUNES_HELD, "runes_held", increaseAmount, AttributeModifier.Operation.ADDITION));
        }
    }

    public static void addStatModifier(Player player, Attribute attribute, UUID uuid, double increaseAmount, AttributeModifier.Operation operation, boolean setMax) {
        AttributeInstance attributeInstance = player.getAttributes().getInstance(attribute);
        AttributeModifier attributemodifier = attributeInstance.getModifier(uuid);
        if (attributemodifier != null) {
            attributeInstance.removeModifier(attributemodifier);
            attributemodifier = new AttributeModifier(uuid, attribute.getDescriptionId(), attributemodifier.getAmount() + increaseAmount, attributemodifier.getOperation());
            attributeInstance.addTransientModifier(attributemodifier);
        } else {
            attributemodifier = new AttributeModifier(uuid, attribute.getDescriptionId(), increaseAmount, operation);
            attributeInstance.addTransientModifier(attributemodifier);
        }
        updateMainAttributes(setMax);
    }

    public static void removeStatModifier(Player player, Attribute attribute, UUID uuid, double decreaseAmount, boolean setMax) {
        AttributeInstance attributeInstance = player.getAttributes().getInstance(attribute);
        AttributeModifier attributemodifier = attributeInstance.getModifier(uuid);
        if (attributemodifier != null) {
            attributeInstance.removeModifier(attributemodifier);
            var amount = attributemodifier.getAmount() - decreaseAmount;
            if (amount > 0) {
                attributemodifier = new AttributeModifier(uuid, attribute.getDescriptionId(), amount, attributemodifier.getOperation());
                attributeInstance.addTransientModifier(attributemodifier);
            }
        }
        updateMainAttributes(setMax);
    }

    public static void addStatModifier(Player player, Attribute attribute, UUID uuid, double increaseAmount, boolean setMax) {
        addStatModifier(player, attribute, uuid, increaseAmount, AttributeModifier.Operation.ADDITION, setMax);
    }

    public static double getFP(Player player) {
        return EntityStatusProvider.get(player).getFP();
    }

    public static void setFP(ServerPlayer player, float fpAmount) {
        EntityStatusProvider.get(player).setFP(fpAmount);
        ModNetworking.syncCap(player);
    }

    public static double getMaxFP(Player player) {
        return EntityStatusProvider.get(player).getMaxFP();
    }

    public static void increaseFP(Player player, float fpAmount) {
        EntityStatusProvider.get(player).setFP((float) Math.min(getFP(player) + fpAmount, player.getAttributeValue(EntityAttributeInit.MAX_FP.get())));
    }

    public static boolean consumeFP(Player player, float amount, boolean forceConsume) {
        return EntityStatusProvider.get(player).consumeFP(amount, forceConsume);
    }

    public static void updateDefense(Player player, Attribute attribute) {
        if (!isMainAttribute(attribute)) {
            for (WeaponDamage weaponDamage : WeaponDamage.values()) {
                if (weaponDamage != WeaponDamage.LIGHTNING) {
                    if (weaponDamage.getWeaponScaling().getAttribute() == attribute) {
                        player.getEntityData().set(weaponDamage.getDefAccessor(), DamageUtil.calculateDefense(player, weaponDamage));
                    }
                } else {
                        player.getEntityData().set(PlayerStatus.LIGHTNING_DEF, DamageUtil.calculateDefense(player, WeaponDamage.LIGHTNING));
                }
            }
        }
    }

    private static void updateMainAttributes(boolean setMax) {
        ModNetworking.updateMainAttributes(setMax);
    }

    public static boolean isMainAttribute(Attribute attribute) {
        return attribute == EntityAttributeInit.VIGOR.get() || attribute == EntityAttributeInit.MIND.get() || attribute == EntityAttributeInit.ENDURANCE.get();
    }

    public static SpellType<?> getSpellByName(ResourceLocation resourceLocation) {
        for (RegistryObject<SpellType<?>> registryObject : SpellInit.SPELL_TYPE.getEntries()) {
            if (registryObject.getId().equals(resourceLocation)) {
                return registryObject.get();
            }
        }
        return null;
    }

    public static CompoundTag storeSpell(SpellType<?> spellType) {
        CompoundTag compoundTag = new CompoundTag();
        return storeSpell(compoundTag, spellType);
    }

    private static CompoundTag storeSpell(CompoundTag compoundTag, SpellType<?> spellType) {
        compoundTag.putString("Spell", spellType.getResourceLocation().toString());
        return compoundTag;
    }

    public static ResourceLocation getSpellId(CompoundTag compoundTag, String tagKey) {
        return ResourceLocation.tryParse(compoundTag.getString(tagKey));
    }

    public static ObjectOpenHashSet<AbstractSpell> getActiveSpells(Player player) {
        return EntityStatusProvider.get(player).getActiveSpells();
    }

    public static void activateSpell(Player player, AbstractSpell abstractSpell) {
        getActiveSpells(player).add(abstractSpell);
    }

    public static LinkedHashSet<SpellType<?>> getSpellSet(Player player) {
        return EntityStatusProvider.get(player).getSpellSet();
    }

    public static SpellType<?> getSelectedSpell(Player player) {
        return EntityStatusProvider.get(player).getSelectedSpell();
    }

    public static void setSelectedSpell(ServerPlayer player, SpellType<?> spellType) {
        EntityStatusProvider.get(player).setSelectedSpell(spellType);
        ModNetworking.syncCap(player);
    }

    public static EntityType<?> getSpiritSummon(Player player) {
        return EntityStatusProvider.get(player).getSpiritSummon();
    }

    public static void setSpiritSummon(ServerPlayer player, EntityType<?> spiritSummon) {
        EntityStatusProvider.get(player).setSpiritSummon(spiritSummon);
        ModNetworking.syncCap(player);
    }

    public static boolean isTorrentSpawnedOrIncapacitated(Player player) {
        return EntityStatusProvider.get(player).getTorrentHealth() <= 0 || EntityStatusProvider.get(player).isSpawnedTorrent();
    }

    public static void setTorrentSpawned(Player player, boolean isSpawned) {
        EntityStatusProvider.get(player).setSpawnTorrent(isSpawned);
    }

    public static double getTorrentHealth(Player player) {
        return EntityStatusProvider.get(player).getTorrentHealth();
    }

    public static void setTorrentHealth(Player player, double health) {
        EntityStatusProvider.get(player).setTorrentHealth(health);
    }

    public static int getTalismanPouches(Player player) {
        return EntityStatusProvider.get(player).getTalismanPouches();
    }

    public static void increaseTalismanPouches(ServerPlayer player) {
        EntityStatusProvider.get(player).increaseTalismanPouches();
        ModNetworking.syncCap(player);
    }

    public static int getMemoryStones(Player player) {
        return EntityStatusProvider.get(player).getMemoryStones();
    }

    public static void increaseMemoryStones(ServerPlayer player) {
        EntityStatusProvider.get(player).increaseMemoryStones();
        ModNetworking.syncCap(player);
    }

    public static ItemStack getQuickAccessItem(Player player) {
        return CurioHelper.getQuickAccessStack(player, getQuickAccessSlot(player));
    }

    public static int getQuickAccessSlot(Player player) {
        return EntityStatusProvider.get(player).getQuickAccessSlot();
    }

    public static void setQuickAccessSlot(ServerPlayer player, int slot) {
        EntityStatusProvider.get(player).setQuickAccessSlot(slot);
        ModNetworking.syncCap(player);
    }

    public static boolean isUsingQuickAccess(Player player) {
        return EntityStatusProvider.get(player).isUsingQuickAccess();
    }

    public static void setUsingQuickAccess(Player player, boolean usingQuickAccess) {
        IPlayerStatus playerStatus = EntityStatusProvider.get(player);
        playerStatus.setUsingQuickAccess(usingQuickAccess);

        if (usingQuickAccess) {
//            ItemStack itemStack = player.getMainHandItem();
            ItemStack itemStack = getQuickAccessItem(player);
            playerStatus.setUseItemTicks(itemStack.getUseDuration() == 0 ? 2 : getQuickAccessItem(player).getUseDuration() + 1);
            Constants.LOG.info(String.valueOf(playerStatus.getUseItemTicks()));
            setCachedItem(player, itemStack);
        }
    }

    public static ItemStack getCachedItem(Player player) {
        return EntityStatusProvider.get(player).getCachedItem();
    }

    private static void setCachedItem(Player player, ItemStack itemStack) {
        EntityStatusProvider.get(player).setCachedItem(itemStack);
    }

}
