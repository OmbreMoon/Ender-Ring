package com.ombremoon.enderring.event;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.client.KeyBinds;
import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.object.entity.LevelledMob;
import com.ombremoon.enderring.common.object.item.equipment.IQuickAccess;
import com.ombremoon.enderring.common.object.world.ERDamageSource;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.EntityStatusUtil;
import com.ombremoon.enderring.util.FlaskUtil;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class PlayerStatusManager {

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();
        if (event.isWasDeath()) {
            EntityStatusProvider.get(newPlayer).deserializeNBT(EntityStatusProvider.get(oldPlayer).serializeNBT());

            if (oldPlayer.hasEffect(StatusEffectInit.SACRIFICIAL_TWIG.get())) {
                int runeAmount = EntityStatusUtil.getRunesHeld(oldPlayer);
                EntityStatusUtil.increaseRunes(newPlayer, runeAmount);
                IDynamicStackHandler stackHandler = CurioHelper.getTalismanStacks(oldPlayer);
                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack itemStack = stackHandler.getStackInSlot(i);
                    if (itemStack.is(ItemInit.SACRIFICIAL_TWIG.get())) {
                        stackHandler.setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            }

            for (Attribute attribute : EntityAttributeInit.PLAYER_ATTRIBUTES) {
                EntityStatusUtil.setBaseStat(newPlayer, attribute, (int) oldPlayer.getAttributeBaseValue(attribute), true);
            }
            newPlayer.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(oldPlayer.getAttributeBaseValue(Attributes.MAX_HEALTH));
            ModNetworking.updateMainAttributes(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntity() instanceof Player player))
            return;

        if (event.getSource().is(DamageTypes.FALL) && player.hasEffect(StatusEffectInit.LONGTAIL_CAT_TALISMAN.get())) {
            if (player.getMaxHealth() - event.getAmount() > 0) {
                event.setCanceled(true);
                return;
            }
        }

        float damage = event.getAmount();
        if (event.getSource() instanceof ERDamageSource damageSource && damageSource.isPhysicalDamage()) {
            if (player.hasEffect(StatusEffectInit.PHYSICAL_DAMAGE_NEGATION.get()))
                damage *= 0.85F;
        }
        if (player.hasEffect(StatusEffectInit.RADAGONS_SCARSEAL.get())) {
            MobEffectInstance instance = player.getEffect(StatusEffectInit.RADAGONS_SCARSEAL.get());
            damage *= 1.1F + (0.05F * instance.getAmplifier());
        }
        if (player.hasEffect(StatusEffectInit.MARIKAS_SCARSEAL.get())) {
            MobEffectInstance instance = player.getEffect(StatusEffectInit.MARIKAS_SCARSEAL.get());
            damage *= 1.1F + (0.05F * instance.getAmplifier());
        }
        if (player.hasEffect(StatusEffectInit.DRAGONCREST_SHIELD_TALISMAN.get())) {
            MobEffectInstance instance = player.getEffect(StatusEffectInit.DRAGONCREST_SHIELD_TALISMAN.get());
            int amp = instance.getAmplifier();
            if (amp == 0) damage *= 0.9F;
            else if (amp == 1) damage *= 0.87F;
            else damage *= 0.83F;
        }
        if (player.hasEffect(StatusEffectInit.OPALINE_BUBBLE.get()) && (event.getSource().getEntity() != null || event.getSource().is(DamageTypes.EXPLOSION))) {
            player.removeEffect(StatusEffectInit.OPALINE_BUBBLE.get());
            damage *= 0.1F;
        }
        if (player.hasEffect(StatusEffectInit.FROSTBITE.get())) {
            damage *= 1.2F;
            if (event.getSource().is(ModDamageTypes.FIRE))
                player.removeEffect(StatusEffectInit.FROSTBITE.get());
        }
        if (player.hasEffect(StatusEffectInit.SLEEP.get())) {
            player.removeEffect(StatusEffectInit.SLEEP.get());
        }
        event.setAmount(damage);
//        Constants.LOG.info(String.valueOf(event.getAmount()));
    }

    @SubscribeEvent
    public static void onSpellActivateTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if (event.phase == TickEvent.Phase.START && !player.level().isClientSide) {
            if (EntityStatusProvider.isPresent(player)) {
                ObjectOpenHashSet<AbstractSpell> activeSpells = EntityStatusUtil.getActiveSpells(player);

                activeSpells.removeIf(spell -> spell.isInactive);
                for (AbstractSpell abstractSpell : activeSpells) {
                    abstractSpell.tick();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase != TickEvent.Phase.END) return;
        if (player.level().isClientSide) return;

        ItemStack itemStack = player.getMainHandItem();

        if (!EntityStatusProvider.isPresent(player)) return;
        if (itemStack == EntityStatusUtil.getCachedItem(player) || !(itemStack.getItem() instanceof IQuickAccess)) return;

        if (EntityStatusUtil.isUsingQuickAccess(player)) {
            PlayerStatus playerStatus =  EntityStatusProvider.get(player);
            if (itemStack != ItemStack.EMPTY) {
                int j = playerStatus.getUseItemTicks();
                if (j >= 0) {
                    playerStatus.setUseItemTicks(Math.max(j - 1, 0));
                    ModNetworking.useQuickAccessItem(playerStatus.getUseItemTicks(), (ServerPlayer) player);
                }
            }/* else if (itemStack != ItemStack.EMPTY) {
                itemStack.use(player.level(), player, InteractionHand.MAIN_HAND);
                ModNetworking.getInstance().useQuickAccessItem();
            }*/
        }
    }

    @SubscribeEvent
    public static void onSpellChargeTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase != TickEvent.Phase.END) return;
        if (!player.level().isClientSide) return;

        if (!EntityStatusProvider.isPresent(player)) return;

        if (EntityStatusUtil.isChannelling(player)) {
            if (!KeyBinds.getCastKey().isDown()) {
                ModNetworking.chargeSpell(false);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        FlaskUtil.resetFlaskCooldowns(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        FlaskUtil.resetFlaskCooldowns(event.getEntity());
    }
}
