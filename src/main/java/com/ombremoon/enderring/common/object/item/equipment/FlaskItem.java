package com.ombremoon.enderring.common.object.item.equipment;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.util.FlaskUtil;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Objects;

public class FlaskItem extends QuickAccessItem implements ICurioItem {
    private final Type type;
    public static final String NO_TEARS = "enderring.item.flask.error";
    private static final int SCALE_FACTOR = 15;

    public FlaskItem(Type type, Properties pProperties) {
        super(pProperties.stacksTo(1), true);
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (this.type != Type.PHYSICK) {
            pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".flask.potency").append(": +").append(String.valueOf(FlaskUtil.getFlaskLevel(pStack))).withStyle(ChatFormatting.DARK_RED));
        } else {
            pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".flask.tear_1").append(": ").append(getCrystalTear(pStack, 0)).withStyle(ChatFormatting.AQUA));
            pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".flask.tear_2").append(": ").append(getCrystalTear(pStack, 1)).withStyle(ChatFormatting.LIGHT_PURPLE));
            if (Screen.hasShiftDown()) {

            }
        }
        pTooltipComponents.add(Component.translatable(Constants.MOD_ID + ".flask.charges").append(": ").append(FlaskUtil.getCharges(pStack) + "/" + FlaskUtil.getMaxCharges(pStack, this.getType())).withStyle(ChatFormatting.YELLOW));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void useItem(Player player, Level level, InteractionHand usedHand, ItemStack itemStack) {
        if (!level.isClientSide) {
            int charges = FlaskUtil.getCharges(itemStack);
            if (charges > 0) {
                switch (this.type) {
                    case HP, FP -> {
                        int flaskLevel = FlaskUtil.getFlaskLevel(itemStack);
                        float f;
                        if (this.type == Type.HP) {
                            f = flaskLevel <= 6 ? (250 + flaskLevel * 95 - (10 * ((float) (flaskLevel * (flaskLevel - 1)) / 2))) : (float) ((670 + 23.333 * flaskLevel));
                            player.heal(f / SCALE_FACTOR);
                        } else {
                            f = flaskLevel <= 4 ? 80 + (15 * flaskLevel) : 150 + ((flaskLevel - 5) * 10);
                            PlayerStatusUtil.increaseFP(player, f);
                        }
                    }
                    default -> {
                        ListTag listTag = FlaskUtil.getCrystalTears(itemStack);
                        if (!listTag.isEmpty()) {
                            this.applyFlaskEffects(listTag, player);
                        } else {
                            player.displayClientMessage(Component.translatable("enderring.item.flask.error"), true);
                        }
                    }
                }

                if (!player.getAbilities().instabuild) {
                    itemStack.getTag().putInt("Charges", Math.max(charges - 1, 0));
                }
            }

            if (player != null) {
                ItemCooldowns cooldowns = player.getCooldowns();
                if (!cooldowns.isOnCooldown(this) && charges <= 0) {
                    cooldowns.addCooldown(this, 168000);
                }
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
    }

    private void applyFlaskEffects(ListTag listTag, Player player) {
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag nbt = listTag.getCompound(i);
            MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(FlaskUtil.getEffectId(nbt));
            if (mobEffect != null) {
                player.addEffect(new MobEffectInstance(mobEffect, nbt.getInt("TearDuration")));
            }
        }
    }

    private Component getCrystalTear(ItemStack itemStack, int index) {
        ListTag listTag = FlaskUtil.getCrystalTears(itemStack);
        if (listTag.size() < index + 1) {
            return Component.translatable(Constants.MOD_ID + ".flask.empty_tear");
        } else {
            return Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(FlaskUtil.getCrystalTear(listTag.getCompound(index)))).getName(itemStack);
        }
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        this.useItem((Player) pLivingEntity, pLevel, pLivingEntity.getUsedItemHand(), pStack);
        return pStack;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    public Type getType() {
        return this.type;
    }

    public enum Type {
        HP,
        FP,
        PHYSICK
    }
}
