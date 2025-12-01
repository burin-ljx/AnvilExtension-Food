package dev.burin.anvilextension.food.init.item;

import dev.burin.anvilextension.food.Color;
import dev.burin.anvilextension.food.item.CupItem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ModDrinkItems {
    public static final List<ItemStack> COLORFUL_GLASS_MILKS = Util.make(new ObjectArrayList<>(), (list) -> {
        for (Color color : Color.values()) {
            ItemStack itemStack = CupItem.applyData(ModTablewareItems.COLORFUL_CUPS.get(color), 0, ModFoods.MILK);
            list.add(itemStack);
        }
    });
}
