package dev.burin.anvilextension.food.init;

import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import dev.burin.anvilextension.food.recipe.CookingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTER =
        DeferredRegister.create(Registries.RECIPE_TYPE, AnvilExtensionFoodMod.MOD_ID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTER =
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, AnvilExtensionFoodMod.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<CookingRecipe>> COOKING_RECIPE_TYPE =
        registerType("food_cooking");
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CookingRecipe>> COOKING_RECIPE_SERIALIZER =
        RECIPE_SERIALIZER_REGISTER.register("food_cooking", CookingRecipe.Serializer::new);

    private static <T extends Recipe<?>> DeferredHolder<RecipeType<?>, RecipeType<T>> registerType(String name) {
        return RECIPE_TYPE_REGISTER.register(name, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return AnvilExtensionFoodMod.of(name).toString();
            }
        });
    }

    public static void register(IEventBus eventBus) {
        RECIPE_TYPE_REGISTER.register(eventBus);
        RECIPE_SERIALIZER_REGISTER.register(eventBus);
        AnvilExtensionFoodMod.LOGGER.info("AnvilExtension Food Mod Recipe Types loading.");
    }
}
