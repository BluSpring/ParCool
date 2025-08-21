package com.alrex.parcool.common.item.recipe;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.common.item.recipe.special.ZiplineRopeDyeRecipe;
import io.github.fabricators_of_create.porting_lib.registry.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

import java.util.function.Supplier;

public class Recipes {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ParCool.MOD_ID);
    public static final Supplier<RecipeSerializer<ZiplineRopeDyeRecipe>> ZIPLINE_ROPE_DYE = RECIPES.register("zipline_rope_dye", () -> new SimpleCraftingRecipeSerializer<>(ZiplineRopeDyeRecipe::new));

    public static void registerAll() {
        RECIPES.register();
    }
}
