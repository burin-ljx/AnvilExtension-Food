package dev.burin.anvilextension.food;

import com.tterrag.registrate.Registrate;
import dev.burin.anvilextension.food.init.item.ModCreativeModeTabs;
import dev.burin.anvilextension.food.init.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.neoforged.fml.common.Mod;

@Mod(AnvilExtensionFoodMod.MOD_ID)
public class AnvilExtensionFoodMod {
    public static final String MOD_ID = "anvilextension_food";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final Registrate REGISTRATE = Registrate.create(MOD_ID);

    public AnvilExtensionFoodMod(IEventBus eventBus) {
        ModItems.register();
        ModCreativeModeTabs.register(eventBus);
        LOGGER.info("Mod loading");
    }

    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
