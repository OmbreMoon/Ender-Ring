package com.ombremoon.enderring.event;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.capability.PlayerStatusProvider;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellInstance;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class PlayerStatusManager {
    public static final List<RegistryObject<Item>> FLASKS = List.of(ItemInit.WONDROUS_PHYSICK_FLASK, ItemInit.CRIMSON_FLASK, ItemInit.CERULEAN_FLASK);

    @SubscribeEvent
    public static void onAttachPlayerCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(PlayerStatusProvider.PLAYER_STATUS).isPresent()) {
                var provider = new PlayerStatusProvider();
                event.addCapability(PlayerStatusProvider.CAPABILITY_LOCATION, provider);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        Collection<Attribute> playerStats = EntityAttributeInit.ATTRIBUTES.getEntries().stream().map(RegistryObject::get).collect(Collectors.toSet());

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        if (event.isWasDeath()) {
            for (Attribute attribute : playerStats) {
                invalidateCharacterStats(oldPlayer, newPlayer, attribute);
            }
        }
    }

    private static void invalidateCharacterStats(Player oldPlayer, Player newPlayer, Attribute attribute) {
        if (attribute != EntityAttributeInit.RUNES_HELD.get()) {
            newPlayer.getAttributes().getInstance(attribute).setBaseValue(oldPlayer.getAttributeBaseValue(attribute));
            for (Map.Entry<UUID, AttributeModifier> entry : PlayerStatusUtil.getStatusAttributeModifiers(oldPlayer).entrySet()) {
                if (attribute.getDescriptionId().equalsIgnoreCase(entry.getValue().getName()) && attribute != EntityAttributeInit.RUNES_HELD.get()) {
                    PlayerStatusUtil.addStatModifier(newPlayer, attribute, entry.getKey(), (int) entry.getValue().getAmount(), entry.getValue().getOperation());
                }
            }
        }
        newPlayer.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(oldPlayer.getAttributeBaseValue(Attributes.MAX_HEALTH));
        newPlayer.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue(oldPlayer.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        if (player.hasEffect(StatusEffectInit.PHYSICAL_DAMAGE_NEGATION.get()) && event.getSource().is(ModDamageTypes.PHYSICAL)) {
            event.setAmount(event.getAmount() * 0.85F);
        }
        if (player.hasEffect(StatusEffectInit.OPALINE_BUBBLE.get()) && (event.getSource().getEntity() != null || event.getSource().is(DamageTypes.EXPLOSION))) {
            player.removeEffect(StatusEffectInit.OPALINE_BUBBLE.get());
            event.setAmount(event.getAmount() * 0.1F);
        }
    }

    @SubscribeEvent
    public static void onAbilityTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (PlayerStatusProvider.isPresent(player)) {
            Iterator<AbstractSpell> iterator = PlayerStatusUtil.getActiveSpells(player).keySet().iterator();

            try {
                while (iterator.hasNext()) {
                    AbstractSpell abstractSpell = iterator.next();
                    SpellInstance spellInstance = PlayerStatusUtil.getActiveSpells(player).get(abstractSpell);
                    if (!spellInstance.tickSpellEffect(player, player.level(), player.getOnPos(), () -> {
                        abstractSpell.onSpellUpdate(spellInstance, player, player.level(), player.getOnPos());
                    })) {
                        if (!player.level().isClientSide) {
                            iterator.remove();
                            abstractSpell.onSpellStop(spellInstance, player, player.level(), player.getOnPos());
                        }
                        //TODO: SUBJECT TO CHANGE
                    } else if (spellInstance.getDuration() % 600 == 0) {
                        abstractSpell.onSpellUpdate(spellInstance, player, player.level(), player.getOnPos());
                    }
                }
            } catch (ConcurrentModificationException ignored) {
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        resetFlaskCooldowns(event);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        resetFlaskCooldowns(event);
    }

    private static void resetFlaskCooldowns(PlayerEvent playerEvent) {
        Player player = playerEvent.getEntity();
        ItemCooldowns itemCooldowns = player.getCooldowns();
        for (RegistryObject<Item> item : FLASKS) {
            if (itemCooldowns.isOnCooldown(item.get())) {
                itemCooldowns.removeCooldown(item.get());
            }
        }
    }
}
