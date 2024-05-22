package com.ombremoon.enderring.compat.epicfight.world.capabilities.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

public class ExtendedWeaponCapability extends WeaponCapability {
    Skill ashOfWar;

    protected ExtendedWeaponCapability(CapabilityItem.Builder builder) {
        super(builder);
        ExtendedWeaponCapability.Builder weaponBuilder = (ExtendedWeaponCapability.Builder)builder;
        this.ashOfWar = weaponBuilder.defaultAshOfWar;
    }

    public Skill getAshOfWar(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getTag();
        if (nbt != null && nbt.contains("AshOfWar", 8)) {
            return EpicFightSkills.SWEEPING_EDGE;
        }
        return this.ashOfWar;
    }

    public void swapAshOfWar(ItemStack itemStack, Skill ashOfWar) {
        itemStack.getOrCreateTag().putString("AshOfWar", ashOfWar.getRegistryName().toString());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends WeaponCapability.Builder {
        Skill defaultAshOfWar;

        protected Builder() {
            this.constructor(ExtendedWeaponCapability::new);
            this.defaultAshOfWar = null;
        }

        public Builder defaultAshOfWar(Skill defaultAshOfWar) {
            this.defaultAshOfWar = defaultAshOfWar;
            return this;
        }

        @Override
        public Builder category(WeaponCategory category) {
            super.category(category);
            return this;
        }

        @Override
        public Builder styleProvider(Function<LivingEntityPatch<?>, Style> styleProvider) {
            super.styleProvider(styleProvider);
            return this;
        }

        @Override
        public Builder passiveSkill(Skill passiveSkill) {
            super.passiveSkill(passiveSkill);
            return this;
        }

        @Override
        public Builder swingSound(SoundEvent swingSound) {
            super.swingSound(swingSound);
            return this;
        }

        @Override
        public Builder hitSound(SoundEvent hitSound) {
            super.hitSound(hitSound);
            return this;
        }

        @Override
        public Builder hitParticle(HitParticleType hitParticle) {
            super.hitParticle(hitParticle);
            return this;
        }

        @Override
        public Builder collider(Collider collider) {
            super.collider(collider);
            return this;
        }

        @Override
        public Builder canBePlacedOffhand(boolean canBePlacedOffhand) {
            super.canBePlacedOffhand(canBePlacedOffhand);
            return this;
        }

        @Override
        public Builder livingMotionModifier(Style wieldStyle, LivingMotion livingMotion, StaticAnimation animation) {
            super.livingMotionModifier(wieldStyle, livingMotion, animation);
            return this;
        }

        @Override
        public Builder addStyleAttibutes(Style style, Pair<Attribute, AttributeModifier> attributePair) {
            super.addStyleAttibutes(style, attributePair);
            return this;
        }

        @Override
        public Builder newStyleCombo(Style style, StaticAnimation... animation) {
            super.newStyleCombo(style, animation);
            return this;
        }

        @Override
        public Builder weaponCombinationPredicator(Function<LivingEntityPatch<?>, Boolean> predicator) {
            super.weaponCombinationPredicator(predicator);
            return this;
        }

        @Override
        public Builder innateSkill(Style style, Function<ItemStack, Skill> innateSkill) {
            super.innateSkill(style, innateSkill);
            return this;
        }

        @Override
        public Builder comboCancel(Function<Style, Boolean> comboCancel) {
            super.comboCancel(comboCancel);
            return this;
        }
    }
}
