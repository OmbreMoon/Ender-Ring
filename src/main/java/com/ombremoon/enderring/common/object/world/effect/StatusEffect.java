package com.ombremoon.enderring.common.object.world.effect;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.capability.EntityStatus;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.world.effect.stacking.EffectType;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.*;

public class StatusEffect extends MobEffect {
    protected static final Logger LOGGER = Constants.LOG;
    @Nullable public final Map<Integer, String> translationKeys;
    @Nullable private final Map<String, Map<Integer, Double>> tiers;
    @Nullable private List<Supplier<? extends MobEffect>> buildUps;
    private final BiFunction<Integer, Integer, Boolean> applyTick;
    private final boolean instant;
    private final EffectType type;

    public StatusEffect(EffectType type, int pColor, @Nullable Map<Integer, String> translations, Map<String, Map<Integer, Double>> tiers, BiFunction<Integer, Integer, Boolean> applyTick, MobEffectCategory category) {
        super(category, pColor);
        this.translationKeys = translations;
        this.tiers = tiers;
        this.type = type;
        if (applyTick == null) {
            this.applyTick = (a, b) -> false;
            this.instant = true;
        } else {
            this.applyTick = applyTick;
            this.instant = false;
        }
        this.buildUps = List.of(StatusEffectInit.POISON,StatusEffectInit.SCARLET_ROT,
                StatusEffectInit.BLOOD_LOSS
        ,StatusEffectInit.FROSTBITE,StatusEffectInit.SLEEP,StatusEffectInit.MADNESS,
                StatusEffectInit.DEATH_BLIGHT);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (this == StatusEffectInit.CRIMSON_CRYSTAL.get()) {
            pLivingEntity.heal(pLivingEntity.getMaxHealth() / 2);
        } else if (this == StatusEffectInit.CERULEAN_CRYSTAL.get() && pLivingEntity instanceof Player player) {
            EntityStatusUtil.increaseFP(player, (float) (player.getAttributeValue(EntityAttributeInit.MAX_FP.get()) / 2));
        } else if (this == StatusEffectInit.BLESSED_DEW_TALISMAN.get()) {
            pLivingEntity.heal(0.13F);
        }
        else if(this == StatusEffectInit.CRIMSONBURST_CRYSTAL.get())
        {
            pLivingEntity.heal(0.15f);
        }
        else if(this == StatusEffectInit.GREENBURST_CRYSTAL.get())
        {
            /** I too dont know what i did, was drunk
             *
            AttributeModifier modifier = new AttributeModifier(
                    "GREENBURST_CRYSTAL",
                    1F, AttributeModifier.Operation.MULTIPLY_TOTAL

            );
            Objects.requireNonNull(pLivingEntity
                    .getAttribute(EpicFightAttributes.STAMINA_REGEN.get())).addTransientModifier(modifier);
            **/
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return applyTick.apply(pDuration, pAmplifier);
    }

    @Override
    public boolean isInstantenous() {
        return this.instant;
    }

    public EffectType getEffectType() {
        return this.type;
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
