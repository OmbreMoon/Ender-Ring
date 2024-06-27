package com.ombremoon.enderring.common.object.item;

import com.ombremoon.enderring.util.CurioHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Supplier;

public class TalismanItem extends Item implements ICurioItem {
    private final Supplier<MobEffect> effect;

    public TalismanItem(Supplier<MobEffect> effect, Properties pProperties) {
        super(pProperties.stacksTo(1));
        this.effect = effect;
    }

    @Override
    public String getDescriptionId(ItemStack pStack) {
        int tier = getTier(pStack);
        if (tier == 0) return super.getDescriptionId(pStack);
        else return super.getDescriptionId(pStack) + "plus" + tier;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        LivingEntity livingEntity = slotContext.entity();
        if (!livingEntity.hasEffect(this.getEffect())) {
            int amp = getTier(stack);
            MobEffectInstance effectInstance = new MobEffectInstance(effect.get(), -1, amp,
                    false, false);
            
            slotContext.entity().addEffect(effectInstance);
            if (effectInstance.getEffect().isInstantenous()) {
                effectInstance.getEffect().applyInstantenousEffect(livingEntity, livingEntity, livingEntity, amp, 1.0D);
            }
        }
        ICurioItem.super.onEquip(slotContext, prevStack, stack);
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Player player = (Player) slotContext.entity();

        int slots = CurioHelper.getTalismanStacks(player).getSlots();
        for (int i = 0; i < slots; i++) {
            if (stack.getItem() == CurioHelper.getTalismanStacks(player).getStackInSlot(i).getItem()) {
                ICurioItem.super.onUnequip(slotContext, newStack, stack);
                return;
            }
        }

        MobEffect mobEffect = this.getEffect();
        mobEffect.removeAttributeModifiers(player, player.getAttributes(), getTier(stack));
        player.removeEffect(mobEffect);

        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    public MobEffect getEffect() {
        return this.effect.get();
    }

    public int getTier(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) return 0;
        return tag.getInt("tier");
    }
}
