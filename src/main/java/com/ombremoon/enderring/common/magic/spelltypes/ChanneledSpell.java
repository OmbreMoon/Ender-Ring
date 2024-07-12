package com.ombremoon.enderring.common.magic.spelltypes;

import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.magic.Classification;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.event.custom.EventFactory;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.function.Supplier;

public abstract class ChanneledSpell extends AnimatedSpell {
    protected int fpTickCost;

    public static Builder<ChanneledSpell> createChannelledSpellBuilder() {
        return new Builder<>();
    }

    public ChanneledSpell(SpellType<?> spellType, Builder<ChanneledSpell> builder) {
        super(spellType, builder);
        builder = EventFactory.getChanneledBuilder(spellType, builder);
        this.fpTickCost = builder.fpTickCost;
    }

    public int getFpTickCost() {
        return this.fpTickCost;
    }

    @Override
    protected void onSpellTick(LivingEntityPatch<?> livingEntityPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellTick(livingEntityPatch, level, blockPos, weapon);
        if (livingEntityPatch instanceof PlayerPatch<?> playerPatch) {
            if (!EntityStatusUtil.consumeFP(playerPatch.getOriginal(), this.fpTickCost, this, true) || !EntityStatusUtil.isChannelling(playerPatch.getOriginal())) {
                this.endSpell();
            }
        }
    }

    @Override
    protected void onSpellStop(LivingEntityPatch<?> livingEntityPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellStop(livingEntityPatch, level, blockPos, weapon);
        if (livingEntityPatch instanceof PlayerPatch<?> playerPatch)
            EntityStatusUtil.setChannelling((ServerPlayer) playerPatch.getOriginal(), false);
    }

    @Override
    public boolean requiresConcentration() {
        return true;
    }

    public static class Builder<T extends ChanneledSpell> extends AnimatedSpell.Builder<T> {
        protected int fpTickCost;

        public Builder() {
        }

        public Builder<T> setClassification(Classification classification) {
            this.classification = classification;
            return this;
        }

        public Builder<T> setFPCost(int fpCost) {
            this.fpCost = fpCost;
            return this;
        }

        public Builder<T> setFPTickCost(int fpTickCost) {
            this.fpTickCost = fpTickCost;
            return this;
        }

        public Builder<T> setStaminaCost(int staminaCost) {
            this.staminaCost = staminaCost;
            return this;
        }

        public Builder<T> setRequirements(WeaponScaling weaponScaling, int statReq) {
            this.createReqList(weaponScaling, statReq);
            return this;
        }

        public Builder<T> setMotionValue(float motionValue) {
            this.motionValue = motionValue;
            return this;
        }

        public Builder<T> setCastSound(SoundEvent castSound) {
            this.castSound = castSound;
            return this;
        }

        public Builder<T> setAnimation(Supplier<StaticAnimation> spellAnimation) {
            this.spellAnimation = spellAnimation;
            return this;
        }
    }
}
