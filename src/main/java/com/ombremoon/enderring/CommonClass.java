package com.ombremoon.enderring;

import com.ombremoon.enderring.client.ModelLocations;
import com.ombremoon.enderring.common.init.BlockInit;
import com.ombremoon.enderring.common.init.LootModifiersInit;
import com.ombremoon.enderring.common.init.MenuTypeInit;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.EntityInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonClass {

    public static void init(IEventBus modEventBus) {
        EntityAttributeInit.register(modEventBus);
        StatusEffectInit.register(modEventBus);
        ItemInit.register(modEventBus);
        BlockInit.register(modEventBus);
        EntityInit.register(modEventBus);
        MenuTypeInit.register(modEventBus);
        LootModifiersInit.register(modEventBus);
        SpellInit.init(modEventBus);
    }

    public static ResourceLocation customLocation(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }
}
