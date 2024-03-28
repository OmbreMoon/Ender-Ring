package com.ombremoon.enderring;

import com.ombremoon.enderring.common.init.BlockInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.EntityInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonClass {

    public static void init(IEventBus modEventBus) {
        ItemInit.register(modEventBus);
        BlockInit.register(modEventBus);
        EntityInit.register(modEventBus);
        EntityAttributeInit.register(modEventBus);
        StatusEffectInit.register(modEventBus);
    }

    public static ResourceLocation customLocation(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }
}
