package com.ombremoon.enderring.common;

import com.ombremoon.enderring.common.capability.PlayerStatus;
import com.ombremoon.enderring.common.data.Saturations;
import com.ombremoon.enderring.common.init.entity.EntityAttributeInit;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import com.ombremoon.enderring.util.DamageUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Supplier;

public enum WeaponDamage {
    PHYSICAL("PhysicalDamage", DamageUtil::getPhysicalAP, ModDamageTypes.PHYSICAL, Saturations.PHYSICAL_DEFENSE, WeaponScaling.STR, PlayerStatus.PHYSICAL_DEF, EntityAttributeInit.PHYS_DEFENSE, EntityAttributeInit.PHYS_NEGATE),
    MAGICAL("MagicDamage", DamageUtil::getMagicalAP, ModDamageTypes.MAGICAL, Saturations.MAGICAL_DEFENSE, WeaponScaling.INT, PlayerStatus.MAGICAL_DEF, EntityAttributeInit.MAGIC_DEFENSE, EntityAttributeInit.MAGIC_NEGATE),
    FIRE("FireDamage", DamageUtil::getFireAP, ModDamageTypes.FIRE, Saturations.FIRE_DEFENSE, WeaponScaling.FAI, PlayerStatus.FIRE_DEF, EntityAttributeInit.FIRE_DEFENSE, EntityAttributeInit.FIRE_NEGATE),
    LIGHTNING("LightningDamage", DamageUtil::getLightningAP, ModDamageTypes.LIGHTNING, null, null, PlayerStatus.LIGHTNING_DEF, EntityAttributeInit.LIGHT_DEFENSE, EntityAttributeInit.LIGHT_NEGATE),
    HOLY("HolyDamage", DamageUtil::getHolyAP, ModDamageTypes.HOLY, Saturations.HOLY_DEFENSE, WeaponScaling.ARC, PlayerStatus.HOLY_DEF, EntityAttributeInit.HOLY_DEFENSE, EntityAttributeInit.HOLY_NEGATE);

    private final String compoundName;
    private final TriFunction<ScaledWeapon, LivingEntity, Integer, Float> damageFunction;
    private final ResourceKey<DamageType> damageType;
    private final Saturations saturation;
    private final WeaponScaling weaponScaling;
    private final EntityDataAccessor<Float> dataAccessor;
    private final Supplier<Attribute> negateAttribute;
    private final Supplier<Attribute> defenseAttribute;

    WeaponDamage(String compoundName, TriFunction<ScaledWeapon, LivingEntity, Integer, Float> damageFunction, ResourceKey<DamageType> damageType, Saturations saturation, WeaponScaling weaponScaling, EntityDataAccessor<Float> dataAccessor, Supplier<Attribute> defenseAttribute, Supplier<Attribute> negateAttribute) {
        this.compoundName = compoundName;
        this.damageFunction = damageFunction;
        this.damageType = damageType;
        this.saturation = saturation;
        this.weaponScaling = weaponScaling;
        this.dataAccessor = dataAccessor;
        this.defenseAttribute = defenseAttribute;
        this.negateAttribute = negateAttribute;
    }

    public String getCompoundName() {
        return this.compoundName;
    }

    public TriFunction<ScaledWeapon, LivingEntity, Integer, Float> getDamageFunction() {
        return this.damageFunction;
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

    public EntityDataAccessor<Float> getDefAccessor() {
        return this.dataAccessor;
    }

    public Attribute getDefenseAttribute() {
        return this.defenseAttribute.get();
    }

    public Attribute getNegateAttribute() {
        return this.negateAttribute.get();
    }
}
