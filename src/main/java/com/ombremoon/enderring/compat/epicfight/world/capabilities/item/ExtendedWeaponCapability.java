package com.ombremoon.enderring.compat.epicfight.world.capabilities.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPChangeSkill;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExtendedWeaponCapability extends WeaponCapability {
    protected final Map<Style, List<StaticAnimation>> heavyAutoAttackMotions;
    protected final Map<Style, List<Float>> autoMotionValues;
    protected final Map<Style, List<Float>> heavyAutoMotionValues;
    Skill ashOfWar;

    protected ExtendedWeaponCapability(CapabilityItem.Builder builder) {
        super(builder);
        ExtendedWeaponCapability.Builder weaponBuilder = (ExtendedWeaponCapability.Builder)builder;
        this.heavyAutoAttackMotions = weaponBuilder.heavyAutoAttackMotionMap;
        this.autoMotionValues = weaponBuilder.autoMotionValues;
        this.heavyAutoMotionValues = weaponBuilder.heavyAutoMotionValues;
        this.ashOfWar = weaponBuilder.defaultAshOfWar;
    }

    public final List<StaticAnimation> getHeavyAutoAttackMotion(PlayerPatch<?> playerPatch) {
        return this.heavyAutoAttackMotions.get(this.getStyle(playerPatch));
    }

    public final List<Float> getAutoMotionValues(PlayerPatch<?> playerPatch) {
        return this.autoMotionValues.get(this.getStyle(playerPatch));
    }

    public List<Float> getHeavyAutoMotionValues(PlayerPatch<?> playerPatch) {
        return this.heavyAutoMotionValues.get(this.getStyle(playerPatch));
    }

    public Skill getAshOfWar(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getTag();
        if (nbt != null && nbt.contains("AshOfWar", 8)) {
            return SkillManager.getSkill(nbt.getString("AshOfWar"));
        }
        return this.ashOfWar;
    }

    public void swapAshOfWar(ItemStack itemStack, Skill ashOfWar) {
        itemStack.getOrCreateTag().putString("AshOfWar", ashOfWar.getRegistryName().toString());
    }

    @Override
    public void changeWeaponInnateSkill(PlayerPatch<?> playerpatch, ItemStack itemstack) {
        Skill ashOfWarSkill = this.getAshOfWar(itemstack);
        String skillName = "";
        SPChangeSkill.State state = SPChangeSkill.State.ENABLE;
        SkillContainer ashOfWarSkillContainer = playerpatch.getSkill(ExtendedSkillSlots.ASH_OF_WAR);

        if (ashOfWarSkill != null) {
            if (ashOfWarSkillContainer.getSkill() != ashOfWarSkill) {
                ashOfWarSkillContainer.setSkill(ashOfWarSkill);
            }

            skillName = ashOfWarSkill.toString();
        } else {
            state = SPChangeSkill.State.DISABLE;
        }

        ashOfWarSkillContainer.setDisabled(ashOfWarSkill == null);

        EpicFightNetworkManager.sendToPlayer(new SPChangeSkill(ExtendedSkillSlots.ASH_OF_WAR, skillName, state), (ServerPlayer) playerpatch.getOriginal());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends WeaponCapability.Builder {
        private final Map<Style, List<StaticAnimation>> heavyAutoAttackMotionMap;
        private final Map<Style, List<Float>> autoMotionValues;
        private final Map<Style, List<Float>> heavyAutoMotionValues;
        private Skill defaultAshOfWar;

        protected Builder() {
            this.constructor(ExtendedWeaponCapability::new);
            this.defaultAshOfWar = null;
            this.heavyAutoAttackMotionMap = Maps.newHashMap();
            this.autoMotionValues = Maps.newHashMap();
            this.heavyAutoMotionValues = Maps.newHashMap();

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

        public Builder newHeavyCombo(Style style, StaticAnimation... animation) {
            this.heavyAutoAttackMotionMap.put(style, Lists.newArrayList(animation));
            return this;
        }

        public Builder motionValues(Style style, Float... motionValues) {
            this.autoMotionValues.put(style, Lists.newArrayList(motionValues));
            return this;
        }

        public Builder heavyMotionValues(Style style, Float... heavyMotionValues) {
            this.autoMotionValues.put(style, Lists.newArrayList(heavyMotionValues));
            return this;
        }

        public Builder defaultAshOfWar(Skill defaultAshOfWar) {
            this.defaultAshOfWar = defaultAshOfWar;
            return this;
        }
    }
}
