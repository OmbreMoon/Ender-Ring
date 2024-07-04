package com.ombremoon.enderring.datagen.custom;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.data.AttackElement;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.object.PhysicalDamageType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class ScaledWeaponProvider extends ScaledWeaponDataProvider {
    public ScaledWeaponProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, completableFuture);
    }

    @Override
    protected void registerWeapons() {
        registerDaggers();
        registerGreatSwords();
        registerCurvedSwords();
        registerHalberds();
        registerCatalysts();
        registerThrowingPots();
    }

    protected void registerDaggers() {
        addWeapon(CommonClass.customLocation("dagger"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("dagger")).defaultMaxUpgrades().infusable().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.STANDARD)
                .physDamageType(PhysicalDamageType.SLASH, PhysicalDamageType.PIERCE)
                .saturation(0, 0, 0, 0, 0)
                .weaponDamage(74, 0, 0, 0, 0)
                .weaponScaling(35, 65, 0, 0, 0)
                .weaponGuard(35, 20, 20, 20, 20)
                .weaponRequirements(5, 9, 0, 0, 0).build());

        addWeapon(CommonClass.customLocation("black_knife"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("black_knife")).isUnique().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.UNIQUE)
                .physDamageType(PhysicalDamageType.SLASH, PhysicalDamageType.PIERCE)
                .saturation(0, 0, 0, 0, 4)
                .weaponDamage(66, 0, 0, 0, 65)
                .weaponScaling(10, 55, 0, 35, 0)
                .weaponGuard(26, 15, 15, 15, 42)
                .weaponRequirements(8, 12, 0, 18, 0).build());
    }

    protected void registerGreatSwords() {
        addWeapon(CommonClass.customLocation("lordsworns_greatsword"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("lordsworns_greatsword")).defaultMaxUpgrades().infusable()
                .twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.STANDARD)
                .physDamageType(PhysicalDamageType.STANDARD, PhysicalDamageType.PIERCE)
                .saturation(0, 0, 0, 0 , 0)
                .weaponDamage(136, 0, 0, 0, 0)
                .weaponScaling(43, 37, 0, 0, 0)
                .weaponGuard(65, 35, 35, 35, 35)
                .weaponRequirements(16, 10, 0,0,0).build());
    }

    protected void registerCurvedSwords() {
        addWeapon(CommonClass.customLocation("scimitar"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("scimitar")).defaultMaxUpgrades().infusable().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.STANDARD)
                .physDamageType(PhysicalDamageType.SLASH)
                .saturation(0, 0, 0, 0, 0)
                .weaponDamage(106, 0, 0, 0, 0)
                .weaponScaling(25, 57, 0 ,0 ,0)
                .weaponRequirements(7, 13, 0, 0, 0).build());
    }

    protected void registerHalberds() {
        addWeapon(CommonClass.customLocation("guardian_swordspear"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("guardian_swordspear")).defaultMaxUpgrades().infusable().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.STANDARD)
                .physDamageType(PhysicalDamageType.STANDARD, PhysicalDamageType.PIERCE)
                .saturation(0, 0, 0, 0, 0)
                .weaponDamage(139, 0, 0, 0, 0)
                .weaponScaling(5, 64, 0, 0, 0)
                .weaponRequirements(17, 16, 0, 0, 0).build());
    }

    protected void registerCatalysts() {
        addWeapon(CommonClass.customLocation("glintstone_staff"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("glintstone_staff")).defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.STAFF).reinforceType(ReinforceType.STANDARD_CATALYST)
                .physDamageType(PhysicalDamageType.STRIKE)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(12, 0, 85, 0 ,0)
                .weaponRequirements(6, 0, 10, 0, 0).build());

        addWeapon(CommonClass.customLocation("astrologers_staff"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("astrologers_staff")).defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.STAFF).reinforceType(ReinforceType.STANDARD_CATALYST)
                .physDamageType(PhysicalDamageType.STRIKE)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(12, 0, 89, 0 ,0)
                .weaponRequirements(7, 0, 16, 0, 0).build());

        addWeapon(CommonClass.customLocation("finger_seal"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("finger_seal")).defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.SEAL).reinforceType(ReinforceType.STANDARD_CATALYST)
                .physDamageType(PhysicalDamageType.STRIKE)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(15, 0, 0, 83 ,0)
                .weaponRequirements(4, 0, 0, 10, 0).build());

        addWeapon(CommonClass.customLocation("giants_seal"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("giants_seal")).defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.SEAL).reinforceType(ReinforceType.STANDARD_CATALYST)
                .physDamageType(PhysicalDamageType.STRIKE)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(15, 0, 0, 77, 0)
                .weaponGuard(25, 15, 15, 15, 15)
                .weaponRequirements(4, 0, 0, 14, 0).build());
    }

    protected void registerThrowingPots() {
        addWeapon(CommonClass.customLocation("holy_water"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("holy_water")).elementID(AttackElement.HOLY_WATER_POT).reinforceType(ReinforceType.STANDARD)
                .saturation(0, 0, 0, 0, 4)
                .weaponDamage(0, 0, 0, 0, 167)
                .weaponScaling(0, 0, 0, 140, 0).build());

        addWeapon(CommonClass.customLocation("roped_holy_water"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("roped_holy_water")).elementID(AttackElement.HOLY_WATER_POT).reinforceType(ReinforceType.STANDARD)
                .saturation(0, 0, 0, 0, 4)
                .weaponDamage(0, 0, 0, 0, 150)
                .weaponScaling(0, 0, 0, 165, 0).build());

        addWeapon(CommonClass.customLocation("fetid_pot"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("fetid_pot")).elementID(AttackElement.ARCANE_POT).reinforceType(ReinforceType.STANDARD)
                .saturation(3, 0, 0, 0, 0)
                .weaponDamage(1, 0, 0, 0, 0)
                .weaponScaling(0, 0, 0, 0, 65)
                .poisonBuildUp(200).statusDuration(600).build());

        addWeapon(CommonClass.customLocation("roped_fetid_pot"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("roped_fetid_pot")).elementID(AttackElement.ARCANE_POT).reinforceType(ReinforceType.STANDARD)
                .saturation(3, 0, 0, 0, 0)
                .weaponDamage(1, 0, 0, 0, 0)
                .weaponScaling(0, 0, 0, 0, 65)
                .poisonBuildUp(200).statusDuration(600).build());

        addWeapon(CommonClass.customLocation("sleep_pot"), ScaledWeapon.Builder.create()
                .name(CommonClass.customLocation("sleep_pot")).elementID(AttackElement.ARCANE_POT).reinforceType(ReinforceType.STANDARD)
                .saturation(3, 0, 0, 0, 0)
                .weaponDamage(1, 0, 0, 0, 0)
                .weaponScaling(0, 0, 0, 0, 65)
                .sleepBuildUp(29).statusDuration(1200).build());
    }
}
