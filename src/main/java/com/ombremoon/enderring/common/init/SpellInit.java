package com.ombremoon.enderring.common.init;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.AnimatedSpell;
import com.ombremoon.enderring.common.magic.spelltypes.ProjectileSpell;
import com.ombremoon.enderring.common.object.entity.projectile.spell.GlintstoneArcEntity;
import com.ombremoon.enderring.common.object.entity.projectile.spell.GlintstonePebbleEntity;
import com.ombremoon.enderring.common.object.spell.incantation.firemonk.CatchFlameIncantation;
import com.ombremoon.enderring.common.object.spell.sorcery.glintstone.CometAzurSorcery;
import com.ombremoon.enderring.common.object.spell.sorcery.glintstone.GlintstoneArcSorcery;
import com.ombremoon.enderring.common.object.spell.sorcery.glintstone.GlintstonePebbleSorcery;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SpellInit {
    public static final DeferredRegister<SpellType<?>> SPELL_TYPE = DeferredRegister.create(CommonClass.customLocation("spells"), Constants.MOD_ID);
    public static final Supplier<IForgeRegistry<SpellType<?>>> REGISTRY = SPELL_TYPE.makeRegistry(RegistryBuilder::new);

    //SPELLS
    public static final RegistryObject<SpellType<GlintstonePebbleSorcery>> GLINTSTONE_PEBBLE = registerSpell("glintstone_pebble", GlintstonePebbleSorcery::new);
    public static final RegistryObject<SpellType<GlintstoneArcSorcery>> GLINTSTONE_ARC = registerSpell("glintstone_arc", GlintstoneArcSorcery::new);
    public static final RegistryObject<SpellType<CometAzurSorcery>> COMET_AZUR = registerSpell("comet_azur", CometAzurSorcery::new);

    //INCANTATIONS
    public static final RegistryObject<SpellType<AnimatedSpell>> CATCH_FLAME = registerSpell("catch_flame", CatchFlameIncantation::new);
//    public static final RegistryObject<SpellType<SimpleAnimationSpell>> HEAL = registerSpell("heal", HealIncantation::new, HealIncantation.createHealBuilder());
//    public static final RegistryObject<SpellType<SimpleAnimationSpell>> URGENT_HEAL = registerSpell("urgent_heal", UrgentHealIncantation::new, UrgentHealIncantation.createUrgentHealBuilder());

    private static <T extends AbstractSpell> RegistryObject<SpellType<T>> registerSpell(String name, SpellType.SpellFactory<T> factory) {
        return SPELL_TYPE.register(name, () -> new SpellType<>(CommonClass.customLocation(name), factory));
    }

    public static void register(IEventBus modEventBus) {
        SPELL_TYPE.register(modEventBus);
    }
}
