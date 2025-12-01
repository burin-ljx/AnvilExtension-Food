package dev.burin.anvilextension.food.init.item;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties MILK = new FoodProperties.Builder()
        .nutrition(4)
        .saturationModifier(0.7f)
        .build();
}
