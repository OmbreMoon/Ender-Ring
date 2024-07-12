package com.ombremoon.enderring.common.object.world.effect.buildup;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.common.object.world.effect.StatusEffect;
import com.ombremoon.enderring.common.object.world.effect.stacking.EffectType;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.function.BiFunction;

public class BuildUpStatusEffect extends StatusEffect {
    protected ScaledWeapon scaledWeapon;
    protected SpellType<?> spellType;

    public BuildUpStatusEffect(int pColor) {
        super(EffectType.BUILD_UP, pColor, null, null, (a, b) -> true, MobEffectCategory.HARMFUL);
    }

    public BuildUpStatusEffect(BiFunction<Integer, Integer, Boolean> applyTick, int pColor) {
        super(EffectType.BUILD_UP, pColor, null, null, applyTick, MobEffectCategory.HARMFUL);
    }

    public void applyInstantaneousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, LivingEntity pLivingEntity) {
        if (this == StatusEffectInit.SLEEP.get()) {
            float time = 60.0F;
            if (pLivingEntity instanceof Player player) {
                time = 1.0F;
                EntityStatusUtil.consumeFP(player, (float) (EntityStatusUtil.getMaxFP(player) * 0.1F + 30.0F), null, true);
            }

            this.stunEntity(pLivingEntity, time);
        } else if (this == StatusEffectInit.MADNESS.get()) {
            pLivingEntity.hurt(DamageUtil.moddedDamageSource(pLivingEntity.level(), ModDamageTypes.MADNESS), pLivingEntity.getMaxHealth() * 0.15F + 6.7F);
            if (pLivingEntity instanceof Player player) {
                EntityStatusUtil.consumeFP(player, EntityStatusUtil.getMaxFP(player) * 0.1F + 30.0F, null, true);
            }
            Constants.LOG.info(String.valueOf(this.scaledWeapon.serializeNBT()));

            this.stunEntity(pLivingEntity, 0.83F);
        } else if (this == StatusEffectInit.DEATH_BLIGHT.get()) {
            pLivingEntity.hurt(DamageUtil.moddedDamageSource(pLivingEntity.level(), ModDamageTypes.DEATH_BLIGHT), Float.MAX_VALUE);
        }
    }

    @Override
    public boolean isInstantenous() {
        return this == StatusEffectInit.BLOOD_LOSS.get() || this == StatusEffectInit.FROSTBITE.get() || this == StatusEffectInit.SLEEP.get() || this == StatusEffectInit.MADNESS.get() || this == StatusEffectInit.DEATH_BLIGHT.get();
    }

    protected void stunEntity(LivingEntity livingEntity, float time) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(livingEntity, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            livingEntityPatch.applyStun(StunType.SHORT, time);
        }
    }

    public BuildUpStatusEffect setScaledWeapon(ScaledWeapon scaledWeapon) {
        this.scaledWeapon = scaledWeapon;
        return this;
    }

    public BuildUpStatusEffect setSpellType(SpellType<?> spellType) {
        this.spellType = spellType;
        return this;
    }
}
