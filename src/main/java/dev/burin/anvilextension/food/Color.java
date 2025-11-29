package dev.burin.anvilextension.food;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import lombok.Getter;
import net.minecraft.Util;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Map;

@Getter
public enum Color implements StringRepresentable {
    TRANSPARENT("transparent"),
    WHITE("white"),
    LIGHT_GRAY("light_gray"),
    GRAY("gray"),
    BLACK("black"),
    BROWN("brown"),
    RED("red"),
    ORANGE("orange"),
    YELLOW("yellow"),
    LIME("lime"),
    GREEN("green"),
    CYAN("cyan"),
    LIGHT_BLUE("light_blue"),
    BLUE("blue"),
    PURPLE("purple"),
    MAGENTA("magenta"),
    PINK("pink");

    public static final Map<Color, Item> COLORFUL_GLASS = Util.make(new Object2ObjectLinkedOpenHashMap<>(), (map) -> {
        map.put(Color.TRANSPARENT, Items.GLASS);
        map.put(Color.WHITE, Items.WHITE_STAINED_GLASS);
        map.put(Color.LIGHT_GRAY, Items.WHITE_STAINED_GLASS);
        map.put(Color.GRAY, Items.GRAY_STAINED_GLASS);
        map.put(Color.BLACK, Items.BLACK_STAINED_GLASS);
        map.put(Color.BROWN, Items.BROWN_STAINED_GLASS);
        map.put(Color.RED, Items.RED_STAINED_GLASS);
        map.put(Color.ORANGE, Items.ORANGE_STAINED_GLASS);
        map.put(Color.YELLOW, Items.YELLOW_STAINED_GLASS);
        map.put(Color.LIME, Items.LIME_STAINED_GLASS);
        map.put(Color.GREEN, Items.GREEN_STAINED_GLASS);
        map.put(Color.CYAN, Items.CYAN_STAINED_GLASS);
        map.put(Color.LIGHT_BLUE, Items.LIGHT_BLUE_STAINED_GLASS);
        map.put(Color.BLUE, Items.BLUE_STAINED_GLASS);
        map.put(Color.PURPLE, Items.PURPLE_STAINED_GLASS);
        map.put(Color.MAGENTA, Items.MAGENTA_STAINED_GLASS);
        map.put(Color.PINK, Items.PINK_STAINED_GLASS);
    });
    public static final Map<Color, Item> COLORFUL_GLASS_PANE = Util.make(new Object2ObjectLinkedOpenHashMap<>(), (map) -> {
        map.put(Color.TRANSPARENT, Items.GLASS_PANE);
        map.put(Color.WHITE, Items.WHITE_STAINED_GLASS_PANE);
        map.put(Color.LIGHT_GRAY, Items.WHITE_STAINED_GLASS_PANE);
        map.put(Color.GRAY, Items.GRAY_STAINED_GLASS_PANE);
        map.put(Color.BLACK, Items.BLACK_STAINED_GLASS_PANE);
        map.put(Color.BROWN, Items.BROWN_STAINED_GLASS_PANE);
        map.put(Color.RED, Items.RED_STAINED_GLASS_PANE);
        map.put(Color.ORANGE, Items.ORANGE_STAINED_GLASS_PANE);
        map.put(Color.YELLOW, Items.YELLOW_STAINED_GLASS_PANE);
        map.put(Color.LIME, Items.LIME_STAINED_GLASS_PANE);
        map.put(Color.GREEN, Items.GREEN_STAINED_GLASS_PANE);
        map.put(Color.CYAN, Items.CYAN_STAINED_GLASS_PANE);
        map.put(Color.LIGHT_BLUE, Items.LIGHT_BLUE_STAINED_GLASS_PANE);
        map.put(Color.BLUE, Items.BLUE_STAINED_GLASS_PANE);
        map.put(Color.PURPLE, Items.PURPLE_STAINED_GLASS_PANE);
        map.put(Color.MAGENTA, Items.MAGENTA_STAINED_GLASS_PANE);
        map.put(Color.PINK, Items.PINK_STAINED_GLASS_PANE);
    });

    private final String name;

    Color(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return super.name();
    }
}
