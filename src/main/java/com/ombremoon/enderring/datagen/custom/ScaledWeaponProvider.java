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
        this.addWeapon(CommonClass.customLocation("dagger"), ScaledWeapon.Builder.create()
                .defaultMaxUpgrades()
                .infusable()
                .twoHandBonus()
                .build());

        this.addWeapon(CommonClass.customLocation("black_knife"), ScaledWeapon.Builder.create()
                .isUnique()
                .twoHandBonus()
                .build());
    }
}
