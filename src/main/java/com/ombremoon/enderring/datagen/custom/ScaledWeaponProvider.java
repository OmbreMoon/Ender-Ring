package com.ombremoon.enderring.datagen.custom;

import com.ombremoon.enderring.CommonClass;
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
                .defaultMaxUpgrades().infusable().twoHandBonus()
                .weaponDamage(74, 0, 0, 0, 0)
                .elementID(10000).build());
    }
}
