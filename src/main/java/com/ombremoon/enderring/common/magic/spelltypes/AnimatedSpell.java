package com.ombremoon.enderring.common.magic.spelltypes;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.function.Supplier;

public abstract class AnimatedSpell extends AbstractSpell {
    protected AnimationProvider spellAnimation;

    public static Builder<AnimatedSpell> createSimpleSpellBuilder() {
        return new Builder<>();
    }

    public AnimatedSpell(SpellType<?> spellType, Builder builder) {
        super(spellType, builder);
        if (builder.spellAnimation != null) {
            this.spellAnimation = () -> {
                return EpicFightMod.getInstance().animationManager.findAnimationByPath(((StaticAnimation) builder.spellAnimation.get()).getRegistryName().toString());
            };
        }
    }

    public StaticAnimation getSpellAnimation() {
        return this.spellAnimation.get();
    }

    @Override
    protected void onSpellStart(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellStart(playerPatch, level, blockPos, weapon);
        if (playerPatch.isBattleMode() && this.spellAnimation != null) {
            playerPatch.playAnimationSynchronized(this.spellAnimation.get(), 0.0F);
        }
    }

    public static class Builder<T extends AnimatedSpell> extends AbstractSpell.Builder<T> {
        protected Supplier<StaticAnimation> spellAnimation;

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
