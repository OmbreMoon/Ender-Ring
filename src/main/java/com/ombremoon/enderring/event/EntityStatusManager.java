package com.ombremoon.enderring.event;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.capability.EntityStatus;
import com.ombremoon.enderring.common.capability.EntityStatusProvider;
import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.object.entity.SpellAttackMob;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.EntityStatusUtil;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class EntityStatusManager {

    @SubscribeEvent
    public static void onAttachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof LivingEntity livingEntity) {
            var provider = new EntityStatusProvider(livingEntity);
            if (provider.hasCap()) {
                var cap = provider.getCapability(EntityStatusProvider.ENTITY_STATUS).orElse(null);
                cap.initStatus(livingEntity);
                event.addCapability(EntityStatusProvider.CAPABILITY_LOCATION, provider);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity instanceof Player) {
                EntityStatus<?> entityStatus = EntityStatusUtil.getEntityStatus(livingEntity, EntityStatus.class);
                if (entityStatus != null/* && !entityStatus.isInitialized()*/) {
                    entityStatus.defineEntityData(livingEntity);
                    if (event.getLevel() instanceof ServerLevel && livingEntity instanceof ServerPlayer player) {
                        player.getEntityData().set(PlayerStatus.FP, EntityStatusUtil.getMaxFP(player));
                        ModNetworking.syncCap(player);
                    }
                }
            }
        }
    }

/*    @SubscribeEvent
    public static void onSpellActivateTick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();

        if (livingEntity instanceof Player || livingEntity instanceof SpellAttackMob) {
            if (!livingEntity.level().isClientSide) {
                if (EntityStatusProvider.isPresent(livingEntity)) {
                    ObjectOpenHashSet<AbstractSpell> activeSpells = EntityStatusUtil.getActiveSpells(livingEntity);

                    activeSpells.removeIf(spell -> spell.isInactive);
                    for (AbstractSpell abstractSpell : activeSpells) {
                        abstractSpell.tick();
                    }
                }
            }
        }
    }*/

}
