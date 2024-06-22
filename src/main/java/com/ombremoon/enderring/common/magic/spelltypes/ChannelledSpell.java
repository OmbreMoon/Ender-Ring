package com.ombremoon.enderring.common.magic.spelltypes;

import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import net.minecraft.sounds.SoundEvent;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.function.Supplier;

public abstract class ChannelledSpell extends AnimatedSpell {
    protected int fpTickCost;

    public static Builder createChannelledSpellBuilder() {
        return new Builder<>().setDuration(-1);
    }
    public ChannelledSpell(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
        this.fpTickCost = builder.fpTickCost;
    }

    public int getFpTickCost() {
        return this.fpTickCost;
    }

    @Override
    public boolean canCharge() {
        return true;
    }

    public static class Builder<T extends ChannelledSpell> extends AnimatedSpell.Builder<T> {
        protected int fpTickCost;

        public Builder() {
        }

        public Builder<T> setMagicType(MagicType magicType) {
            this.magicType = magicType;
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

        public Builder<T> setDuration(int duration) {
            this.duration = duration;
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

        public Builder<T> setChargedMotionValue(float motionValue) {
            this.chargedMotionValue = motionValue;
            return this;
        }

        public Builder<T> setCanCharge(boolean canCharge) {
            this.canCharge = canCharge;
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
