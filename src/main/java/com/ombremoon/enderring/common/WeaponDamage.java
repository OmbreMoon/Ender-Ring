package com.ombremoon.enderring.common;

import com.ombremoon.enderring.capability.PlayerStatus;
import com.ombremoon.enderring.common.data.Saturations;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.util.DamageUtil;
import com.ombremoon.enderring.util.PlayerStatusUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Function;

public enum WeaponDamage {
    PHYSICAL("PhysicalDamage", DamageUtil::getPhysicalAP, PlayerStatusUtil::getPhysDefense, ModDamageTypes.PHYSICAL, Saturations.PHYSICAL_DEFENSE, WeaponScaling.STR, PlayerStatus.PHYSICAL_DEF),
    MAGICAL("MagicDamage", DamageUtil::getMagicalAP, PlayerStatusUtil::getMagicDefense, ModDamageTypes.MAGICAL, Saturations.MAGICAL_DEFENSE, WeaponScaling.INT, PlayerStatus.MAGICAL_DEF),
    FIRE("FireDamage", DamageUtil::getFireAP, PlayerStatusUtil::getFireDefense, ModDamageTypes.FIRE, Saturations.FIRE_DEFENSE, WeaponScaling.FAI, PlayerStatus.FIRE_DEF),
    LIGHTNING("LightningDamage", DamageUtil::getLightningAP, PlayerStatusUtil::getLightDefense, ModDamageTypes.LIGHTNING, null, null, PlayerStatus.LIGHTNING_DEF),
    HOLY("HolyDamage", DamageUtil::getHolyAP, PlayerStatusUtil::getHolyDefense, ModDamageTypes.HOLY, Saturations.HOLY_DEFENSE, WeaponScaling.ARC, PlayerStatus.HOLY_DEF);

    private final String compoundName;
    private final TriFunction<ScaledWeapon, Player, Integer, Float> damageFunction;
    private final Function<Player, Float> defenseFunction;
    private final ResourceKey<DamageType> damageType;
    private final Saturations saturation;
    private final WeaponScaling weaponScaling;
    private final EntityDataAccessor<Float> dataAccessor;

    WeaponDamage(String compoundName, TriFunction<ScaledWeapon, Player, Integer, Float> damageFunction, Function<Player, Float> defenseFunction, ResourceKey<DamageType> damageType, Saturations saturation, WeaponScaling weaponScaling, EntityDataAccessor<Float> dataAccessor) {
        this.compoundName = compoundName;
        this.damageFunction = damageFunction;
        this.defenseFunction = defenseFunction;
        this.damageType = damageType;
        this.saturation = saturation;
        this.weaponScaling = weaponScaling;
        this.dataAccessor = dataAccessor;
    }

    public String getCompoundName() {
        return this.compoundName;
    }

    public TriFunction<ScaledWeapon, Player, Integer, Float> getDamageFunction() {
        return this.damageFunction;
    }

    public Function<Player, Float> getDefenseFunction() {
        return this.defenseFunction;
    }

    public ResourceKey<DamageType> getDamageType() {
        return this.damageType;
    }

    public Saturations getSaturation() {
        return this.saturation;
    }

    public WeaponScaling getWeaponScaling() {
        return this.weaponScaling;
    }

    public EntityDataAccessor<Float> getDataAccessor() {
        return this.dataAccessor;
    }
}
