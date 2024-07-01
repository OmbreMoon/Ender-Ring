package com.ombremoon.enderring.common.magic.spelltypes;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.magic.MagicType;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.event.custom.EventFactory;
import com.ombremoon.enderring.util.EntityStatusUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

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
    protected void onSpellTick(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellTick(playerPatch, level, blockPos, weapon);
        if (!EntityStatusUtil.consumeFP(playerPatch.getOriginal(), this.fpTickCost, true) || !EntityStatusUtil.isChannelling(playerPatch.getOriginal())) {
            this.endSpell();
        }
    }

    @Override
    protected void onSpellStop(ServerPlayerPatch playerPatch, Level level, BlockPos blockPos, ScaledWeapon weapon) {
        super.onSpellStop(playerPatch, level, blockPos, weapon);
        EntityStatusUtil.setChannelling(playerPatch.getOriginal(), false);
    }

    @Override
    public boolean requiresConcentration() {
        return true;
    }

    public static class Builder<T extends ChanneledSpell> extends AnimatedSpell.Builder<T> {
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
