package com.ombremoon.enderring.event.custom;

import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.data.Saturation;
import com.ombremoon.enderring.common.magic.AbstractSpell;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.magic.spelltypes.AnimatedSpell;
import com.ombremoon.enderring.common.magic.spelltypes.ChanneledSpell;
import com.ombremoon.enderring.common.magic.spelltypes.ProjectileSpell;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;

public class EventFactory {

    public static AbstractSpell.Builder<?> getBuilder(SpellType<?> spellType, AbstractSpell.Builder<?> builder) {
        BuildSpellEvent event = new BuildSpellEvent(spellType, builder);
        if (MinecraftForge.EVENT_BUS.post(event)) return new AbstractSpell.Builder<>();
        return event.getBuilder();
    }

    public static AnimatedSpell.Builder<?> getAnimatedBuilder(SpellType<?> spellType, AnimatedSpell.Builder<?> builder) {
        BuildSpellEvent.Animated event = new BuildSpellEvent.Animated(spellType, builder);
        if (MinecraftForge.EVENT_BUS.post(event)) return new AnimatedSpell.Builder<>();
        return event.getBuilder();
    }

    public static ChanneledSpell.Builder<ChanneledSpell> getChanneledBuilder(SpellType<?> spellType, ChanneledSpell.Builder<ChanneledSpell> builder) {
        BuildSpellEvent.Channeled event = new BuildSpellEvent.Channeled(spellType, builder);
        if (MinecraftForge.EVENT_BUS.post(event)) return new ChanneledSpell.Builder<>();
        return event.getBuilder();
    }

    public static ProjectileSpell.Builder<? extends ProjectileSpell<?, ?>> getProjectileBuilder(SpellType<?> spellType, ProjectileSpell.Builder<? extends ProjectileSpell<?, ?>> builder) {
        BuildSpellEvent.Projectile event = new BuildSpellEvent.Projectile(spellType, builder);
        if (MinecraftForge.EVENT_BUS.post(event)) return new ProjectileSpell.Builder<>();
        return event.getBuilder();
    }

    public static float getFPConsumption(LivingEntity livingEntity, float fpAmount, AbstractSpell abstractSpell) {
        FPConsumeEvent event = new FPConsumeEvent(livingEntity, fpAmount, abstractSpell);
        if (MinecraftForge.EVENT_BUS.post(event)) return 0.0F;
        return event.getNewFPAmount();
    }

    public static float calculateWeaponDamage(LivingEntity livingEntity, WeaponDamage weaponDamage, ScaledWeapon scaledWeapon, float damageAmount) {
        CalculateEvent.Damage event = new CalculateEvent.Damage(livingEntity, weaponDamage, scaledWeapon, damageAmount);
        if (MinecraftForge.EVENT_BUS.post(event)) return 0.0F;
        return event.getNewDamageAmount();
    }

    public static float calculateCatalystScaling(LivingEntity livingEntity, WeaponDamage weaponDamage, ScaledWeapon scaledWeapon, float scalingAmount) {
        CalculateEvent.Scaling event = new CalculateEvent.Scaling(livingEntity, weaponDamage, scaledWeapon, scalingAmount);
        if (MinecraftForge.EVENT_BUS.post(event)) return 0.0F;
        return event.getNewScalingAmount();
    }

    public static float calculateEntityDefense(LivingEntity livingEntity, WeaponDamage weaponDamage, float defenseAmount) {
        CalculateEvent.Defense event = new CalculateEvent.Defense(livingEntity, weaponDamage, defenseAmount);
        if (MinecraftForge.EVENT_BUS.post(event)) return 0.0F;
        return event.getNewDefenseAmount();
    }

    public static float calculateEntityResistance(LivingEntity livingEntity, float resistAmount) {
        CalculateEvent.Resistance event = new CalculateEvent.Resistance(livingEntity, resistAmount);
        if (MinecraftForge.EVENT_BUS.post(event)) return 0.0F;
        return event.getNewResistAmount();
    }
}
