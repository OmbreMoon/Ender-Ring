package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.common.init.item.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class CrystalTearItem extends Item {
    private final Supplier<MobEffect> mobEffect;
    private final int duration;

    public CrystalTearItem(Supplier<MobEffect> mobEffect, int duration, Properties pProperties) {
        super(pProperties.stacksTo(1));
        this.mobEffect = mobEffect;
        this.duration = duration;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            if (pPlayer.getItemInHand(InteractionHand.OFF_HAND).is(ItemInit.WONDROUS_PHYSICK_FLASK.get())) {
                ItemStack stack = pPlayer.getItemInHand(InteractionHand.OFF_HAND);
                ListTag listTag = new ListTag();
                listTag.add(this.serializeNBT());
                stack.getOrCreateTag().put("Tears", listTag);
                stack.getTag().putInt("Charges", 1);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }

    public MobEffect getMobEffect() {
        return mobEffect.get();
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("TearEffect", ForgeRegistries.MOB_EFFECTS.getKey(this.mobEffect.get()).toString());
        compoundTag.putInt("TearDuration", this.duration);
        return compoundTag;
    }
}
