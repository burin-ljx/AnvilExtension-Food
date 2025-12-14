package dev.burin.anvilextension.food.data.recipe;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import dev.burin.anvilextension.food.init.block.ModCauldronBlocks;
import dev.burin.anvilextension.food.recipe.CookingRecipe;
import dev.dubhe.anvilcraft.init.item.ModFoodItems;
import net.minecraft.world.item.Items;

public class CookingRecipeLoader {
    public static void init(RegistrateRecipeProvider provider) {
        CookingRecipe.builder()
            .requires(ModFoodItems.COCOA_POWDER, 4)
            .requires(Items.SUGAR)
            .cauldron(ModCauldronBlocks.MILK_CAULDRON.get())
            .transform(ModCauldronBlocks.HOT_COCOA_CAULDRON.get())
            .save(provider, "hot_cocoa_from_cocoa_powder");

        CookingRecipe.builder()
            .requires(ModFoodItems.CHOCOLATE)
            .cauldron(ModCauldronBlocks.MILK_CAULDRON.get())
            .transform(ModCauldronBlocks.HOT_COCOA_CAULDRON.get())
            .save(provider, "hot_cocoa_from_chocolate");

        CookingRecipe.builder()
            .cauldron(ModCauldronBlocks.SEAWATER_CAULDRON.get())
            .transform(ModCauldronBlocks.SALT_CAULDRON.get())
            .save(provider);
    }
}
