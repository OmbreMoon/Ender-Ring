package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.event.FirstSpawnEvent;
import com.ombremoon.enderring.network.ModNetworking;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
            }
            if (pPlayer.isInWater()) {
                DamageSource damageSource = new DamageSource(pPlayer.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ModDamageTypes.PHYSICAL));
                pPlayer.hurt(damageSource, 20.0F);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }
}