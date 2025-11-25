package dev.burin.anvilextension.food;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.neoforged.fml.common.Mod;

@Mod(AnvilExtensionFoodMod.MOD_ID)
public class AnvilExtensionFoodMod {
    public static final String MOD_ID = "anvilextension_food";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public AnvilExtensionFoodMod() {
        LOGGER.info("Mod loading");
    }

    @Contract("_ -> new")
    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
