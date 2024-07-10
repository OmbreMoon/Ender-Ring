package com.ombremoon.enderring.datagen;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.object.world.ModDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagsProvider extends TagsProvider<DamageType> {
    protected ModDamageTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.DAMAGE_TYPE, pLookupProvider, Constants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(DamageTypeTags.BYPASSES_RESISTANCE)
                .add(ModDamageTypes.PHYSICAL)
                .add(ModDamageTypes.STRIKE)
                .add(ModDamageTypes.SLASH)
                .add(ModDamageTypes.PIERCE)
                .add(ModDamageTypes.MAGICAL)
                .add(ModDamageTypes.FIRE)
                .add(ModDamageTypes.LIGHTNING)
                .add(ModDamageTypes.HOLY)
                .add(ModDamageTypes.POISON)
                .add(ModDamageTypes.SCARLET_ROT)
                .add(ModDamageTypes.BLOOD_LOSS)
                .add(ModDamageTypes.FROSTBITE)
                .add(ModDamageTypes.MADNESS)
                .add(ModDamageTypes.DEATH_BLIGHT);

        tag(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(ModDamageTypes.PHYSICAL)
                .add(ModDamageTypes.STRIKE)
                .add(ModDamageTypes.SLASH)
                .add(ModDamageTypes.PIERCE)
                .add(ModDamageTypes.MAGICAL)
                .add(ModDamageTypes.FIRE)
                .add(ModDamageTypes.LIGHTNING)
                .add(ModDamageTypes.HOLY)
                .add(ModDamageTypes.POISON)
                .add(ModDamageTypes.SCARLET_ROT)
                .add(ModDamageTypes.BLOOD_LOSS)
                .add(ModDamageTypes.FROSTBITE)
                .add(ModDamageTypes.MADNESS)
                .add(ModDamageTypes.DEATH_BLIGHT);
    }
}
