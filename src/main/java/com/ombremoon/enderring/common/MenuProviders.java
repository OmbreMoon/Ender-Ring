package com.ombremoon.enderring.common;

import com.ombremoon.enderring.common.object.world.inventory.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

public class MenuProviders {
    public static final MenuProvider WONDROUS_PHYSICK = new MenuProvider() {
        @Override
        public Component getDisplayName() {
            return Component.translatable("container.enderring.wondrous_physick_menu");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
            return new WondrousPhysickMenu(pContainerId, pPlayerInventory);
        }
    };

    public static final MenuProvider MEMORIZE_SPELL = new MenuProvider() {
        @Override
        public Component getDisplayName() {
            return Component.translatable("container.enderring.memorize_spell_menu");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
            return new MemorizeSpellMenu(pContainerId, pPlayerInventory);
        }
    };

    public static final MenuProvider SACRED_TEAR = new MenuProvider() {
        @Override
        public Component getDisplayName() {
            return Component.translatable("container.enderring.sacred_tear_menu");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
            return new SacredTearMenu(pContainerId, pPlayerInventory, pPlayer);
        }
    };

    public static final MenuProvider GOLDEN_SEED = new MenuProvider() {
        @Override
        public Component getDisplayName() {
            return Component.translatable("container.enderring.golden_seed_menu");
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
            return new GoldenSeedMenu(pContainerId, pPlayerInventory, pPlayer);
        }
    };
}
