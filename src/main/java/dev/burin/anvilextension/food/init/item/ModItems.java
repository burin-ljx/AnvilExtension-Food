package dev.burin.anvilextension.food.init.item;

import dev.burin.anvilextension.food.AnvilExtensionFoodMod;

public class ModItems {
    public static void register() {
        ModTablewareItems.register();
        AnvilExtensionFoodMod.LOGGER.info("AnvilExtension Food Mod Items loading.");
    }
}
