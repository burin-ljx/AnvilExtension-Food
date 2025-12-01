package dev.burin.anvilextension.food.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class LangLoader {
    public static void init(RegistrateLangProvider provider) {
        ItemLangLoader.init(provider);
        CondensedCreativeGroupLangLoader.init(provider);
    }
}
