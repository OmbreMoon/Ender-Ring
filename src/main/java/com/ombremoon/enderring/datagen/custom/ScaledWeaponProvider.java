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
        registerCurvedSwords();
        registerHalberds();
        registerCatalysts();
        registerThrowingPots();
    }

    protected void registerDaggers() {
        addWeapon(CommonClass.customLocation("dagger"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().infusable().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.DEFAULT)
                .physDamageType(PhysicalDamageType.SLASH, PhysicalDamageType.PIERCE)
                .saturation(0, 0, 0, 0, 0)
                .weaponDamage(74, 0, 0, 0, 0)
                .weaponScaling(35, 65, 0, 0, 0)
                .weaponGuard(35, 20, 20, 20, 20)
                .weaponRequirements(5, 9, 0, 0, 0).build());

        addWeapon(CommonClass.customLocation("black_knife"), ScaledWeapon.Builder.create()
                .isUnique().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.UNIQUE)
                .physDamageType(PhysicalDamageType.SLASH, PhysicalDamageType.PIERCE)
                .saturation(0, 0, 0, 0, 4)
                .weaponDamage(66, 0, 0, 0, 65)
                .weaponScaling(10, 55, 0, 35, 0)
                .weaponGuard(26, 15, 15, 15, 42)
                .weaponRequirements(8, 12, 0, 18, 0).build());
    }

    protected void registerCurvedSwords() {
        addWeapon(CommonClass.customLocation("scimitar"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().infusable().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.DEFAULT)
                .physDamageType(PhysicalDamageType.SLASH)
                .saturation(0, 0, 0, 0, 0)
                .weaponDamage(106, 0, 0, 0, 0)
                .weaponScaling(25, 57, 0 ,0 ,0)
                .weaponRequirements(7, 13, 0, 0, 0).build());
    }

    protected void registerHalberds() {
        addWeapon(CommonClass.customLocation("guardian_swordspear"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().infusable().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.DEFAULT)
                .physDamageType(PhysicalDamageType.STANDARD, PhysicalDamageType.PIERCE)
                .saturation(0, 0, 0, 0, 0)
                .weaponDamage(139, 0, 0, 0, 0)
                .weaponScaling(5, 64, 0, 0, 0)
                .weaponRequirements(17, 16, 0, 0, 0).build());
    }

    protected void registerCatalysts() {
        addWeapon(CommonClass.customLocation("glintstone_staff"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.STAFF).reinforceType(ReinforceType.PURE_CATALYST_FRONT)
                .physDamageType(PhysicalDamageType.STRIKE)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(12, 0, 85, 0 ,0)
                .weaponRequirements(6, 0, 10, 0, 0).build());

        addWeapon(CommonClass.customLocation("astrologers_staff"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.STAFF).reinforceType(ReinforceType.PURE_CATALYST_FRONT)
                .physDamageType(PhysicalDamageType.STRIKE)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(12, 0, 89, 0 ,0)
                .weaponRequirements(7, 0, 16, 0, 0).build());

        addWeapon(CommonClass.customLocation("finger_seal"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.SEAL).reinforceType(ReinforceType.PURE_CATALYST_FRONT)
                .physDamageType(PhysicalDamageType.STRIKE)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(15, 0, 0, 83 ,0)
                .weaponRequirements(4, 0, 0, 10, 0).build());

        addWeapon(CommonClass.customLocation("giants_seal"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.SEAL).reinforceType(ReinforceType.PURE_CATALYST_FRONT)
                .physDamageType(PhysicalDamageType.STRIKE)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(15, 0, 0, 77, 0)
                .weaponGuard(25, 15, 15, 15, 15)
                .weaponRequirements(4, 0, 0, 14, 0).build());
    }

    protected void registerThrowingPots() {
        addWeapon(CommonClass.customLocation("holy_water"), ScaledWeapon.Builder.create()
                .elementID(AttackElement.HOLY_WATER_POT).reinforceType(ReinforceType.DEFAULT)
                .saturation(0, 0, 0, 0, 4)
                .weaponDamage(0, 0, 0, 0, 167)
                .weaponScaling(0, 0, 0, 140, 0).build());

        addWeapon(CommonClass.customLocation("roped_holy_water"), ScaledWeapon.Builder.create()
                .elementID(AttackElement.HOLY_WATER_POT).reinforceType(ReinforceType.DEFAULT)
                .saturation(0, 0, 0, 0, 4)
                .weaponDamage(0, 0, 0, 0, 150)
                .weaponScaling(0, 0, 0, 165, 0).build());
    }
}
