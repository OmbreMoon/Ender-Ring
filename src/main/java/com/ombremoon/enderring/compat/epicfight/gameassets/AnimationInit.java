package com.ombremoon.enderring.compat.epicfight.gameassets;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.compat.epicfight.api.animation.AnimationProperties;
import com.ombremoon.enderring.compat.epicfight.api.animation.types.SpellAttackAnimation;
import com.ombremoon.enderring.compat.epicfight.util.EFMUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.tslat.smartbrainlib.util.RandomUtil;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimationInit {

    public static StaticAnimation SPELL_HEAL;
    public static StaticAnimation CATCH_FLAME;

    public AnimationInit() {

    }

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(Constants.MOD_ID, AnimationInit::build);
    }

    private static void build() {
        HumanoidArmature biped = Armatures.BIPED;
        SPELL_HEAL = new StaticAnimation(false, "biped/spell/heal", biped);
        CATCH_FLAME = new SpellAttackAnimation(0.08F, 0.0F, 0.70F, 0.8F, 1.2F, ColliderInit.CATCH_FLAME, biped.rootJoint, "biped/spell/catch_flame", biped).addProperty(AnimationProperties.ERPhaseProperty.FIRE_DAMAGE, 12.0F).addEvents(AnimationEvent.TimeStampedEvent.create(0.75F, (livingEntityPatch, staticAnimation, objects) -> {
            LivingEntity livingEntity = livingEntityPatch.getOriginal();
            RandomSource random = livingEntity.getRandom();
            AABB aabb =  EFMUtil.getSpellColliderBB(livingEntityPatch, (SpellAttackAnimation) staticAnimation);
            Vec3 vec3 = aabb.getCenter();
            double x = vec3.x();
            double y = vec3.y();
            double z = vec3.z();
            for (int i = 0; i < 15; i++) {
                livingEntity.level().addParticle(ParticleTypes.FLAME, x + RandomUtil.randomValueBetween(-0.5, 0.5), y + RandomUtil.randomValueBetween(-0.5, 0.5), z + RandomUtil.randomValueBetween(-0.5, 0.5), random.nextDouble() * 0.005, random.nextDouble() * 0.005, random.nextDouble() * 0.005);
            }
            livingEntity.level().playSound(livingEntity, livingEntity.getOnPos(), SoundEvents.GHAST_SHOOT, SoundSource.PLAYERS, 1.0F, random.nextFloat() * 0.4F + 0.8F);
        }, AnimationEvent.Side.CLIENT));
    }
}
