package com.ombremoon.enderring.event;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.compat.epicfight.gameassets.SkillInit;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedSkillSlots;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.skill.CapabilitySkill;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class CommonModEvents {

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        EntityPatch<Entity> entitypatch = EpicFightCapabilities.getEntityPatch(event.getEntity(), EntityPatch.class);
        if (entitypatch != null && !entitypatch.isInitialized()) {
            if (entitypatch instanceof PlayerPatch<?> playerPatch) {
                CapabilitySkill skill = playerPatch.getSkillCapability();
                skill.skillContainers[ExtendedSkillSlots.HEAVY_ATTACK.universalOrdinal()].setSkill(SkillInit.HEAVY_ATTACK);
            }
        }
    }
}
