package com.ombremoon.enderring.compat.epicfight.skills;

import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedSkillCategories;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;

public class SimpleAshOfWarSkill extends AshOfWarSkill {
    public static class Builder extends Skill.Builder<SimpleAshOfWarSkill> {
        protected ResourceLocation attackAnimation;

        public Builder setCategory(SkillCategory category) {
            this.category = category;
            return this;
        }

        public Builder setActivateType(ActivateType activateType) {
            this.activateType = activateType;
            return this;
        }

        public Builder setResource(Resource resource) {
            this.resource = resource;
            return this;
        }

        public Builder setAnimations(ResourceLocation attackAnimation) {
            this.attackAnimation = attackAnimation;
            return this;
        }
    }

    public static Builder createSimpleAshOfWarBuilder() {
        return (new Builder()).setCategory(ExtendedSkillCategories.ASH_OF_WAR).setResource(Resource.STAMINA);
    }

    protected AnimationProvider.AttackAnimationProvider attackAnimation;

    public SimpleAshOfWarSkill(Builder builder) {
        super(builder);

        this.attackAnimation = () -> (AttackAnimation) EpicFightMod.getInstance().animationManager.findAnimationByPath(builder.attackAnimation.toString());
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playAnimationSynchronized(this.attackAnimation.get(), 0);
        super.executeOnServer(executer, args);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = super.getTooltipOnItem(itemStack, cap, playerCap);
        this.generateTooltipforPhase(list, itemStack, cap, playerCap, this.properties.get(0), "Each Strike:");

        return list;
    }

    @Override
    public AshOfWarSkill registerPropertiesToAnimation() {
        AttackAnimation anim = this.attackAnimation.get();

        for (AttackAnimation.Phase phase : anim.phases) {
            phase.addProperties(this.properties.get(0).entrySet());
        }

        return this;
    }
}
