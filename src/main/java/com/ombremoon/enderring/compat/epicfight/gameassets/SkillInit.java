package com.ombremoon.enderring.compat.epicfight.gameassets;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.compat.epicfight.skills.AshOfWarSkill;
import com.ombremoon.enderring.compat.epicfight.skills.HeavyAttack;
import com.ombremoon.enderring.compat.epicfight.skills.SimpleAshOfWarSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.data.reloader.SkillManager;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SkillInit {

    public static Skill HEAVY_ATTACK;
    public static Skill SWEEPING_EDGE_NEW;

    public static void registerSkills() {
        SkillManager.register(HeavyAttack::new, HeavyAttack.createHeavyAttackBuilder(), Constants.MOD_ID, "heavy_attack");
        SkillManager.register(SimpleAshOfWarSkill::new, SimpleAshOfWarSkill.createSimpleAshOfWarBuilder().setAnimations(new ResourceLocation(EpicFightMod.MODID, "biped/skill/sweeping_edge")), Constants.MOD_ID, "sweeping_edge_new");
    }

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent event) {
        HEAVY_ATTACK = event.build(Constants.MOD_ID, "heavy_attack");

        AshOfWarSkill sweepingEdgeNew = event.build(Constants.MOD_ID, "sweeping_edge_new");
        sweepingEdgeNew.newProperty()
                .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1))
                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F))
                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F))
                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                .addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create()))
                .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE))
                .registerPropertiesToAnimation();
        SWEEPING_EDGE_NEW = sweepingEdgeNew;
    }
}
