package com.ombremoon.enderring.common.object.item.equipment;

import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.item.ItemInit;
import com.ombremoon.enderring.util.CurioHelper;
import com.ombremoon.enderring.util.FlaskUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.Objects;

public class FlaskItem extends Item implements ICurioItem, IQuickAccess {
    private final Type type;
    public static final String NO_TEARS = "enderring.item.flask.error";

    public FlaskItem(Type type, Properties pProperties) {
        super(pProperties.stacksTo(1));
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
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand);
    }

/*    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
    }*/

    private void applyFlaskEffects(ListTag listTag, LivingEntity livingEntity) {
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag nbt = listTag.getCompound(i);
            MobEffect mobEffect = ForgeRegistries.MOB_EFFECTS.getValue(FlaskUtil.getEffectId(nbt));
            if (mobEffect != null) {
                livingEntity.addEffect(new MobEffectInstance(mobEffect, nbt.getInt("TearDuration")));
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
        if (!pLevel.isClientSide) {
            int charges = FlaskUtil.getCharges(pStack);
            if (charges > 0) {
                switch (this.type) {
                    case HP, FP -> {
                        int flaskLevel = FlaskUtil.getFlaskLevel(pStack);
                        IDynamicStackHandler stackHandler = CurioHelper.getTalismanStacks((Player) pLivingEntity);
                        boolean crimsonTaliFlag = false;
                        boolean ceruleanTaliFlag = false;
                        for (int i = 0; i < stackHandler.getSlots(); i++) {
                            ItemStack stack = stackHandler.getStackInSlot(i);
                            if (stack.is(ItemInit.CRIMSON_SEED_TALISMAN.get())) crimsonTaliFlag = true;
                            else if (stack.is(ItemInit.CERULEAN_SEED_TALISMAN.get())) ceruleanTaliFlag = true;
                        }
                        float f;
                        if (this.type == Type.HP) {
                            f = flaskLevel <= 6 ? (250 + flaskLevel * 95 - (10 * ((float) (flaskLevel * (flaskLevel - 1)) / 2))) : (float) ((670 + 23.333 * flaskLevel));
                            if (crimsonTaliFlag) f *= 1.2f;
                            pLivingEntity.heal(f / ConfigHandler.STAT_SCALE.get());
                        } else {
                            f = flaskLevel <= 4 ? 80 + (15 * flaskLevel) : 150 + ((flaskLevel - 5) * 10);
                            if (ceruleanTaliFlag) f *= 1.2f;
                            if (pLivingEntity instanceof Player player)
                                EntityStatusUtil.increaseFP(player, f);
                        }
                    }
                    default -> {
                        ListTag listTag = FlaskUtil.getCrystalTears(pStack);
                        if (!listTag.isEmpty()) {
                            this.applyFlaskEffects(listTag, pLivingEntity);
                        } else {
                            ((Player)pLivingEntity).displayClientMessage(Component.translatable("enderring.item.flask.error"), true);
                        }
                    }
                }

                if (pLivingEntity instanceof Player player && !player.getAbilities().instabuild) {
                    pStack.getTag().putInt("Charges", Math.max(charges - 1, 0));
                }
            }

            if (pLivingEntity instanceof Player player) {
                ItemCooldowns cooldowns = player.getCooldowns();
                if (!cooldowns.isOnCooldown(this) && charges <= 0) {
                    cooldowns.addCooldown(this, 168000);
                }

                player.awardStat(Stats.ITEM_USED.get(this));
            }
        }
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
