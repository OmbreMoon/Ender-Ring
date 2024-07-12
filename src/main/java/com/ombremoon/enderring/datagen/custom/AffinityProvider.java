package com.ombremoon.enderring.datagen.custom;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.data.ScaledWeapon;
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

    public static class HeavyProvider extends AffinityDataProvider.HeavyDataProvider {

        public HeavyProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class KeenProvider extends AffinityDataProvider.KeenDataProvider {

        public KeenProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class QualityProvider extends AffinityDataProvider.QualityDataProvider {

        public QualityProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class FlameProvider extends AffinityDataProvider.FlameDataProvider {

        public FlameProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class LightningProvider extends AffinityDataProvider.LightningDataProvider {

        public LightningProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class SacredProvider extends AffinityDataProvider.SacredDataProvider {

        public SacredProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class MagicProvider extends AffinityDataProvider.MagicDataProvider {

        public MagicProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class ColdProvider extends AffinityDataProvider.ColdDataProvider {

        public ColdProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class PoisonProvider extends AffinityDataProvider.PoisonDataProvider {

        public PoisonProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class BloodProvider extends AffinityDataProvider.BloodDataProvider {

        public BloodProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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

    public static class OccultProvider extends AffinityDataProvider.OccultDataProvider {

        public OccultProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
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
