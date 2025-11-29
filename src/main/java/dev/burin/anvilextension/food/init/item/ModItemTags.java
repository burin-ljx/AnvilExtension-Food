package dev.burin.anvilextension.food.init.item;

import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
    public static final TagKey<Item> GLASS_CUP = bind("glass_cup");

    private static TagKey<Item> bind(String id) {
        return TagKey.create(Registries.ITEM, AnvilExtensionFoodMod.of(id));
    }
}
