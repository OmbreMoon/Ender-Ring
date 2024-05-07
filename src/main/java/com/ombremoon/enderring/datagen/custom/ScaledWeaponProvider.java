package com.ombremoon.enderring.datagen.custom;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.ReinforceType;
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
        this.registerDaggers();
    }

    protected void registerDaggers() {
        addWeapon(CommonClass.customLocation("dagger"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades().infusable().twoHandBonus().elementID(10000).reinforceType(ReinforceType.DEFAULT)
                .weaponDamage(74, 0, 0, 0, 0)
                .weaponScaling(35, 65, 0, 0, 0)
                .build());

        addWeapon(CommonClass.customLocation("black_knife"), ScaledWeapon.Builder.create()
                .isUnique().twoHandBonus().elementID(10000).reinforceType(ReinforceType.HEAVY)
                .weaponDamage(66, 0, 0, 0, 65)
                .weaponScaling(10, 55, 0, 35, 0)
                .build());
    }
}
