package com.ombremoon.enderring.util;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.capability.EntityStatus;
import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.entity.spirit.ISpiritAsh;
import com.ombremoon.enderring.network.ModNetworking;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
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
import java.util.Objects;
import java.util.UUID;

public class EntityStatusUtil {

    @SuppressWarnings("unchecked")
    public static <T extends EntityStatus<?>> T getEntityStatus(LivingEntity livingEntity, Class<T> status) {
        if (livingEntity != null) {
            EntityStatus<?> entityStatus = EntityStatusProvider.get(livingEntity);
            if (entityStatus != null && status.isAssignableFrom(entityStatus.getClass())) {
                return (T) entityStatus;
            }
        }
        return null;
    }

    public static PlayerStatus getPlayerStatus(Player player) {
        return getEntityStatus(player, PlayerStatus.class);
    }

    public static int getRuneLevel(Player player) {
        return (int) player.getAttributeValue(EntityAttributeInit.RUNE_LEVEL.get());
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

    public static void setBaseStat(Player player, Attribute attribute, double bastStat) {
        setBaseStat(player, attribute, bastStat, false);
    }

    public static void setBaseStat(Player player, Attribute attribute, double bastStat, boolean setMax) {
        player.getAttributes().getInstance(attribute).setBaseValue(bastStat);
        updateMainAttributes(setMax);
        updateDefense(player, attribute);
        updateResistances(player, attribute);
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

    public static int getRunesHeld(Player player) {
        return player.getEntityData().get(PlayerStatus.RUNES);
    }

    public static void setRunesHeld(Player player, int amount) {
        player.getEntityData().set(PlayerStatus.RUNES, amount);
    }

    public static void increaseRunes(Player player, int increaseAmount) {
        player.getEntityData().set(PlayerStatus.RUNES, getRunesHeld(player) + increaseAmount);
    }

    public static boolean consumeRunes(Player player, int amount, boolean forceConsume) {
        return getPlayerStatus(player).consumeRunes(amount, forceConsume);
    }

    public static double getFP(Player player) {
        return getPlayerStatus(player).getFP();
    }

    public static void setFP(ServerPlayer player, float fpAmount) {
        getPlayerStatus(player).setFP(fpAmount);
    }

    public static float getMaxFP(Player player) {
        return getPlayerStatus(player).getMaxFP();
    }

    public static void increaseFP(Player player, float fpAmount) {
        getPlayerStatus(player).setFP((float) Math.min(getFP(player) + fpAmount, player.getAttributeValue(EntityAttributeInit.MAX_FP.get())));
    }

    public static boolean consumeFP(Player player, float amount, AbstractSpell abstractSpell, boolean forceConsume) {
        return getPlayerStatus(player).consumeFP(amount, abstractSpell, forceConsume);
    }

    public void increaseBuildUp(LivingEntity livingEntity, EntityDataAccessor<Float> accessor, float amount, float limit) {
        livingEntity.getEntityData().set(accessor,  Mth.clamp(amount, 0.0F, limit));
    }

    public static void updateDefense(Player player, Attribute attribute) {
        if (!isMainAttribute(attribute)) {
            for (WeaponDamage weaponDamage : WeaponDamage.values()) {
                if (weaponDamage != WeaponDamage.LIGHTNING) {
                    if (weaponDamage.getWeaponScaling().getAttribute() == attribute) {
                        player.getAttribute(weaponDamage.getDefenseAttribute()).setBaseValue(DamageUtil.calculateDefense(player, weaponDamage));
                        break;
                    }
                } else {
                    player.getAttribute(EntityAttributeInit.LIGHT_DEFENSE.get()).setBaseValue(DamageUtil.calculateDefense(player, WeaponDamage.LIGHTNING));
                }
                if (attribute == EntityAttributeInit.RUNE_LEVEL.get()) {
                    player.getAttribute(weaponDamage.getDefenseAttribute()).setBaseValue(DamageUtil.calculateDefense(player, weaponDamage));
                }
            }
        }
    }
    
    public static void updateResistances(Player player, Attribute attribute) {
        if (isMainAttribute(attribute)) {
            player.getAttribute(EntityAttributeInit.ATTRIBUTE_MAP.get(attribute)).setBaseValue(Mth.floor(DamageUtil.calculateResistance(player, attribute)));
        } else if (attribute == EntityAttributeInit.ARCANE.get()) {
            player.getAttribute(EntityAttributeInit.VITALITY.get()).setBaseValue(Mth.floor(DamageUtil.calculateResistance(player, EntityAttributeInit.ARCANE.get())));
        } else if (attribute == EntityAttributeInit.RUNE_LEVEL.get()) {
            for (var entry : EntityAttributeInit.ATTRIBUTE_MAP.entrySet()) {
                player.getAttribute(entry.getValue()).setBaseValue(Mth.floor(DamageUtil.calculateResistance(player, entry.getKey())));
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

    @SuppressWarnings("unchecked")
    public static ObjectOpenHashSet<AbstractSpell> getActiveSpells(LivingEntity livingEntity) {
        return getEntityStatus(livingEntity, EntityStatus.class).getActiveSpells();
    }

    public static void activateSpell(LivingEntity livingEntity, AbstractSpell abstractSpell) {
        getActiveSpells(livingEntity).add(abstractSpell);
        setRecentlyActivatedSpell(livingEntity, abstractSpell);
    }

    @SuppressWarnings("unchecked")
    public static LinkedHashSet<SpellType<?>> getSpellSet(LivingEntity livingEntity) {
        return getEntityStatus(livingEntity, EntityStatus.class).getSpellSet();
    }

    public static AbstractSpell getRecentlyActivatedSpell(LivingEntity livingEntity) {
        return getEntityStatus(livingEntity, EntityStatus.class).getRecentlyActivatedSpell();
    }

    public static void setRecentlyActivatedSpell(LivingEntity livingEntity, AbstractSpell abstractSpell) {
        getEntityStatus(livingEntity, EntityStatus.class).setRecentlyActivatedSpell(abstractSpell);
    }

    public static SpellType<?> getSelectedSpell(LivingEntity livingEntity) {
        return getEntityStatus(livingEntity, EntityStatus.class).getSelectedSpell();
    }

    @SuppressWarnings("unchecked")
    public static void setSelectedSpell(LivingEntity livingEntity, SpellType<?> spellType) {
        getEntityStatus(livingEntity, EntityStatus.class).setSelectedSpell(spellType);
        if (livingEntity instanceof ServerPlayer player) {
            ModNetworking.syncCap(player);
        }
    }

    public static boolean isChannelling(Player player) {
        return getPlayerStatus(player).isChannelling();
    }

    public static void setChannelling(ServerPlayer player, boolean channelling) {
        getPlayerStatus(player).setChannelling(channelling);
        ModNetworking.syncCap(player);
    }

    public static EntityType<? extends ISpiritAsh> getSpiritSummon(Player player) {
        return getPlayerStatus(player).getSpiritSummon();
    }

    public static void setSpiritSummon(ServerPlayer player, EntityType<? extends ISpiritAsh> spiritSummon) {
        getPlayerStatus(player).setSpiritSummon(spiritSummon);
        ModNetworking.syncCap(player);
    }

    public static boolean isTorrentSpawnedOrIncapacitated(Player player) {
        return getPlayerStatus(player).getTorrentHealth() <= 0 || getPlayerStatus(player).isSpawnedTorrent();
    }

    public static void setTorrentSpawned(Player player, boolean isSpawned) {
        getPlayerStatus(player).setSpawnTorrent(isSpawned);
    }

    public static double getTorrentHealth(Player player) {
        return getPlayerStatus(player).getTorrentHealth();
    }

    public static void setTorrentHealth(Player player, double health) {
        getPlayerStatus(player).setTorrentHealth(health);
    }

    public static int getTalismanPouches(Player player) {
        return getPlayerStatus(player).getTalismanPouches();
    }

    public static void increaseTalismanPouches(ServerPlayer player) {
        getPlayerStatus(player).increaseTalismanPouches();
        ModNetworking.syncCap(player);
    }

    public static int getMemoryStones(Player player) {
        return getPlayerStatus(player).getMemoryStones();
    }

    public static void increaseMemoryStones(ServerPlayer player) {
        getPlayerStatus(player).increaseMemoryStones();
        ModNetworking.syncCap(player);
    }

    public static ItemStack getQuickAccessItem(Player player) {
        return CurioHelper.getQuickAccessStack(player, getQuickAccessSlot(player));
    }

    public static int getQuickAccessSlot(Player player) {
        return getPlayerStatus(player).getQuickAccessSlot();
    }

    public static void setQuickAccessSlot(ServerPlayer player, int slot) {
        getPlayerStatus(player).setQuickAccessSlot(slot);
        ModNetworking.syncCap(player);
    }

    public static boolean isUsingQuickAccess(Player player) {
        return getPlayerStatus(player).isUsingQuickAccess();
    }

    public static void setUsingQuickAccess(Player player, boolean usingQuickAccess) {
        PlayerStatus playerStatus = EntityStatusProvider.get(player);
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
        return getPlayerStatus(player).getCachedItem();
    }

    private static void setCachedItem(Player player, ItemStack itemStack) {
        getPlayerStatus(player).setCachedItem(itemStack);
    }

}
