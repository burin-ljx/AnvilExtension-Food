package dev.burin.anvilextension.food.init.item;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.burin.anvilextension.food.Color;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;

import java.util.Map;

import static dev.burin.anvilextension.food.AnvilExtensionFoodMod.REGISTRATE;

public class ModTablewareItems {
    static {
        REGISTRATE.defaultCreativeTab(ModCreativeModeTabs.TABLEWARE.getKey());
    }

    public static final Map<Color, ItemEntry<Item>> COLORFUL_CUPS = registerColorfulCupItem(Item::new);

    private static Map<Color, ItemEntry<Item>> registerColorfulCupItem(NonNullFunction<Item.Properties, Item> factory) {
        Map<Color, ItemEntry<Item>> map = new Object2ObjectLinkedOpenHashMap<>();
        for (Map.Entry<Color, Item> entry : Color.COLORFUL_GLASS_PANE.entrySet()) {
            String color = entry.getKey().getName() + "_";
            if (entry.getKey() == Color.TRANSPARENT) {
                color = "";
            }
            map.put(
                entry.getKey(),
                REGISTRATE
                    .item(color + "glass_cup", factory)
                    .tag(ModItemTags.GLASS_CUP)
                    .recipe((ctx, prov) -> ShapedRecipeBuilder
                        .shaped(RecipeCategory.MISC, ctx.get())
                        .pattern("A A")
                        .pattern(" A ")
                        .define('A', entry.getValue())
                        .unlockedBy("has_item", RegistrateRecipeProvider.has(entry.getValue()))
                        .save(prov))
                    .register()
            );
        }
        return map;
    }

    public static void register() {
    }
}
