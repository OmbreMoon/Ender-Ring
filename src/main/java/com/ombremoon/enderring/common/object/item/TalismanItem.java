package com.ombremoon.enderring.common.object.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Supplier;

public class TalismanItem extends Item implements ICurioItem {
    private final Supplier<MobEffectInstance> effectInstance;

    public TalismanItem(Supplier<MobEffectInstance> effectInstance, Properties pProperties) {
        super(pProperties.stacksTo(1));
        this.effectInstance = effectInstance;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (slotContext.identifier().equalsIgnoreCase("talismans")) {
            slotContext.entity().addEffect(effectInstance.get());
        }
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        MobEffect mobEffect = this.getEffectInstance().getEffect();
        mobEffect.removeAttributeModifiers(livingEntity, livingEntity.getAttributes(), this.getEffectInstance().getAmplifier());
        livingEntity.removeEffect(mobEffect);
        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    public MobEffectInstance getEffectInstance() {
        return this.effectInstance.get();
    }
}
