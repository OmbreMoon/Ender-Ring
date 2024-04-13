package com.ombremoon.enderring.common.init;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.spell.sorcery.GlintstonePebbleSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SpellInit {
    public static final DeferredRegister<SpellType<?>> SPELL_TYPE = DeferredRegister.create(CommonClass.customLocation("spells"), Constants.MOD_ID);
    public static final Supplier<IForgeRegistry<SpellType<?>>> REGISTRY = SPELL_TYPE.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<SpellType<?>> GLINTSTONE_PEBBLE = registerSpell("glintstone_pebble", GlintstonePebbleSpell::new);

    private static <T extends AbstractSpell> RegistryObject<SpellType<?>> registerSpell(String name, Supplier<T> supplier) {
        return SPELL_TYPE.register(name, () -> new SpellType<>(CommonClass.customLocation(name), supplier));
    }

    public static void init(IEventBus modEventBus) {
        SPELL_TYPE.register(modEventBus);
    }
}
