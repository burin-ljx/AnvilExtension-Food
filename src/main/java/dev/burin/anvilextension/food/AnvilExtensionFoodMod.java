package dev.burin.anvilextension.food;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import dev.burin.anvilextension.food.data.lang.LangLoader;
import dev.burin.anvilextension.food.init.ModBlocks;
import dev.burin.anvilextension.food.init.ModInteractionMap;
import dev.burin.anvilextension.food.init.item.ModComponents;
import dev.burin.anvilextension.food.init.item.ModCreativeModeTabs;
import dev.burin.anvilextension.food.init.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
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
        ModBlocks.register();
        ModComponents.register(eventBus);

        NeoForgeMod.enableMilkFluid();

        registerEvents(eventBus);

        REGISTRATE.addDataGenerator(ProviderType.LANG, LangLoader::init);
        LOGGER.info("Mod loading");
    }

    private static void registerEvents(IEventBus eventBus) {
        eventBus.addListener(AnvilExtensionFoodMod::loadComplete);
    }

    private static void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(ModInteractionMap::initInteractionMap);
    }

    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
