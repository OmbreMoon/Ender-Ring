package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatusEffect extends MobEffect {
    protected static final Logger LOGGER = Constants.LOG;
    @Nullable public Map<Integer, String> translationKeys = null;
    @Nullable private Map<String, Map<Integer, Double>> tiers;

    public StatusEffect(MobEffectCategory pCategory, int pColor, @Nullable Map<Integer, String> translations, Map<String, Map<Integer, Double>> tiers) {
        super(pCategory, pColor);
        this.translationKeys = translations;
        this.tiers = tiers;
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (this == StatusEffectInit.CRIMSON_CRYSTAL.get()) {
            pLivingEntity.heal(pLivingEntity.getMaxHealth() / 2);
        } else if (this == StatusEffectInit.CERULEAN_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseFP(player, (float) (player.getAttributeValue(EntityAttributeInit.MAX_FP.get()) / 2));
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

    public Double getTier(String uuid, int amp) {
        if (this.tiers == null) {
            LOGGER.warn("Attempted to use getTier on status effect with no tiers.");
            return null;
        } else if (this.tiers.get(uuid) == null) {
            LOGGER.warn("Attempted to use getTier on an invalid UUID");
            return null;
        }
        return this.tiers.get(uuid).get(amp);
    }

}
