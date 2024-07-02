package com.ombremoon.enderring.common.object.item;

import com.mojang.logging.LogUtils;
import com.ombremoon.enderring.common.object.world.effect.ModifiedAttributeEffect;
import com.ombremoon.enderring.util.CurioHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Attr;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import yesman.epicfight.api.animation.property.AnimationProperty;

import java.util.List;
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
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable(getDescriptionId().replace("item", "tooltip")));
        pTooltipComponents.add(CommonComponents.EMPTY);

        if (Screen.hasShiftDown() && this.effect != null && this.effect.get() instanceof ModifiedAttributeEffect statusEffect) {
            for (AttributeModifier modifer : statusEffect.getAttributeMap().keySet()) {
                AttributeModifier.Operation operation = modifer.getOperation();
                double amount = modifer.getAmount();
                for (Supplier<Attribute> attribute : statusEffect.getAttributeMap().get(modifer)) {
                    String descId = attribute.get().getDescriptionId();
                    String tooltip = getTooltip(operation, amount);

                    pTooltipComponents.add(
                            Component.literal(tooltip).append(CommonComponents.SPACE)
                                    .append(Component.translatable(descId))
                                    .withStyle(ChatFormatting.BLUE)
                    );
                }
            }
        } else if (!Screen.hasShiftDown() && this.effect != null &&this.effect.get() instanceof ModifiedAttributeEffect) {
            pTooltipComponents.add(Component.translatable("tooltip.enderring.more_info")
                    .withStyle(ChatFormatting.GRAY));
        }
    }

    private static @NotNull String getTooltip(AttributeModifier.Operation operation, double amount) {
        String tooltip = "";
        if (operation == AttributeModifier.Operation.ADDITION) {
            if (amount >= 0) tooltip += "+" + (int) amount;
            else tooltip += amount;
        } else if (operation == AttributeModifier.Operation.MULTIPLY_TOTAL
                || operation == AttributeModifier.Operation.MULTIPLY_BASE) {
            if (amount >= 0) tooltip += "+" + (int) Math.round(amount*100) + "%";
            else tooltip += (int) Math.round(amount*100) + "%";
        }
        return tooltip;
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        if (this.getEffect() == null) {
            ICurioItem.super.onEquip(slotContext, prevStack, stack);
            return;
        }
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

        if (this.getEffect() == null || CurioHelper.hasTalisman(player, this)) {
            ICurioItem.super.onUnequip(slotContext, newStack, stack);
            return;
        }

        MobEffect mobEffect = this.getEffect();
        mobEffect.removeAttributeModifiers(player, player.getAttributes(), getTier(stack));
        player.removeEffect(mobEffect);

        ICurioItem.super.onUnequip(slotContext, newStack, stack);
    }

    public MobEffect getEffect() {
        return this.effect != null ? this.effect.get() : null;
    }

    public int getTier(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) return 0;
        return tag.getInt("tier");
    }
}
