package com.ombremoon.enderring.datagen.custom;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.ArmorResistance;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class ArmorResistanceProvider extends ArmorResistanceDataProvider {
    public ArmorResistanceProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, completableFuture);
    }

    @Override
    protected void registerArmors() {
        addArmor(CommonClass.customLocation("blue_cloth_cowl"), ArmorResistance.Builder.create()
                .negation(2.8F, 2.8F, 2.3F, 2.3F, 3.1F, 3.4F, 3.8F, 2.8F)
                .resistance(24, 15, 18, 18).build());
        addArmor(CommonClass.customLocation("blue_cloth_vest"), ArmorResistance.Builder.create()
                .negation(9.5F, 9.5F, 8.0F, 8.0F, 10.2F, 10.9F, 11.9F, 9.5F)
                .resistance(63, 42, 50, 50).build());
        addArmor(CommonClass.customLocation("blue_cloth_leggings"), ArmorResistance.Builder.create()
                .negation(5.4F, 5.4F, 4.5F, 4.5F, 5.8F, 6.2F, 6.8F, 5.4F)
                .resistance(39, 26, 31, 31).build());
        addArmor(CommonClass.customLocation("blue_cloth_greaves"), ArmorResistance.Builder.create()
                .negation(2.3F, 2.3F, 1.9F, 1.9F, 2.5F, 2.7F, 2.9F, 2.3F)
                .resistance(21, 14, 17, 17).build());
    }
}
