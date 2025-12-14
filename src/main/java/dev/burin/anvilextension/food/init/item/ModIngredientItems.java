package dev.burin.anvilextension.food.init.item;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import dev.burin.anvilextension.food.init.ModCreativeModeTabs;
import dev.burin.anvilextension.food.init.block.ModBuildingBlocks;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import dev.dubhe.anvilcraft.util.DataGenUtil;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static dev.burin.anvilextension.food.AnvilExtensionFoodMod.REGISTRATE;

public class ModIngredientItems {
    static {
        REGISTRATE.defaultCreativeTab(ModCreativeModeTabs.INGREDIENT.getKey());
    }

    public static final ItemEntry<Item> SALT = REGISTRATE
        .item("salt", Item::new)
        .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ctx.get(), 9)
            .requires(ModBuildingBlocks.SALT_BLOCK)
            .unlockedBy(AnvilCraftDatagen.hasItem(ModBuildingBlocks.SALT_BLOCK), AnvilCraftDatagen.has(ModBuildingBlocks.SALT_BLOCK))
            .save(prov, AnvilExtensionFoodMod.of("shapeless/" + ctx.getName())))
        .register();

    public static final ItemEntry<Item> SEAWATER_BUCKET = REGISTRATE
        .item("seawater_bucket", Item::new)
        .properties((properties) -> properties.craftRemainder(Items.BUCKET).stacksTo(1))
        .model(DataGenUtil::noExtraModelOrState)
        .register();

    public static void register() {
        AnvilExtensionFoodMod.LOGGER.info("AnvilExtension Food Mod Ingredient Items loading.");
    }
}
