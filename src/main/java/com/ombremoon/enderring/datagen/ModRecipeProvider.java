package com.ombremoon.enderring.datagen;

import com.ombremoon.enderring.Constants;
import com.ombremoon.enderring.common.init.blocks.BlockInit;
import com.ombremoon.enderring.common.init.item.EquipmentInit;
import com.ombremoon.enderring.common.init.item.ItemInit;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ItemInit.CLAY_POT.get()).define('X', Items.CLAY_BALL).define('#', Ingredient.of(Items.COAL, Items.CHARCOAL)).pattern(" X ").pattern("X#X").pattern(" X ").unlockedBy("read_missionary_cookbook_one", has(Items.CLAY_BALL)).save(pWriter);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemInit.CLAY_POT.get()), RecipeCategory.MISC, ItemInit.HARDENED_POT.get(), 0.3F, 200).unlockedBy("has_clay_pot", has(ItemInit.CLAY_POT.get())).save(pWriter);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ItemInit.HARDENED_POT.get()), RecipeCategory.MISC, ItemInit.CRACKED_POT.get(), 0.35F, 200).unlockedBy("has_hardened_pot", has(ItemInit.HARDENED_POT.get())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.HOLY_WATER.get()).requires(ItemInit.CRACKED_POT.get()).requires(Ingredient.of(Items.RED_MUSHROOM, Items.BROWN_MUSHROOM)).requires(BlockInit.TARNISHED_SUNFLOWER.get()).unlockedBy("read_missionary_cookbook_one", read(ItemInit.MISSIONARY_COOKBOOK_ONE.get())).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ItemInit.ROPED_HOLY_WATER.get()).requires(ItemInit.HOLY_WATER.get()).requires(Items.STRING).unlockedBy("read_missionary_cookbook_one", read(ItemInit.MISSIONARY_COOKBOOK_ONE.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemInit.INVIGORATING_CURED_MEAT.get()).define('X', Ingredient.of(Items.BEEF, Items.PORKCHOP, Items.CHICKEN, Items.MUTTON, Items.RABBIT)).define('#', BlockInit.GOLDEN_ROWA.get()).define('*', ItemInit.CRAB_EGGS.get()).define('^', ItemInit.LAND_OCTOPUS_OVARY.get()).pattern("*#^").pattern("#X#").unlockedBy("read_nomadic_cookbook_two", read(ItemInit.NOMADIC_WARRIOR_COOKBOOK_TWO.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ItemInit.INVIGORATING_CURED_WHITE_MEAT.get()).define('X', ItemInit.WHITE_FLESH_STRIP.get()).define('#', BlockInit.GOLDEN_ROWA.get()).define('*', ItemInit.CRAB_EGGS.get()).define('^', ItemInit.LAND_OCTOPUS_OVARY.get()).pattern("*#^").pattern("#X#").unlockedBy("read_nomadic_cookbook_two", read(ItemInit.NOMADIC_WARRIOR_COOKBOOK_TWO.get())).save(pWriter);
        boneArrows(pWriter, EquipmentInit.BONE_ARROW.get(), Items.FLINT);
        boneBolts(pWriter, EquipmentInit.BONE_BOLT.get(), Items.FLINT);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer,
                                     List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime,
                            pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, Constants.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    protected static void boneArrows(Consumer<FinishedRecipe> recipeConsumer, ItemLike resultItem, ItemLike itemLike) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItem).define('X', itemLike).define('S', Items.STICK).define('#', Items.FEATHER).pattern("X").pattern("S").pattern("#").unlockedBy("read_nomadic_cookbook_one", read(ItemInit.NOMADIC_WARRIOR_COOKBOOK_ONE.get())).save(recipeConsumer);
    }

    protected static void boneBolts(Consumer<FinishedRecipe> recipeConsumer, ItemLike resultItem, ItemLike itemLike) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, resultItem).define('X', itemLike).define('S', Items.STICK).pattern("X").pattern("S").unlockedBy("read_nomadic_cookbook_one", read(ItemInit.NOMADIC_WARRIOR_COOKBOOK_ONE.get())).save(recipeConsumer);
    }

    protected static ConsumeItemTrigger.TriggerInstance read(ItemLike itemLike) {
        return ConsumeItemTrigger.TriggerInstance.usedItem(itemLike);
    }
}
