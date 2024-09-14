package com.ombremoon.enderring.datagen.custom;

import com.ombremoon.enderring.CommonClass;
import com.ombremoon.enderring.common.data.ArmorResistance;
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

        //Vagabond Knight Set
        addArmor(CommonClass.customLocation("vagabond_knight_helm"), ArmorResistance.Builder.create()
                .negation(4F,4.6F,3.6F,4.2F,4,3.1F,3.6F,2.8F)
                .resistance(14,23,9,9).build());
        addArmor(CommonClass.customLocation("vagabond_knight_armor"), ArmorResistance.Builder.create()
                .negation(13.5F,11.4F,12.4F,11.9F,10.2F,10.9F,8.8F,8.8F)
                .resistance(35,57,23,23).build());
        addArmor(CommonClass.customLocation("vagabond_knight_gauntlets"), ArmorResistance.Builder.create()
                .negation(3.3F,2.8F,3.1F,2.9F,2.5F,2.7F,2.1F,2.1F)
                .resistance(12,19,8,8).build());
        addArmor(CommonClass.customLocation("vagabond_knight_greaves"), ArmorResistance.Builder.create()
                .negation(7.4F,5.8F,6.8F,6.5F,5F,5.8F,4.5F,4.5F)
                .resistance(20,34,13,13).build());

        //Land of Reeds Set
        addArmor(CommonClass.customLocation("land_of_reeds_helm"), ArmorResistance.Builder.create()
                .negation(3.1F,3.4F,4.8F,3.4F,3.6F,4F,4.2F,3.8F)
                .resistance(26,22,0,23).build());
        addArmor(CommonClass.customLocation("land_of_reeds_armor"), ArmorResistance.Builder.create()
                .negation(8.8F,9.5F,11.9F,9.5F,10.2F,11.4F,11.9F,10.9F)
                .resistance(60,50,50,55).build());
        addArmor(CommonClass.customLocation("land_of_reeds_guantlets"), ArmorResistance.Builder.create()
                .negation(2.1F,2.3F,2.9F,2.3F,2.5F,2.8F,2.9F,2.7F)
                .resistance(20,17,17,18).build());
        addArmor(CommonClass.customLocation("land_of_reeds_greaves"), ArmorResistance.Builder.create()
                .negation(5F,5.4F,8.8F,5.4F,5.8F,6.5F,6.8F,6.2F)
                .resistance(37,31,31,34).build());

        // Astrologer Set
        addArmor(CommonClass.customLocation("astrologer_hood"), ArmorResistance.Builder.create()
                .negation(2.2F, 2.2F, 2.0F, 2.0F, 2.5F, 2.7F, 3.1F, 2.1F)
                .resistance(12, 10, 14, 10).build());
        addArmor(CommonClass.customLocation("astrologer_robe"), ArmorResistance.Builder.create()
                .negation(6.0F, 6.2F, 5.8F, 5.8F, 7.5F, 7.8F, 7.9F, 6.5F)
                .resistance(38, 30, 45, 30).build());
        addArmor(CommonClass.customLocation("astrologer_gloves"), ArmorResistance.Builder.create()
                .negation(1.8F, 1.8F, 1.7F, 1.7F, 2.0F, 2.3F, 2.6F, 1.8F)
                .resistance(8, 6, 10, 8).build());
        addArmor(CommonClass.customLocation("astrologer_trousers"), ArmorResistance.Builder.create()
                .negation(3.4F, 3.6F, 3.3F, 3.3F, 4.5F, 4.6F, 4.8F, 3.8F)
                .resistance(25, 20, 35, 20).build());

        // Bandit Set
        addArmor(CommonClass.customLocation("bandit_mask"), ArmorResistance.Builder.create()
                .negation(1.8F, 1.7F, 1.5F, 1.5F, 2.3F, 2.5F, 2.7F, 2.1F)
                .resistance(10, 8, 11, 8).build());
        addArmor(CommonClass.customLocation("bandit_garbs"), ArmorResistance.Builder.create()
                .negation(5.5F, 5.8F, 5.1F, 5.2F, 6.5F, 7.0F, 7.3F, 5.7F)
                .resistance(33, 28, 40, 25).build());
        addArmor(CommonClass.customLocation("bandit_bracers"), ArmorResistance.Builder.create()
                .negation(1.5F, 1.5F, 1.3F, 1.3F, 1.8F, 1.9F, 2.0F, 1.7F)
                .resistance(6, 5, 8, 6).build());
        addArmor(CommonClass.customLocation("bandit_boots"), ArmorResistance.Builder.create()
                .negation(3.0F, 3.2F, 2.8F, 2.8F, 4.0F, 4.2F, 4.3F, 3.5F)
                .resistance(21, 18, 30, 20).build());

        // Confessor Set
        addArmor(CommonClass.customLocation("confessor_hood"), ArmorResistance.Builder.create()
                .negation(3.0F, 3.2F, 2.8F, 2.9F, 3.5F, 3.8F, 4.0F, 3.0F)
                .resistance(15, 13, 16, 14).build());
        addArmor(CommonClass.customLocation("confessor_armor"), ArmorResistance.Builder.create()
                .negation(8.8F, 9.0F, 7.9F, 8.2F, 9.5F, 10.0F, 10.2F, 9.0F)
                .resistance(55, 42, 58, 46).build());
        addArmor(CommonClass.customLocation("confessor_gauntlets"), ArmorResistance.Builder.create()
                .negation(2.2F, 2.5F, 2.0F, 2.2F, 2.8F, 3.0F, 3.2F, 2.5F)
                .resistance(12, 10, 14, 12).build());
        addArmor(CommonClass.customLocation("confessor_boots"), ArmorResistance.Builder.create()
                .negation(5.5F, 5.6F, 4.8F, 5.0F, 6.3F, 6.8F, 7.0F, 5.8F)
                .resistance(33, 28, 40, 30).build());

        //Hero Set
        addArmor(CommonClass.customLocation("hero_helm"), ArmorResistance.Builder.create()
                .negation(3.4F, 3.6F, 3.2F, 3.3F, 4.5F, 4.6F, 4.8F, 3.8F)
                .resistance(22, 20, 25, 20).build());
        addArmor(CommonClass.customLocation("hero_armor"), ArmorResistance.Builder.create()
                .negation(10.2F, 10.5F, 9.8F, 10.0F, 12.5F, 13.0F, 13.5F, 11.0F)
                .resistance(65, 55, 70, 60).build());
        addArmor(CommonClass.customLocation("hero_gauntlets"), ArmorResistance.Builder.create()
                .negation(3.0F, 3.2F, 2.8F, 3.0F, 4.0F, 4.2F, 4.5F, 3.5F)
                .resistance(20, 18, 22, 18).build());
        addArmor(CommonClass.customLocation("hero_boots"), ArmorResistance.Builder.create()
                .negation(6.0F, 6.3F, 5.7F, 5.9F, 7.2F, 7.5F, 7.8F, 6.5F)
                .resistance(40, 35, 45, 38).build());

        // Prisoner Set
        addArmor(CommonClass.customLocation("prisoner_iron_mask"), ArmorResistance.Builder.create()
                .negation(4.8F, 4.5F, 4.2F, 4.5F, 5.3F, 5.5F, 5.7F, 4.8F)
                .resistance(30, 25, 30, 26).build());
        addArmor(CommonClass.customLocation("prisoner_clothing"), ArmorResistance.Builder.create()
                .negation(7.0F, 7.2F, 6.8F, 6.8F, 8.5F, 9.0F, 9.2F, 7.5F)
                .resistance(45, 35, 50, 38).build());
        addArmor(CommonClass.customLocation("prisoner_trousers"),ArmorResistance.Builder.create()
                .negation(2.3F,3F,3F,2.3F,6.8F,6.5F,6.5F,7.2F)
                .resistance(26,14,39,39).build());

        addArmor(CommonClass.customLocation("prophet_blindfold"), ArmorResistance.Builder.create()
                .negation(1.2F, 1.5F, 1.0F, 1.0F, 1.5F, 1.7F, 1.8F, 1.5F)
                .resistance(8, 6, 9, 7).build());
        addArmor(CommonClass.customLocation("prophet_robe"), ArmorResistance.Builder.create()
                .negation(5.2F, 5.5F, 4.8F, 5.0F, 6.3F, 6.8F, 7.0F, 5.8F)
                .resistance(30, 24, 35, 26).build());
        addArmor(CommonClass.customLocation("prophet_trousers"), ArmorResistance.Builder.create()
                .negation(3.1F, 3.2F, 2.8F, 3.0F, 4.0F, 4.2F, 4.3F, 3.5F)
                .resistance(20, 16, 25, 18).build());
        // Astrologer Set
        addArmor(CommonClass.customLocation("astrologer_hood"), ArmorResistance.Builder.create()
                .negation(2.2F, 2.2F, 2.0F, 2.0F, 2.5F, 2.7F, 3.1F, 2.1F)
                .resistance(12, 10, 14, 10).build());
        addArmor(CommonClass.customLocation("astrologer_robe"), ArmorResistance.Builder.create()
                .negation(6.0F, 6.2F, 5.8F, 5.8F, 7.5F, 7.8F, 7.9F, 6.5F)
                .resistance(38, 30, 45, 30).build());
        addArmor(CommonClass.customLocation("astrologer_gloves"), ArmorResistance.Builder.create()
                .negation(1.8F, 1.8F, 1.7F, 1.7F, 2.0F, 2.3F, 2.6F, 1.8F)
                .resistance(8, 6, 10, 8).build());
        addArmor(CommonClass.customLocation("astrologer_trousers"), ArmorResistance.Builder.create()
                .negation(3.4F, 3.6F, 3.3F, 3.3F, 4.5F, 4.6F, 4.8F, 3.8F)
                .resistance(25, 20, 35, 20).build());
    }
}
