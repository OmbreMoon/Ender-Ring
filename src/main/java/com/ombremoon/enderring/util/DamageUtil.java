package com.ombremoon.enderring.util;

import com.google.common.collect.Lists;
import com.ombremoon.enderring.ConfigHandler;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.Saturation;
import com.ombremoon.enderring.common.data.Saturations;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.init.entity.StatusEffectInit;
import com.ombremoon.enderring.common.object.PhysicalDamageType;
import com.ombremoon.enderring.common.object.entity.IPlayerEnemy;
import com.ombremoon.enderring.common.object.item.equipment.weapon.Scalable;
import com.ombremoon.enderring.common.object.world.ModDamageSource;
import com.ombremoon.enderring.event.custom.EventFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
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

    public static void conditionalHurt(ItemStack itemStack, ScaledWeapon scaledWeapon, LivingEntity attackEntity, LivingEntity targetEntity) {
        conditionalHurt(itemStack, scaledWeapon, attackEntity, targetEntity, 1.0F);
    }

    public static void conditionalHurt(ItemStack itemStack, ScaledWeapon scaledWeapon, LivingEntity attackEntity, LivingEntity targetEntity, float motionValue) {
        Scalable scalable = (Scalable) itemStack.getItem();
        for (WeaponDamage weaponDamage : WeaponDamage.values()) {
            DamageSource damageSource;
            float typeDamage = weaponDamage.getDamageFunction().apply(scaledWeapon, attackEntity, scalable.getWeaponLevel(itemStack));

            //Ensures that type damage is 1 when between 0 and 1
            if (typeDamage > 0) {
                damageSource = moddedDamageSource(attackEntity.level(), weaponDamage.getDamageType(), scaledWeapon.getDamage().getPhysDamageTypes());
                typeDamage = Math.max(typeDamage, 1.0F);
                targetEntity.hurt(damageSource, motionValue != 0 ? typeDamage * motionValue : typeDamage);
            }
        }
    }

    public static DamageSource moddedDamageSource(Level level, ResourceKey<DamageType> damageType) {
        return new ModDamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType));
    }

    public static DamageSource moddedDamageSource(Level level, ResourceKey<DamageType> damageType, PhysicalDamageType... damageTypes) {
        return new ModDamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType)).addPhysicalDamage(damageTypes);
    }

    private static float calculateDamage(ScaledWeapon weapon, LivingEntity livingEntity, int weaponLevel, WeaponDamage weaponDamage) {
        float damage = getDamageUpgrade(weapon, weaponDamage, weaponLevel);
        float f = 0;
        if (livingEntity instanceof Player || livingEntity instanceof IPlayerEnemy) {
            if (weapon.getRequirements().meetsRequirements(livingEntity, weapon, weaponDamage)) {
                final var satList = createSaturationList(weapon, livingEntity, weaponDamage);
                for (var map : satList) {
                    for (var entry : map.entrySet()) {
                        f += damage * getScalingUpgrade(weapon, entry.getKey(), weaponLevel) * entry.getValue();
                    }
                }
                return EventFactory.calculateWeaponDamage(livingEntity, weaponDamage, weapon, ((damage + f) / STAT_SCALE) * getApMultipliers(livingEntity));
            } else {
                return EventFactory.calculateWeaponDamage(livingEntity, weaponDamage, weapon, ((damage + (damage * -0.4F)) / STAT_SCALE) * getApMultipliers(livingEntity));
            }
        } else {
            return EventFactory.calculateWeaponDamage(livingEntity, weaponDamage, weapon, damage * getApMultipliers(livingEntity));
        }
    }

    private static float getApMultipliers(LivingEntity entity) {
        float multiplier = 1.0F;
        if (entity.hasEffect(StatusEffectInit.RITUAL_SWORD_TALISMAN.get())
            && entity.getHealth() >= entity.getMaxHealth()) multiplier += 0.1F;
        if (entity.hasEffect(StatusEffectInit.RED_FEATHERED_BRANCHSWORD.get())
            && entity.getHealth() <= entity.getMaxHealth() * 0.2F) multiplier += 0.2F;

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
        return EventFactory.calculateCatalystScaling(player, weaponDamage, weapon, 100 + f * getApMultipliers(player));
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

    public static float calculateStatusBuildUp(ScaledWeapon weapon, LivingEntity livingEntity, int weaponLevel, int buildUp) {
        float f = getSaturationValue(Saturations.STATUS_EFFECT, EntityStatusUtil.getEntityAttribute(livingEntity, EntityAttributeInit.ARCANE.get()), false);
        return buildUp * getScalingUpgrade(weapon, WeaponScaling.ARC, weaponLevel) * f;
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

    private static float getSaturationValue(Saturation saturation, double attributeValue, boolean runeSaturation) {
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
