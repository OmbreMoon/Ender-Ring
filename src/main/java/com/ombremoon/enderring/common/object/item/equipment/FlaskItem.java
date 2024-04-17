package com.ombremoon.enderring.common.object.item.equipment;

import com.ombremoon.enderring.util.FlaskUtil;
import com.ombremoon.enderring.util.PlayerStatusUtil;
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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

//TODO: RESET CHARGE ON RESPAWN
//TODO: ADD HOVER TEXT FOR CRYSTAL TEARS

public class FlaskItem extends QuickAccessItem {
    private final Type type;
    public static final String NO_TEARS = "enderring.item.flask.error";
    private static final int SCALE_FACTOR = 15;

    public FlaskItem(Type type, Properties pProperties) {
        super(pProperties.stacksTo(1), true);
        this.type = type;
    }

    @Override
    public void useItem(Player player, Level level, InteractionHand usedHand, ItemStack itemStack) {
        if (!level.isClientSide) {
            if (this.getCharges(itemStack) > 0) {
                switch (this.type) {
                    case HP, FP -> {
                        int flaskLevel = itemStack.getOrCreateTag().getInt("FlaskLevel");
                        float f;
                        if (this.type == Type.HP) {
                            f = flaskLevel <= 6 ? (250 + flaskLevel * 95 - (10 * ((float) (flaskLevel * (flaskLevel - 1)) / 2))) / SCALE_FACTOR : (float) ((670 + 23.333 * flaskLevel) / SCALE_FACTOR);
                            player.heal(f);
                        } else {
                            f = flaskLevel <= 4 ? 80 + (15 * flaskLevel) : 150 + ((flaskLevel - 5) * 10);
                            PlayerStatusUtil.increaseFP(player, f);
                        }
                    }
                    default -> {
                        ListTag listTag = itemStack.getOrCreateTag().getList("Tears", 10);
                        if (!listTag.isEmpty()) {
                            this.applyFlaskEffects(listTag, player);
                        } else {
                            player.displayClientMessage(Component.translatable("enderring.item.flask.error"), true);
                        }
                    }
                }

                if (!player.getAbilities().instabuild) {
                    itemStack.getTag().putInt("Charges", Math.max(this.getCharges(itemStack) - 1, 0));
                }
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        if (player != null) {
            ItemCooldowns cooldowns = player.getCooldowns();
            if (!cooldowns.isOnCooldown(this) && this.getCharges(itemStack) <= 0) {
                cooldowns.addCooldown(this, 12000);
            }
        }
    }

    private int getCharges(ItemStack itemStack) {
        return itemStack.getOrCreateTag().getInt("Charges");
    }

    private int getMaxCharges(ItemStack itemStack, Type type) {
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        if (type != Type.PHYSICK) {
            return 2 + compoundTag.getInt("FlaskLevel");
        }
        return 1;
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
