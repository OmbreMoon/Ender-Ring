package com.ombremoon.enderring.compat.epicfight.skills;

import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedSkillCategories;
import com.ombremoon.enderring.compat.epicfight.world.capabilities.item.ExtendedWeaponCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public abstract class AshOfWarSkill extends WeaponInnateSkill {

    public static Skill.Builder<AshOfWarSkill> createAshOfWarBuilder() {
        return (new Builder<AshOfWarSkill>()).setCategory(ExtendedSkillCategories.ASH_OF_WAR).setResource(Resource.STAMINA);
    }

    public AshOfWarSkill(Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        if (executer.isLogicalClient()) {
            return super.canExecute(executer);
        } else {
            ItemStack itemstack = ((Player)executer.getOriginal()).getMainHandItem();
            if (this.checkExecuteCondition(executer)) {
                CapabilityItem capabilityItem = EpicFightCapabilities.getItemStackCapability(itemstack);
                if (capabilityItem instanceof ExtendedWeaponCapability weaponCapability) {
                    return weaponCapability.getAshOfWar(itemstack) == this && ((Player) executer.getOriginal()).getVehicle() == null && (!executer.getSkill(this).isActivated() || this.activateType == ActivateType.TOGGLE);
                }
            }
            return false;
        }
    }
}
