package dev.burin.anvilextension.food.init.block;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import dev.burin.anvilextension.food.init.ModCreativeModeTabs;
import dev.burin.anvilextension.food.init.item.ModIngredientItems;
import dev.dubhe.anvilcraft.data.AnvilCraftDatagen;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;

import static dev.burin.anvilextension.food.AnvilExtensionFoodMod.REGISTRATE;

public class ModBuildingBlocks {
    static {
        REGISTRATE.defaultCreativeTab(ModCreativeModeTabs.BUILDING_BLOCK.getKey());
    }

    public static final BlockEntry<Block> SALT_BLOCK = REGISTRATE
        .block("salt_block", Block::new)
        .lang("Block of Salt")
        .properties((properties) -> properties.mapColor(DyeColor.WHITE)
            .requiresCorrectToolForDrops()
            .strength(1.5F, 6.0F))
        .simpleItem()
        .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ctx.get())
            .pattern("AAA")
            .pattern("AAA")
            .pattern("AAA")
            .define('A', ModIngredientItems.SALT)
            .unlockedBy(AnvilCraftDatagen.hasItem(ModIngredientItems.SALT), AnvilCraftDatagen.has(ModIngredientItems.SALT))
            .save(prov, AnvilExtensionFoodMod.of("shaped/" + ctx.getName())))
        .register();

    public static void register() {
        AnvilExtensionFoodMod.LOGGER.info("AnvilExtension Food Mod Building Blocks loading.");
    }
}
