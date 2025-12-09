package dev.burin.anvilextension.food.init.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties MILK = new FoodProperties.Builder()
        .nutrition(4)
        .saturationModifier(0.7f)
        .build();

    public static final FoodProperties HOT_COCOA = new FoodProperties.Builder()
        .nutrition(5)
        .saturationModifier(1f)
        .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 1, true, true), 1f)
        .effect(() -> new MobEffectInstance(MobEffects.JUMP, 3600, 0, true, true), 1f)
        .build();
}
