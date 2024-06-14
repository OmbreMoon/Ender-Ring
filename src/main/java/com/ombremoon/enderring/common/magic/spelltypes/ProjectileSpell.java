package com.ombremoon.enderring.common.magic.spelltypes;

import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.function.Supplier;

public abstract class ProjectileSpell extends AnimatedSpell {

    public static Builder createProjectileBuilder() {
        return new Builder();
    }

    public ProjectileSpell(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
    }

    @Override
    protected boolean shouldTickEffect(int duration) {
        return this.isInstantSpell() ? duration == 1 : super.shouldTickEffect(duration);
    }

    public static class Builder<T extends ProjectileSpell> extends AnimatedSpell.Builder<T> {

        public Builder() {
        }

        public Builder setMagicType(MagicType magicType) {
            this.magicType = magicType;
            return this;
        }

        public Builder setRequirements(WeaponScaling weaponScaling, int statReq) {
            this.createReqList(weaponScaling, statReq);
            return this;
        }

        public Builder setFPCost(int fpCost) {
            this.fpCost = fpCost;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setMotionValue(int motionValue) {
            this.motionValue = motionValue;
            return this;
        }

        public Builder setAnimation(Supplier<StaticAnimation> animation) {
            this.spellAnimation = animation;
            return this;
        }
    }
}
