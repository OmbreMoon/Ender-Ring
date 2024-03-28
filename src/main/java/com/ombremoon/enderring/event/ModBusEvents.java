package com.ombremoon.enderring.event;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents {

    @SubscribeEvent
    public static void onPlayerAttributeModification(EntityAttributeModificationEvent event) {
        addAttributesToPlayer(event,
                EntityAttributeInit.RUNE_LEVEL,
                EntityAttributeInit.RUNES_HELD,
                EntityAttributeInit.VIGOR,
                EntityAttributeInit.MIND,
                EntityAttributeInit.STRENGTH,
                EntityAttributeInit.DEXTERITY,
                EntityAttributeInit.INTELLIGENCE,
                EntityAttributeInit.FAITH,
                EntityAttributeInit.ARCANE);
    }

    @SafeVarargs
    private static void addAttributesToPlayer(EntityAttributeModificationEvent event, Supplier<Attribute>... attributes) {
        Arrays.stream(attributes).map(Supplier::get).forEach(attribute -> event.add(EntityType.PLAYER, attribute));
    }
}
