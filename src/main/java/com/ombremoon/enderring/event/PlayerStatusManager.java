package com.ombremoon.enderring.event;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.capability.PlayerStatusProvider;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class PlayerStatusManager {

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
                for (Map.Entry<UUID, AttributeModifier> entry : PlayerStatusUtil.getStatusAttributeModifiers(oldPlayer).entrySet()) {
                    if (attribute.getDescriptionId().equalsIgnoreCase(entry.getValue().getName()) && attribute != EntityAttributeInit.RUNES_HELD.get()) {
                        PlayerStatusUtil.addStatModifier(newPlayer, attribute, entry.getKey(), (int) entry.getValue().getAmount(), entry.getValue().getOperation());
                    }
                }
            }
        }
    }

    private static void invalidateCharacterStats(Player oldPlayer, Player newPlayer, Attribute attribute) {
        if (attribute != EntityAttributeInit.RUNES_HELD.get()) {
            newPlayer.getAttributes().getInstance(attribute).setBaseValue(oldPlayer.getAttributeBaseValue(attribute));
        }
        newPlayer.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(oldPlayer.getAttributeBaseValue(Attributes.MAX_HEALTH));
        newPlayer.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue(oldPlayer.getAttributeBaseValue(Attributes.ATTACK_DAMAGE));
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        if (player.hasEffect(StatusEffectInit.PHYSICAL_DAMAGE_NEGATION.get()) && event.getSource().is(ModDamageTypes.PHYSICAL)) {
            event.setAmount(event.getAmount() * 0.85F);
        }
    }
}
