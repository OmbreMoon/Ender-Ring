package com.ombremoon.enderring.compat.epicfight.client.events;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.MobInit;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EFMClientModBusEvents {

    @SubscribeEvent
    public static void onPatchEntityRender(PatchedRenderersEvent.Add event) {
        event.addPatchedEntityRenderer(MobInit.TEST_DUMMY.get(), () -> new PHumanoidRenderer<>(Meshes.BIPED_OUTLAYER));
    }
}
