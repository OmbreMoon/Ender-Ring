package com.ombremoon.enderring.common.magic;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.SpellInit;

import java.util.function.Supplier;

public abstract class ProjectileSpell extends AbstractSpell {

    public static Builder createProjectileBuilder() {
        return new Builder();
    }

    public ProjectileSpell(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
    }

    public static ProjectileSpell.Builder createGlinstonePebbleBuilder() {
        return createProjectileBuilder()
                .setMagicType(MagicType.SORCERY)
                .setDuration(10)
                .setFPCost(7)
                .setRequirements(WeaponScaling.INT, 10);
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
    }
}
