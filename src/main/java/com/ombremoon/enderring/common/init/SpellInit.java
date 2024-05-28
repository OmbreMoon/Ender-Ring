package com.ombremoon.enderring.common.init;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.magic.*;
import com.ombremoon.enderring.common.object.spell.incantation.twofingers.HealIncantation;
import com.ombremoon.enderring.common.object.spell.incantation.twofingers.UrgentHealIncantation;
import com.ombremoon.enderring.common.object.spell.sorcery.glintstone.GlintstonePebbleSorcery;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class SpellInit {
    public static final DeferredRegister<SpellType<?>> SPELL_TYPE = DeferredRegister.create(CommonClass.customLocation("spells"), Constants.MOD_ID);
    public static final Supplier<IForgeRegistry<SpellType<?>>> REGISTRY = SPELL_TYPE.makeRegistry(RegistryBuilder::new);

    //SPELLS
    public static final RegistryObject<SpellType<ProjectileSpell>> GLINTSTONE_PEBBLE = registerSpell("glintstone_pebble", GlintstonePebbleSorcery::new, GlintstonePebbleSorcery.createGlinstonePebbleBuilder());

    //INCANTATIONS
    public static final RegistryObject<SpellType<SimpleAnimationSpell>> HEAL = registerSpell("heal", HealIncantation::new, HealIncantation.createHealBuilder());
    public static final RegistryObject<SpellType<SimpleAnimationSpell>> URGENT_HEAL = registerSpell("urgent_heal", UrgentHealIncantation::new, UrgentHealIncantation.createUrgentHealBuilder());

    private static <T extends AbstractSpell, B extends AbstractSpell.Builder<T>> RegistryObject<SpellType<T>> registerSpell(String name, Function<B, T> constructor, B builder) {
        return SPELL_TYPE.register(name, () -> new SpellType<>(CommonClass.customLocation(name),() -> constructor.apply(builder)));
    }

    public static void register(IEventBus modEventBus) {
        SPELL_TYPE.register(modEventBus);
    }
}
