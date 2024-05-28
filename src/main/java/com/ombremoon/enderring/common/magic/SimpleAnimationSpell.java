package com.ombremoon.enderring.common.magic;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public abstract class SimpleAnimationSpell extends AbstractSpell {
    protected AnimationProvider spellAnimation;

    public static Builder createSimpleSpellBuilder() {
        return new Builder();
    }

    public SimpleAnimationSpell(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
        this.spellAnimation = () -> {
            return EpicFightMod.getInstance().animationManager.findAnimationByPath(builder.spellAnimation.toString());
        };
    }

    @Override
    public void onSpellStart(SpellInstance spellInstance, ServerPlayerPatch playerPatch, ScaledWeapon weapon, Level level, BlockPos blockPos) {
//        this.spellConsumer.accept(playerPatch.getOriginal());
        playerPatch.playAnimationSynchronized(this.spellAnimation.get(), 0.0F);
    }

    public static class Builder extends AbstractSpell.Builder<SimpleAnimationSpell> {
        protected ResourceLocation spellAnimation;

        public Builder() {

        }

        public Builder setMagicType(MagicType magicType) {
            this.magicType = magicType;
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

        public Builder setRequirements(WeaponScaling weaponScaling, int statReq) {
            this.createReqList(weaponScaling, statReq);
            return this;
        }

        public Builder setMotionValue(float motionValue) {
            this.motionValue = motionValue;
            return this;
        }

        public Builder setAnimations(ResourceLocation spellAnimation) {
            this.spellAnimation = spellAnimation;
            return this;
        }
    }
}
