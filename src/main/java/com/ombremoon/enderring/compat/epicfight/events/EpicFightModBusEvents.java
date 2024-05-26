package com.ombremoon.enderring.compat.epicfight.events;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapabilityPresets;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EpicFightModBusEvents {

    @SubscribeEvent
    public static void onWeaponCapabilityRegister(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(CommonClass.customLocation("test"), ExtendedWeaponCapabilityPresets.STRAIGHT_SWORD);
    }
}
