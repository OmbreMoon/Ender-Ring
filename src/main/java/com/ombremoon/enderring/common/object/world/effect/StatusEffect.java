package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class StatusEffect extends MobEffect {

    public StatusEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (this == StatusEffectInit.CRIMSON_CRYSTAL.get()) {
            pLivingEntity.heal(pLivingEntity.getMaxHealth() / 2);
        } else if (this == StatusEffectInit.CERULEAN_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            PlayerStatusUtil.increaseFP(player, (float) (player.getAttributeValue(EntityAttributeInit.MAX_FP.get()) / 2));
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return new ArrayList<>();
    }

}
