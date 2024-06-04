package com.ombremoon.enderring.compat.epicfight.util;

import com.ombremoon.enderring.common.object.entity.ERMob;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.ERMobPatch;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class EFMCapabilityUtil {

    public static ExtendedWeaponCapability getWeaponCapability(PlayerPatch<?> playerPatch) {
        CapabilityItem item = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
        return item instanceof ExtendedWeaponCapability weaponCapability ? weaponCapability : null;
    }
}
