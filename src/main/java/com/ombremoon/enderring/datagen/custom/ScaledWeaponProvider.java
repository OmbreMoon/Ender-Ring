package com.ombremoon.enderring.datagen.custom;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.data.AttackElement;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.ScaledWeapon;
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
        registerCatalysts();
    }

    protected void registerDaggers() {
        addWeapon(CommonClass.customLocation("dagger"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().infusable().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.DEFAULT)
                .saturation(0, 0, 0, 0, 0)
                .weaponDamage(74, 0, 0, 0, 0)
                .weaponScaling(35, 65, 0, 0, 0)
                .weaponRequirements(5, 9, 0, 0, 0).build());

        addWeapon(CommonClass.customLocation("black_knife"), ScaledWeapon.Builder.create()
                .isUnique().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.UNIQUE)
                .saturation(0, 0, 0, 0, 4)
                .weaponDamage(66, 0, 0, 0, 65)
                .weaponScaling(10, 55, 0, 35, 0)
                .weaponRequirements(8, 12, 0, 18, 0).build());
    }

    protected void registerCatalysts() {
        addWeapon(CommonClass.customLocation("glintstone_staff"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.STAFF).reinforceType(ReinforceType.PURE_CATALYST_FRONT)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(12, 0, 85, 0 ,0)
                .weaponRequirements(6, 0, 10, 0, 0).build());

        addWeapon(CommonClass.customLocation("astrologer_staff"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.STAFF).reinforceType(ReinforceType.PURE_CATALYST_FRONT)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(12, 0, 89, 0 ,0)
                .weaponRequirements(7, 0, 16, 0, 0).build());

        addWeapon(CommonClass.customLocation("finger_seal"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().twoHandBonus().elementID(AttackElement.SEAL).reinforceType(ReinforceType.PURE_CATALYST_FRONT)
                .saturation(14, 14, 14, 14, 14)
                .weaponDamage(25, 0, 0, 0, 0)
                .weaponScaling(15, 0, 0, 83 ,0)
                .weaponRequirements(4, 0, 0, 10, 0).build());
    }
}
