package dev.burin.anvilextension.food.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class ItemLangLoader {
    public static void init(RegistrateLangProvider provider) {
        provider.add("item.cup.0", "Glass of Milk");
        provider.add("item.cup.1", "Glass of Hot Cocoa");
    }
}
