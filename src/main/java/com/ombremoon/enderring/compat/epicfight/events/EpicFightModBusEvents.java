package com.ombremoon.enderring.compat.epicfight.events;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.MobInit;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.patches.TestDummyPatch;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapabilityPresets;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EpicFightModBusEvents {

    @SubscribeEvent
    public static void onWeaponCapabilityRegister(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(CommonClass.customLocation("test"), ExtendedWeaponCapabilityPresets.STRAIGHT_SWORD);
    }

    @SubscribeEvent
    public static void onEntityPatchRegister(EntityPatchRegistryEvent event) {
        event.getTypeEntry().put(MobInit.TEST_DUMMY.get(), (entity) -> TestDummyPatch::new);
//        event.getTypeEntry().put(MobInit.HEWG.get(), (entity) -> SimpleHumanoidMobPatch::new);
    }
}
