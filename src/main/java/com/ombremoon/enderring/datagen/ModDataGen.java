package com.ombremoon.enderring.datagen;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.datagen.custom.ArmorResistanceProvider;
import com.ombremoon.enderring.datagen.custom.ScaledWeaponProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGen {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        CompletableFuture<HolderLookup.Provider> lookupProviderWithOwn = lookupProvider.thenApply(provider ->
                DatapackRegistriesProvider.BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), provider));

        boolean includeServer = event.includeServer();
        boolean includeClient = event.includeClient();
        generator.addProvider(includeServer, new ModRecipeProvider(packOutput));
        generator.addProvider(includeServer, new ModLootTableProvider(packOutput));
        generator.addProvider(includeServer, new ModSoundProvider(packOutput, existingFileHelper));
        generator.addProvider(includeServer, new ModTagProvider.Blocks(packOutput, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(includeServer, new ModTagProvider.Items(packOutput, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(includeServer, new ModTagProvider.Curios(packOutput, event.getLookupProvider(), existingFileHelper));
        generator.addProvider(includeServer, new ModGlobalLootModifiersProvider(packOutput));
        generator.addProvider(includeServer, new DatapackRegistriesProvider(packOutput, lookupProvider));
        generator.addProvider(includeServer, new ModDamageTypeTagsProvider(packOutput, lookupProviderWithOwn, existingFileHelper));
        generator.addProvider(includeServer, new ScaledWeaponProvider(packOutput, lookupProvider));
        generator.addProvider(includeServer, new ArmorResistanceProvider(packOutput, lookupProvider));
        generator.addProvider(includeClient, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new ModLangProvider(packOutput));
    }
}
