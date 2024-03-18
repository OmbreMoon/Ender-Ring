package com.ombremoon.ommodtemplate.datagen;

import com.ombremoon.ommodtemplate.Constants;
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
