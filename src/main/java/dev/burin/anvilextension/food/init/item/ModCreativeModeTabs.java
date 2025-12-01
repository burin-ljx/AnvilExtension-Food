package dev.burin.anvilextension.food.init.item;

import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import dev.burin.anvilextension.food.Color;
import dev.dubhe.anvilcraft.init.item.ModItemGroups;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static dev.burin.anvilextension.food.AnvilExtensionFoodMod.REGISTRATE;
import static dev.burin.anvilextension.food.AnvilExtensionFoodMod.of;

public class ModCreativeModeTabs {
    private static final DeferredRegister<CreativeModeTab> REGISTER =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AnvilExtensionFoodMod.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TABLEWARE = REGISTER
        .register("tableware", () -> CreativeModeTab.builder()
            .icon(ModTablewareItems.COLORFUL_CUPS.get(Color.TRANSPARENT)::asStack)
            .title(REGISTRATE.addLang("itemGroup", of("tableware"), "AnvilExtension: Food | Tableware"))
            .withTabsBefore(ModItemGroups.ANVILCRAFT_BUILD_BLOCK.getId())
            .withTabsAfter(of("drinks"))
            .build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DRINKS = REGISTER
        .register("drinks", () -> CreativeModeTab.builder()
            .icon(ModDrinkItems.COLORFUL_GLASS_MILKS::getFirst)
            .title(REGISTRATE.addLang("itemGroup", of("drinks"), "AnvilExtension: Food | Drinks"))
            .displayItems((parameters, output) -> {
                output.acceptAll(ModDrinkItems.COLORFUL_GLASS_MILKS);
            })
            .withTabsBefore(ModItemGroups.ANVILCRAFT_BUILD_BLOCK.getId(), TABLEWARE.getId())
            .build());

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
