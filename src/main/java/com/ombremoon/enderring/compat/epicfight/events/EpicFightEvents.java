package com.ombremoon.enderring.compat.epicfight.events;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.MobInit;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.entitypatch.patches.TestDummyPatch;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapabilityPresets;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.UpdatePlayerMotionEvent;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;

public class EpicFightEvents {

    @Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void onWeaponCapabilityRegister(WeaponCapabilityPresetRegistryEvent event) {
            event.getTypeEntry().put(CommonClass.customLocation("test"), ExtendedWeaponCapabilityPresets.STRAIGHT_SWORD);
            event.getTypeEntry().put(CommonClass.customLocation("greatsword"), ExtendedWeaponCapabilityPresets.GREATSWORD);
        }

        @SubscribeEvent
        public static void onEntityPatchRegister(EntityPatchRegistryEvent event) {
            event.getTypeEntry().put(MobInit.TEST_DUMMY.get(), (entity) -> TestDummyPatch::new);
//        event.getTypeEntry().put(MobInit.HEWG.get(), (entity) -> SimpleHumanoidMobPatch::new);
        }
    }


    @Mod.EventBusSubscriber(modid = Constants.MOD_ID)
    public static class ModEvents {

        @SubscribeEvent
        public static void onPlayerUpdateMotion(UpdatePlayerMotionEvent.CompositeLayer event) {
            //THIS IS WHERE WE WILL REGISTER CHARGED AND CHANNELED SPELL LIVING MOTIONS
            //CHECK IF PLAYER HELD/USED ITEM IS A CATALYST AND WHETHER THE PLAYER IS CHARGED OR CHANNELLING
            //CHANGE COMPOSITE (LAYER ON TOP OF BASE LAYER) LIVING MOTION TO SPELL MOTION
        }
    }
}
