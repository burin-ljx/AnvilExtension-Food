package dev.burin.anvilextension.food.init.item;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.burin.anvilextension.food.Color;
import dev.burin.anvilextension.food.item.CupItem;
import dev.dubhe.anvilcraft.util.DataGenUtil;
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

    public static final Map<Color, ItemEntry<CupItem>> COLORFUL_CUPS = registerColorfulCupItem(CupItem::new);

    private static Map<Color, ItemEntry<CupItem>> registerColorfulCupItem(NonNullFunction<Item.Properties, CupItem> factory) {
        Map<Color, ItemEntry<CupItem>> map = new Object2ObjectLinkedOpenHashMap<>();
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
                    .model(DataGenUtil::noExtraModelOrState)
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
