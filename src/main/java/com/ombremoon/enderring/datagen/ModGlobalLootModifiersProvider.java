package com.ombremoon.enderring.datagen;

import com.ombremoon.enderring.Constants;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void start() {

    }
}
