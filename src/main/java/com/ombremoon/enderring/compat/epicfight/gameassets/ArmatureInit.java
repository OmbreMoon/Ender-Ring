package com.ombremoon.enderring.compat.epicfight.gameassets;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.MobInit;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.ModelBuildEvent;
import yesman.epicfight.gameasset.Armatures;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArmatureInit {

    @SubscribeEvent
    public static void onModelBuild(ModelBuildEvent.ArmatureBuild event) {
        Armatures.registerEntityTypeArmature(MobInit.TEST_DUMMY.get(), Armatures.BIPED);
    }
}
