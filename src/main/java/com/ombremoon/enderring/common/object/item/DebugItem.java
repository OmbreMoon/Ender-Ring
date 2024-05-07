package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.SpellInit;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.event.FirstSpawnEvent;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.FlaskUtil;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class DebugItem extends Item {

    public DebugItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            if (pPlayer.isCrouching()) {
                ModNetworking.getInstance().openCharBaseSelectScreen(FirstSpawnEvent.CHARACTER_BASE, (ServerPlayer) pPlayer);
                PlayerStatusUtil.setSelectedSpell(pPlayer, SpellInit.GLINTSTONE_PEBBLE.get());
            } else {
                ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(pPlayer, ServerPlayerPatch.class);
//                ModNetworking.getInstance().syncOverlays((ServerPlayer) pPlayer);
//                ModNetworking.getInstance().openGraceSiteScreen(Component.literal("Grace"),(ServerPlayer) pPlayer);
                Constants.LOG.info(String.valueOf(pPlayer.getAttributeValue(EntityAttributeInit.VIGOR.get())));
            }
            FlaskUtil.resetFlaskCooldowns(pPlayer);
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }
}