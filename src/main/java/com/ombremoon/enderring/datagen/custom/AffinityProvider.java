package com.ombremoon.enderring.datagen.custom;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.ScaledWeapon;
import com.ombremoon.enderring.common.data.AttackElement;
import com.ombremoon.enderring.common.data.ReinforceType;
import com.ombremoon.enderring.common.object.PhysicalDamageType;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class AffinityProvider {

    public static class StandardProvider extends AffinityDataProvider.StandardDataProvider {

        public StandardProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(packOutput, completableFuture);
        }

        @Override
        protected void registerWeapons() {
            registerStandardDaggers();
        }

        protected void registerStandardDaggers() {
            addWeapon(CommonClass.customLocation("dagger"), ScaledWeapon.Builder.create()
                    .name(CommonClass.customLocation("dagger")).defaultMaxUpgrades().infusable().twoHandBonus().elementID(AttackElement.DEFAULT).reinforceType(ReinforceType.STANDARD).physDamageType(PhysicalDamageType.SLASH, PhysicalDamageType.PIERCE)
                    .saturation(0, 0, 0, 0, 0).weaponDamage(74, 0, 0, 0, 0).weaponScaling(35, 65, 0, 0, 0).weaponGuard(35, 20, 20, 20, 20).weaponRequirements(5, 9, 0, 0, 0).build());

        }
    }

    public static class FireProvider extends AffinityDataProvider.FireDataProvider {

        public FireProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(packOutput, completableFuture);
        }

        @Override
        protected void registerWeapons() {
            registerFireDaggers();
        }

        protected void registerFireDaggers() {
            addWeapon(CommonClass.customLocation("dagger"), ScaledWeapon.Builder.create()
                    .name(CommonClass.customLocation("dagger")).defaultMaxUpgrades().infusable().twoHandBonus().elementID(AttackElement.FIRE).reinforceType(ReinforceType.FIRE).physDamageType(PhysicalDamageType.SLASH, PhysicalDamageType.PIERCE)
                    .saturation(0, 0, 4, 0, 0).weaponDamage(71, 0, 71, 0, 0).weaponScaling(47, 10, 0, 0, 0).weaponGuard(35, 20, 20, 20, 20).weaponRequirements(5, 9, 0, 0, 0).build());

        }
    }
}
