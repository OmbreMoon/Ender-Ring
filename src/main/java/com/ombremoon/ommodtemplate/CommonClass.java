package com.ombremoon.ommodtemplate;

import com.ombremoon.ommodtemplate.common.init.BlockInit;
import com.ombremoon.ommodtemplate.common.init.EntityInit;
import com.ombremoon.ommodtemplate.common.init.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonClass {

    public static void init(IEventBus modEventBus) {
        ItemInit.register(modEventBus);
        BlockInit.register(modEventBus);
        EntityInit.register(modEventBus);
    }

    public static ResourceLocation customLocation(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }
}
