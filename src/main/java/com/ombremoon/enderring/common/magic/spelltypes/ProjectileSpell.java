package com.ombremoon.enderring.common.magic.spelltypes;

import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;

public abstract class ProjectileSpell extends AbstractSpell {

    public static Builder createProjectileBuilder() {
        return new Builder();
    }

    public ProjectileSpell(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
    }

    public static class Builder extends AbstractSpell.Builder<ProjectileSpell> {

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
    }
}
