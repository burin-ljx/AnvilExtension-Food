package dev.burin.anvilextension.food.data.recipe;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;

public class RecipeLoader {
    public static void init(RegistrateRecipeProvider provider) {
        CookingRecipeLoader.init(provider);
    }
}
