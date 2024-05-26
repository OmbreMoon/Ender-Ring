package com.ombremoon.enderring.common.init;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SimpleSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.item.equipment.weapon.AbstractWeapon;
import com.ombremoon.enderring.common.object.spell.sorcery.GlintstonePebbleSpell;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.world.item.ItemStack;
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
    public static final RegistryObject<SpellType<?>> HEAL = registerSpell("heal", () -> new SimpleSpell(MagicType.SORCERY, 1, 32, 0, 12, 0, player -> {
        ItemStack itemStack = player.getMainHandItem();
        if (itemStack.getItem() instanceof AbstractWeapon abstractWeapon) {
            ScaledWeapon weapon = abstractWeapon.getModifiedWeapon(itemStack);
            player.heal(2.3F * DamageUtil.getIncantScaling(weapon, player, abstractWeapon.getWeaponLevel(itemStack)) / 15);
        }
    }));

    private static <T extends AbstractSpell> RegistryObject<SpellType<?>> registerSpell(String name, Supplier<T> supplier) {
        return SPELL_TYPE.register(name, () -> new SpellType<>(CommonClass.customLocation(name), supplier));
    }

    public static void register(IEventBus modEventBus) {
        SPELL_TYPE.register(modEventBus);
    }
}
