package com.ombremoon.enderring.common.magic;

import com.ombremoon.enderring.common.ScaledWeapon;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.function.Supplier;

public abstract class SimpleSpell extends AbstractSpell {
    protected AnimationProvider spellAnimation;

    public SimpleSpell(SpellType<?> spellType, Builder builder) {
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

    public static class Builder extends AbstractSpell.Builder<SimpleSpell> {
        protected ResourceLocation spellAnimation;

        public Builder() {

        }

        public Builder setMagicType(MagicType magicType) {
            this.magicType = magicType;
            return this;
        }

        public Builder setAnimations(ResourceLocation spellAnimation) {
            this.spellAnimation = spellAnimation;
            return this;
        }

    }
}
