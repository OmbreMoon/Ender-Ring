package com.ombremoon.enderring.compat.epicfight.gameassets;

import com.ombremoon.enderring.Constants;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimationInit {

    public static StaticAnimation SPELL_HEAL;
//    public static StaticAnimation TEST_CIRCLE;

    public AnimationInit() {

    }

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(Constants.MOD_ID, AnimationInit::build);
    }

    private static void build() {
        HumanoidArmature biped = Armatures.BIPED;
        SPELL_HEAL = new StaticAnimation(false, "biped/spell/heal", biped);
//        TEST_CIRCLE = new StaticAnimation(0.15F, false, "biped/skill/steel_whirlwind_charging", biped).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CHARGING).addEvents(AnimationProperty.StaticAnimationProperty.ON_BEGIN_EVENTS, AnimationEvent.create((livingEntityPatch, staticAnimation, objects) -> {
//            Vec3 pos = livingEntityPatch.getOriginal().position();
//        }, AnimationEvent.Side.CLIENT));

    }
}
