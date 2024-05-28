package com.ombremoon.enderring.event;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.MobInit;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
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
                EntityAttributeInit.MAX_FP,
                EntityAttributeInit.VIGOR,
                EntityAttributeInit.MIND,
                EntityAttributeInit.ENDURANCE,
                EntityAttributeInit.STRENGTH,
                EntityAttributeInit.DEXTERITY,
                EntityAttributeInit.INTELLIGENCE,
                EntityAttributeInit.FAITH,
                EntityAttributeInit.ARCANE,
                EntityAttributeInit.ROBUSTNESS,
                EntityAttributeInit.PHYS_DAMAGE,
                EntityAttributeInit.MAGIC_DAMAGE,
                EntityAttributeInit.FIRE_DAMAGE,
                EntityAttributeInit.LIGHT_DAMAGE,
                EntityAttributeInit.HOLY_DAMAGE,
                EntityAttributeInit.PHYS_NEGATE,
                EntityAttributeInit.MAGIC_NEGATE,
                EntityAttributeInit.FIRE_NEGATE,
                EntityAttributeInit.LIGHT_NEGATE,
                EntityAttributeInit.HOLY_NEGATE);
    }

    @SafeVarargs
    private static void addAttributesToPlayer(EntityAttributeModificationEvent event, Supplier<Attribute>... attributes) {
        Arrays.stream(attributes).map(Supplier::get).forEach(attribute -> event.add(EntityType.PLAYER, attribute));
    }

    @SubscribeEvent
    public static void onEntityAttributeRegister(EntityAttributeCreationEvent e) {
        MobInit.attributeSuppliers.forEach(p -> e.put(p.entityTypeSupplier().get(), p.attributeSupplier().get().build()));
    }
}
