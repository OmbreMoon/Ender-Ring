package com.ombremoon.enderring.common.init;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.world.inventory.MemorizeSpellMenu;
import com.ombremoon.enderring.common.object.world.inventory.WondrousPhysickMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Constants.MOD_ID);

    public static final RegistryObject<MenuType<WondrousPhysickMenu>> WONDROUS_PHYSICK_MENU = registerMenuType(WondrousPhysickMenu::new, "wondrous_physick_menu");
    public static final RegistryObject<MenuType<MemorizeSpellMenu>> MEMORIZE_SPELL_MENU = registerMenuType(MemorizeSpellMenu::new, "memorize_spell_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENU_TYPES.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}
