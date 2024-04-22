package com.ombremoon.enderring.datagen;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.entity.modifier.AddItemModifier;
import com.ombremoon.enderring.common.init.item.ItemInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    private static final String CHEST = "chests";
    private static final String VILLAGE = "village";
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void start() {
        addToEntityLootTable("white_flesh_from_turtle", EntityType.TURTLE, 0.25F, 1, ItemInit.WHITE_FLESH_STRIP.get());

        for (ResourceLocation resourceLocation : BuiltInLootTables.all()) {
            if (resourceLocation.getPath().startsWith(CHEST)) {
                String name = resourceLocation.getPath().substring(CHEST.length() + 1);
                if (name.startsWith(VILLAGE)) {
                    name = name.substring(VILLAGE.length() + 1);
                }
                addToStructureLootTable("cracked_pot_from_" + name, resourceLocation, ItemInit.CRACKED_POT.get(), 1F);
            }
        }
    }

    protected void addToEntityLootTable(String modifierName, EntityType<?> entityType, float dropChance, float lootMultiplier, Item item) {
        add(modifierName, new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(entityType.getDefaultLootTable()).build(),
                LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(dropChance, lootMultiplier).build()
        }, item));
    }

    protected void addToStructureLootTable(String modifierName, ResourceLocation resourceLocation, Item item, float probabilityChance) {
        add(modifierName, new AddItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(resourceLocation).build(),
                LootItemRandomChanceCondition.randomChance(probabilityChance).build()
        }, item));
    }
}
