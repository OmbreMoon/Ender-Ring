package com.ombremoon.enderring.common.object.world.inventory;

import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GraceSiteProvider implements MenuProvider {

    @Override
    public @NotNull Component getDisplayName() {
        Player player = Minecraft.getInstance().player;
        return PlayerStatusUtil.getGraceSiteFlag(player) ? Component.translatable("container.enderring.wondrous_physick") : Component.translatable("container.enderring.memorize_spell");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return PlayerStatusUtil.getGraceSiteFlag(pPlayer) ? new WondrousPhysickMenu(pContainerId, pPlayerInventory) : new MemorizeSpellMenu(pContainerId, pPlayerInventory);
    }
}
