package com.ombremoon.enderring.util;

import com.google.common.collect.Lists;
import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.data.Saturation;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.WeaponDamage;
import com.ombremoon.enderring.common.WeaponScaling;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import net.minecraft.world.entity.player.Player;

import java.util.*;

//TODO: ADD DAMAGE CALCULATION EVENTS

public class DamageUtil {

    public static float getWeaponAP(ScaledWeapon weapon, Player player, int weaponLevel) {
        return getPhysicalAP(weapon, player, weaponLevel) + getMagicalAP(weapon, player, weaponLevel) + getFireAP(weapon, player, weaponLevel)
                + getLightningAP(weapon, player, weaponLevel) + getHolyAP(weapon, player, weaponLevel);
    }

    public static float getPhysicalAP(ScaledWeapon weapon, Player player, int weaponLevel) {
        return weapon.getDamage().getPhysDamage() > 0 ? calculateDamage(weapon, player, weaponLevel, WeaponDamage.PHYSICAL) : 0.0F;
    }

    public static float getMagicalAP(ScaledWeapon weapon, Player player, int weaponLevel) {
        return weapon.getDamage().getMagDamage() > 0 ? calculateDamage(weapon, player, weaponLevel, WeaponDamage.MAGICAL) : 0.0F;
    }

    public static float getFireAP(ScaledWeapon weapon, Player player, int weaponLevel) {
        return weapon.getDamage().getFireDamage() > 0 ? calculateDamage(weapon, player, weaponLevel, WeaponDamage.FIRE) : 0.0F;
    }

    public static float getLightningAP(ScaledWeapon weapon, Player player, int weaponLevel) {
        return weapon.getDamage().getLightDamage() > 0 ? calculateDamage(weapon, player, weaponLevel, WeaponDamage.LIGHTNING) : 0.0F;
    }

    public static float getHolyAP(ScaledWeapon weapon, Player player, int weaponLevel) {
        return weapon.getDamage().getHolyDamage() > 0 ? calculateDamage(weapon, player, weaponLevel, WeaponDamage.HOLY) : 0.0F;
    }

    public static float getSorceryScaling(ScaledWeapon weapon, Player player, int weaponLevel) {
        return calculateMagicScaling(weapon, player, weaponLevel, WeaponDamage.MAGICAL);
    }

    public static float getIncantScaling(ScaledWeapon weapon, Player player, int weaponLevel) {
        return calculateMagicScaling(weapon, player, weaponLevel, WeaponDamage.HOLY);
    }

    private static float calculateMagicScaling(ScaledWeapon weapon, Player player, int weaponLevel, WeaponDamage weaponDamage) {
        final var list = weapon.getBaseStats().getElementID().getListMap().get(weaponDamage);
        float f = 0;
        for (var scaling : list) {
            float scaleVal = getScalingUpgrade(weapon, scaling, weaponLevel);
            double attrVal = PlayerStatusUtil.getPlayerStat(player, scaling.getAttribute());
            f += scaleVal * getSaturationValue(attrVal, weapon, weaponDamage) * 100;
        }
        return 100 + f;
    }

    public static float calculateDamage(ScaledWeapon weapon, Player player, int weaponLevel, WeaponDamage weaponDamage) {
        float damage = getDamageUpgrade(weapon, weaponDamage, weaponLevel);
        float f = 0;
        if (weapon.getRequirements().meetsRequirements(player, weapon, weaponDamage)) {
            final var satList = createSaturationList(weapon, player, weaponDamage);
            for (var map : satList) {
                for (var entry : map.entrySet()) {
                    f += damage * getScalingUpgrade(weapon, entry.getKey(), weaponLevel) * entry.getValue();
                }
            }
            return damage + f;
        } else {
            return damage + (damage * -0.4F);
        }
    }

    private static float getDamageUpgrade(ScaledWeapon weapon, WeaponDamage weaponDamage, int weaponLevel) {
        ReinforceType reinforceType = weapon.getBaseStats().getReinforceType();
        return reinforceType.getReinforceDamageParam(weaponDamage, weaponLevel) * weapon.getDamage().getDamageMap().get(weaponDamage);
    }

    private static float getScalingUpgrade(ScaledWeapon weapon, WeaponScaling weaponScaling, int weaponLevel) {
        ReinforceType reinforceType = weapon.getBaseStats().getReinforceType();
        return reinforceType.getReinforceScaleParam(weaponScaling, weaponLevel) * weapon.getScale().getScalingMap().get(weaponScaling) / 100;
    }

    private static List<Map<WeaponScaling, Float>> createSaturationList(ScaledWeapon weapon, Player player, WeaponDamage weaponDamage) {
        List<Map<WeaponScaling, Float>> saturationMaps = new ArrayList<>();
        final var attackMap = weapon.getBaseStats().getElementID().getListMap();
        final var scalingMap = weapon.getScale().getScalingMap();

        var scaling = attackMap.get(weaponDamage);
        if (scaling != null) {
            scalingMap.keySet().forEach(weaponScaling -> {
                if (scaling.contains(weaponScaling)) {
                    double d0 = PlayerStatusUtil.getPlayerStat(player, weaponScaling.getAttribute());
                    Map<WeaponScaling, Float> map = new TreeMap<>() {{
                        put(weaponScaling, getSaturationValue(d0, weapon, weaponDamage));
                    }};
                    saturationMaps.add(map);
                }
            });
        }
        return saturationMaps;
    }

    public static float getSaturationValue(double attributeValue, ScaledWeapon weapon, WeaponDamage weaponDamage) {
        Saturation saturation = getSaturation(weapon, weaponDamage);
        int i = getStatIndex(saturation, attributeValue);
        float ratio = getSaturationRatio(saturation, i, attributeValue);

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

    private static float getSaturationRatio(Saturation saturation, int index, double attributeValue) {
        int statMin = saturation.getStat()[index];
        int statMax = saturation.getStat()[index + 1];
        return (float) ((attributeValue - statMin) / (statMax - statMin));
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
