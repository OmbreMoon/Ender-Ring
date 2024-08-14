package com.ombremoon.enderring.util;

import com.google.common.collect.Lists;
import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.data.ScaledWeapon;
import com.ombremoon.enderring.common.StatusType;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.Saturation;
import com.ombremoon.enderring.common.data.Saturations;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.magic.SpellType;
import com.ombremoon.enderring.common.object.PhysicalDamageType;
import com.ombremoon.enderring.common.object.entity.IPlayerEnemy;
import com.ombremoon.enderring.common.object.entity.LevelledMob;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Scalable;
import com.ombremoon.enderring.common.object.world.ERDamageSource;
import com.ombremoon.enderring.common.object.world.effect.StatusEffect;
import com.ombremoon.enderring.common.object.world.effect.buildup.BuildUpStatusEffect;
import com.ombremoon.enderring.event.custom.EventFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.*;

public class DamageUtil {
    private static final int STAT_SCALE = ConfigHandler.STAT_SCALE.get();

    public static float getWeaponAP(ScaledWeapon weapon, Player player, int weaponLevel) {
        return getPhysicalAP(weapon, player, weaponLevel) + getMagicalAP(weapon, player, weaponLevel) + getFireAP(weapon, player, weaponLevel)
                + getLightningAP(weapon, player, weaponLevel) + getHolyAP(weapon, player, weaponLevel);
    }

    public static float getPhysicalAP(ScaledWeapon weapon, LivingEntity livingEntity, int weaponLevel) {
        return weapon.getDamage().getPhysDamage() > 0 ? calculateDamage(weapon, livingEntity, weaponLevel, WeaponDamage.PHYSICAL) : 0.0F;
    }

    public static float getMagicalAP(ScaledWeapon weapon, LivingEntity livingEntity, int weaponLevel) {
        return weapon.getDamage().getMagDamage() > 0 ? calculateDamage(weapon, livingEntity, weaponLevel, WeaponDamage.MAGICAL) : 0.0F;
    }

    public static float getFireAP(ScaledWeapon weapon, LivingEntity livingEntity, int weaponLevel) {
        return weapon.getDamage().getFireDamage() > 0 ? calculateDamage(weapon, livingEntity, weaponLevel, WeaponDamage.FIRE) : 0.0F;
    }

    public static float getLightningAP(ScaledWeapon weapon, LivingEntity livingEntity, int weaponLevel) {
        return weapon.getDamage().getLightDamage() > 0 ? calculateDamage(weapon, livingEntity, weaponLevel, WeaponDamage.LIGHTNING) : 0.0F;
    }

    public static float getHolyAP(ScaledWeapon weapon, LivingEntity livingEntity, int weaponLevel) {
        return weapon.getDamage().getHolyDamage() > 0 ? calculateDamage(weapon, livingEntity, weaponLevel, WeaponDamage.HOLY) : 0.0F;
    }

    public static float getSorceryScaling(ScaledWeapon weapon, Player player, int weaponLevel) {
        return calculateMagicScaling(weapon, player, weaponLevel, WeaponDamage.MAGICAL);
    }

    public static float getIncantScaling(ScaledWeapon weapon, Player player, int weaponLevel) {
        return calculateMagicScaling(weapon, player, weaponLevel, WeaponDamage.HOLY);
    }

    public static void handleStatusBuildUp(LivingEntity attackEntity, LivingEntity targetEntity, ScaledWeapon scaledWeapon, SpellType<?> spellType, DamageSource damageSource) {
        var statusMap = scaledWeapon.getStatus().getStatusMap();
        for (var entry : statusMap.entrySet()) {
            var status = entry.getKey();
            int buildUp = entry.getValue();
            if (targetEntity instanceof Player || targetEntity instanceof LevelledMob levelledMob && !levelledMob.isImmuneTo(targetEntity, status, damageSource)) {
                attemptBuildUp(attackEntity, targetEntity, scaledWeapon, spellType, status, buildUp);
            }
        }
    }

    private static void attemptBuildUp(LivingEntity attackEntity, LivingEntity targetEntity, ScaledWeapon scaledWeapon, SpellType<?> spellType, StatusType statusType, int buildUp) {
        BuildUpStatusEffect effect = statusType.getEffect().setScaledWeapon(scaledWeapon).setSpellType(spellType);
        if (!targetEntity.hasEffect(effect)) {
            EntityDataAccessor<Integer> status = statusType.getPlayerStatus();
            Attribute resist = targetEntity instanceof Player ? statusType.getPlayerResist() : statusType.getEntityResist();
            int threshold = (int) EntityStatusUtil.getEntityAttribute(targetEntity, resist);
            int currentStatus = targetEntity.getEntityData().get(status);
            targetEntity.getEntityData().set(status, (int) Mth.clamp(currentStatus + calculateStatusBuildUp(attackEntity, buildUp), 0, threshold));

            if (targetEntity.getEntityData().get(status) >= threshold) {
                targetEntity.getEntityData().set(status, 0);
                int duration = effect == StatusEffectInit.SLEEP.get() && targetEntity instanceof Player ? 20 : scaledWeapon.getStatus().getStatusDuration();
                targetEntity.addEffect(new MobEffectInstance(effect, duration, 0, true, true));
                if (effect.isInstantenous()) {
                    effect.applyInstantaneousEffect(attackEntity, null, targetEntity);
                }

                if (targetEntity instanceof LevelledMob levelledMob) {
                    //increment resistance
                }
            }
        }
    }

    public static ERDamageSource moddedDamageSource(Level level, ResourceKey<DamageType> damageType) {
        return moddedDamageSource(level, damageType, null);
    }

    public static ERDamageSource moddedDamageSource(Level level, ResourceKey<DamageType> damageType, LivingEntity attackEntity) {
        return moddedDamageSource(level, damageType, attackEntity, attackEntity);
    }

    public static ERDamageSource moddedDamageSource(Level level, ResourceKey<DamageType> damageType, Entity directEntity, LivingEntity attackEntity) {
        return new ERDamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), directEntity, attackEntity);
    }

    public static ERDamageSource moddedDamageSource(Level level, ResourceKey<DamageType> damageType, Entity directEntity, LivingEntity attackEntity, Scalable scalable, PhysicalDamageType... damageTypes) {
        return new ERDamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), directEntity, attackEntity).addPhysicalDamage(damageTypes).addScalable(scalable);
    }

    private static float calculateDamage(ScaledWeapon weapon, LivingEntity livingEntity, int weaponLevel, WeaponDamage weaponDamage) {
        float damage;
        float scaledDamage = getDamageUpgrade(weapon, weaponDamage, weaponLevel);
        if (livingEntity instanceof Player || livingEntity instanceof IPlayerEnemy) {
            if (weapon.getRequirements().meetsRequirements(livingEntity, weapon, weaponDamage)) {
                final var satList = createSaturationList(weapon, livingEntity, weaponDamage);
                float f = 0;
                for (var map : satList) {
                    for (var entry : map.entrySet()) {
                        f += scaledDamage * getScalingUpgrade(weapon, entry.getKey(), weaponLevel) * entry.getValue();
                    }
                }
                damage = ((scaledDamage + f) / STAT_SCALE) * getApMultipliers(livingEntity, weaponDamage);
            } else {
                damage = ((scaledDamage + (scaledDamage * -0.4F)) / STAT_SCALE) * getApMultipliers(livingEntity, weaponDamage);
            }
        } else {
            damage = scaledDamage * getApMultipliers(livingEntity, weaponDamage);
        }
//        Constants.LOG.info(String.valueOf(damage));
        return EventFactory.calculateWeaponDamage(livingEntity, weaponDamage, weapon, damage);
    }

    /**
     * Gets the attack power multiplier to add to the damage based on the effects active
     * @param entity The entity to check effects for
     * @param weaponDamage The damage type being used
     * @return damage multiplier
     */
    private static float getApMultipliers(LivingEntity entity, WeaponDamage weaponDamage) {
        float multiplier = 1.0F;
        if (entity.hasEffect(StatusEffectInit.RITUAL_SWORD_TALISMAN.get())
            && entity.getHealth() >= entity.getMaxHealth()) multiplier *= 1.1F;
        if (entity.hasEffect(StatusEffectInit.RED_FEATHERED_BRANCHSWORD.get())
            && entity.getHealth() <= entity.getMaxHealth() * 0.2F) multiplier *= 1.2F;
        if (entity.hasEffect(StatusEffectInit.LANCE_TALISMAN.get()) && entity.isPassenger()) multiplier *= 1.15F;
        if (entity.hasEffect(StatusEffectInit.KINDRED_OF_ROTS_EXULTATION.get())) multiplier *= 1.20F;
        if (entity.hasEffect(StatusEffectInit.LORD_OF_BLOODS_EXULTATION.get())) multiplier *= 1.20F;
        if (entity.hasEffect(StatusEffectInit.ATTACK_POWER_BUFF.get())) {
            int amp = entity.getEffect(StatusEffectInit.ATTACK_POWER_BUFF.get()).getAmplifier();
            multiplier *= ((StatusEffect) StatusEffectInit.ATTACK_POWER_BUFF.get()).getTier("none", amp);
        }

        if (weaponDamage == WeaponDamage.FIRE) {
            if (entity.hasEffect(StatusEffectInit.FIRE_SCORPION_CHARM.get())) multiplier *= 1.12F;
            if(entity.hasEffect(StatusEffectInit.FLAMESHROUDING_CRACKED.get())) multiplier*=1.2f;
        } else if (weaponDamage == WeaponDamage.HOLY) {
            if (entity.hasEffect(StatusEffectInit.FLOCKS_CANVAS_TALISMAN.get())) multiplier *= 1.08F;
            if (entity.hasEffect(StatusEffectInit.SACRED_SCORPION_CHARM.get())) multiplier *= 1.12F;
            if(entity.hasEffect(StatusEffectInit.HOLYSHROUDING_CRACKED.get())) multiplier*=1.2f;
        } else if (weaponDamage == WeaponDamage.MAGICAL) {
            if (entity.hasEffect(StatusEffectInit.MAGIC_SCORPION_CHARM.get())) multiplier *= 1.12F;
            if(entity.hasEffect(StatusEffectInit.MAGICSHROUDING_CRACKED.get())) multiplier*=1.2f;
        } else if (weaponDamage == WeaponDamage.LIGHTNING) {
            if (entity.hasEffect(StatusEffectInit.LIGHTNING_SCORPION_CHARM.get())) multiplier *= 1.12F;
            if(entity.hasEffect(StatusEffectInit.LIGHTNINGSHROUDING_CRACKED.get())) multiplier*=1.2f;
        }

        return multiplier;
    }

    public static float calculateMagicScaling(ScaledWeapon weapon, Player player, int weaponLevel, WeaponDamage weaponDamage) {
        final var list = weapon.getBaseStats().getElementID().getListMap().get(weaponDamage);
        float f = 0;
        for (var scaling : list) {
            float scaleVal = getScalingUpgrade(weapon, scaling, weaponLevel);
            double attrVal = EntityStatusUtil.getEntityAttribute(player, scaling.getAttribute());
            f += scaleVal * getSaturationValue(attrVal, weapon, weaponDamage, false) * 100;
        }
        return EventFactory.calculateCatalystScaling(player, weaponDamage, weapon, 100 + f * getApMultipliers(player, weaponDamage));
    }

    public static float calculateDefense(Player player, WeaponDamage weaponDamage) {
        float f = 0;
        if (weaponDamage != WeaponDamage.LIGHTNING) {
            f += getSaturationValue(weaponDamage.getSaturation(), EntityStatusUtil.getEntityAttribute(player, weaponDamage.getWeaponScaling().getAttribute()), false);
        }
        float def = (100 * (f + getSaturationValue(Saturations.RUNE_DEFENSE, EntityStatusUtil.getEntityAttribute(player, EntityAttributeInit.RUNE_LEVEL.get()), true))) / STAT_SCALE;
        return EventFactory.calculateEntityDefense(player, weaponDamage, def);
    }

    public static float calculateResistance(Player player, Attribute attribute) {
        float f = 0;
        if (EntityStatusUtil.isMainAttribute(attribute)) {
            f += getSaturationValue(Saturations.RESISTANCE, EntityStatusUtil.getEntityAttribute(player, attribute), false);
        } else if (attribute == EntityAttributeInit.ARCANE.get()) {
            f += getSaturationValue(Saturations.VITALITY, EntityStatusUtil.getEntityAttribute(player, attribute), false);
        }
        float resist = 100 * (f + getSaturationValue(Saturations.RUNE_RESISTANCE, EntityStatusUtil.getEntityAttribute(player, EntityAttributeInit.RUNE_LEVEL.get()), false));
        return EventFactory.calculateEntityResistance(player, resist);
    }

    public static float calculateStatusBuildUp(LivingEntity livingEntity, int buildUp) {
        float f = getSaturationValue(Saturations.STATUS_EFFECT, EntityStatusUtil.getEntityAttribute(livingEntity, EntityAttributeInit.ARCANE.get()), false);
        return buildUp * f + buildUp;
    }

    private static float getDamageUpgrade(ScaledWeapon weapon, WeaponDamage weaponDamage, int weaponLevel) {
        ReinforceType reinforceType = weapon.getBaseStats().getReinforceType();
        return reinforceType.getReinforceDamageParam(weaponDamage, weaponLevel) * weapon.getDamage().getDamageMap().get(weaponDamage);
    }

    private static float getScalingUpgrade(ScaledWeapon weapon, WeaponScaling weaponScaling, int weaponLevel) {
        ReinforceType reinforceType = weapon.getBaseStats().getReinforceType();
        return reinforceType.getReinforceScaleParam(weaponScaling, weaponLevel) * weapon.getScale().getScalingMap().get(weaponScaling) / 100;
    }

    private static List<Map<WeaponScaling, Float>> createSaturationList(ScaledWeapon weapon, LivingEntity livingEntity, WeaponDamage weaponDamage) {
        List<Map<WeaponScaling, Float>> saturationMaps = new ArrayList<>();
        final var attackMap = weapon.getBaseStats().getElementID().getListMap();
        final var scalingMap = weapon.getScale().getScalingMap();

        var scaling = attackMap.get(weaponDamage);
        if (scaling != null) {
            scalingMap.keySet().forEach(weaponScaling -> {
                if (scaling.contains(weaponScaling)) {
                    double d0 = EntityStatusUtil.getEntityAttribute(livingEntity, weaponScaling.getAttribute());
                    Map<WeaponScaling, Float> map = new TreeMap<>() {{
                        put(weaponScaling, getSaturationValue(d0, weapon, weaponDamage, false));
                    }};
                    saturationMaps.add(map);
                }
            });
        }
        return saturationMaps;
    }

    private static float getSaturationValue(double attributeValue, ScaledWeapon weapon, WeaponDamage weaponDamage, boolean runeSaturation) {
        return getSaturationValue(getSaturation(weapon, weaponDamage), attributeValue, runeSaturation);
    }

    public static float getSaturationValue(Saturation saturation, double attributeValue, boolean runeSaturation) {
        int i = getStatIndex(saturation, attributeValue);
        float ratio = getSaturationRatio(saturation, i, attributeValue, runeSaturation);

        double expMin = saturation.getExp()[i];
        double growth;
        if (expMin > 0) {
            growth = Math.pow(ratio, expMin);
        } else {
            growth = 1 - Math.pow(1 - ratio, Math.abs(expMin));
        }

        int growMin = saturation.getGrow()[i];
        int growMax = saturation.getGrow()[i + 1];
        return (float) (growMin + ((growMax - growMin) * growth)) / 100;
    }

    private static float getSaturationRatio(Saturation saturation, int index, double attributeValue, boolean runeSaturation) {
        int statMin = saturation.getStat()[index];
        int statMax = saturation.getStat()[index + 1];
        int i = runeSaturation ? 79 : 0;
        return (float) ((attributeValue + i - statMin) / (statMax - statMin));
    }

    private static Saturation getSaturation(ScaledWeapon weapon, WeaponDamage weaponDamage) {
        return weapon.getBaseStats().getSaturations()[weaponDamage.ordinal()];
    }

    private static int getStatIndex(Saturation saturation, double playerAttribute) {
        List<Integer> list = Lists.reverse(Arrays.stream(saturation.getStat()).boxed().toList());
        for (int i : list) {
            if (playerAttribute > i) {
                return 4 - list.indexOf(i);
            }
        }
        return 0;
    }
}
