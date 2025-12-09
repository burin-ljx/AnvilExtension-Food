package dev.burin.anvilextension.food.client;

import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import dev.burin.anvilextension.food.Color;
import dev.burin.anvilextension.food.init.item.ModCreativeModeTabs;
import dev.burin.anvilextension.food.init.item.ModDrinkItems;
import dev.burin.anvilextension.food.init.item.ModItemTags;
import dev.burin.anvilextension.food.init.item.ModTablewareItems;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.init.item.ModItemGroups;
import dev.dubhe.anvilcraft.init.item.ModItems;
import io.wispforest.condensed_creative.registry.CondensedCreativeInitializer;
import io.wispforest.condensed_creative.registry.CondensedEntryRegistry;
import net.minecraft.Util;
import net.minecraft.core.RegistryAccess;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod(value = AnvilExtensionFoodMod.MOD_ID, dist = Dist.CLIENT)
@CondensedCreativeInitializer.InitializeCondensedEntries
public class AnvilExtensionFoodModClient implements CondensedCreativeInitializer {
    @Override
    public void registerCondensedEntries(boolean refreshed, RegistryAccess access) {
        // region anvilcraft tool
        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("pickaxes"),
            ModItems.AMETHYST_PICKAXE.asItem(),
            List.of(
                ModItems.AMETHYST_PICKAXE,
                ModItems.ROYAL_STEEL_PICKAXE,
                ModItems.FROST_METAL_PICKAXE,
                ModItems.EMBER_METAL_PICKAXE
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("axes"),
            ModItems.AMETHYST_AXE.asItem(),
            List.of(
                ModItems.AMETHYST_AXE,
                ModItems.ROYAL_STEEL_AXE,
                ModItems.FROST_METAL_AXE,
                ModItems.EMBER_METAL_AXE
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("swords"),
            ModItems.AMETHYST_SWORD.asItem(),
            List.of(
                ModItems.AMETHYST_SWORD,
                ModItems.ROYAL_STEEL_SWORD,
                ModItems.FROST_METAL_SWORD,
                ModItems.EMBER_METAL_SWORD
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("hoes"),
            ModItems.AMETHYST_HOE.asItem(),
            List.of(
                ModItems.AMETHYST_HOE,
                ModItems.ROYAL_STEEL_HOE,
                ModItems.FROST_METAL_HOE,
                ModItems.EMBER_METAL_HOE
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("shovels"),
            ModItems.AMETHYST_SHOVEL.asItem(),
            List.of(
                ModItems.AMETHYST_SHOVEL,
                ModItems.ROYAL_STEEL_SHOVEL,
                ModItems.FROST_METAL_SHOVEL,
                ModItems.EMBER_METAL_SHOVEL
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("hammers"),
            ModItems.ANVIL_HAMMER.asItem(),
            List.of(
                ModItems.ANVIL_HAMMER,
                ModItems.ROYAL_ANVIL_HAMMER,
                ModItems.EMBER_ANVIL_HAMMER,
                ModItems.TRANSCENDENCE_ANVIL_HAMMER
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("dragon_rods"),
            ModItems.AMETHYST_SHOVEL.asItem(),
            List.of(
                ModItems.DRAGON_ROD,
                ModItems.ROYAL_DRAGON_ROD,
                ModItems.EMBER_DRAGON_ROD,
                ModItems.TRANSCENDENCE_DRAGON_ROD
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("heavy_halberd"),
            ModItems.FROST_METAL_HEAVY_HALBERD.asItem(),
            List.of(
                ModItems.FROST_METAL_HEAVY_HALBERD,
                ModItems.EMBER_METAL_HEAVY_HALBERD,
                ModItems.TRANSCENDENCE_HEAVY_HALBERD
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("resonator"),
            ModItems.FROST_METAL_HEAVY_HALBERD.asItem(),
            List.of(
                ModItems.FROST_METAL_RESONATOR,
                ModItems.EMBER_METAL_RESONATOR,
                ModItems.TRANSCENDENCE_RESONATOR
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("smithing_templates"),
            ModItems.ROYAL_STEEL_UPGRADE_SMITHING_TEMPLATE.asItem(),
            List.of(
                ModItems.ROYAL_STEEL_UPGRADE_SMITHING_TEMPLATE,
                ModItems.FROST_METAL_UPGRADE_SMITHING_TEMPLATE,
                ModItems.EMBER_METAL_UPGRADE_SMITHING_TEMPLATE,
                ModItems.TRANSCENDIUM_UPGRADE_SMITHING_TEMPLATE,
                ModItems.TWO_TO_ONE_SMITHING_TEMPLATE,
                ModItems.FOUR_TO_ONE_SMITHING_TEMPLATE,
                ModItems.EIGHT_TO_ONE_SMITHING_TEMPLATE
            )
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());

        CondensedEntryRegistry.fromItems(
            AnvilCraft.of("amulets"),
            ModItems.EMERALD_AMULET.asItem(),
            Util.make(new ArrayList<>(), (list) -> {
                list.add(ModItems.EMERALD_AMULET);
                list.add(ModItems.TOPAZ_AMULET);
                list.add(ModItems.RUBY_AMULET);
                list.add(ModItems.SAPPHIRE_AMULET);
                list.add(ModItems.ANVIL_AMULET);
                list.add(ModItems.COMRADE_AMULET);
                list.add(ModItems.FEATHER_AMULET);
                list.add(ModItems.COMRADE_AMULET);
                list.add(ModItems.CAT_AMULET);
                list.add(ModItems.DOG_AMULET);
                list.add(ModItems.SILENCE_AMULET);
                list.add(ModItems.ABNORMAL_AMULET);
                list.add(ModItems.GEM_AMULET);
                list.add(ModItems.NATURE_AMULET);
            })
        ).addToItemGroup(ModItemGroups.ANVILCRAFT_TOOL.get());
        // endregion

        CondensedEntryRegistry.fromTag(
            AnvilExtensionFoodMod.of("glass_cup"),
            ModTablewareItems.COLORFUL_CUPS.get(Color.TRANSPARENT).asItem(),
            ModItemTags.GLASS_CUP
        ).addToItemGroup(ModCreativeModeTabs.TABLEWARE.get());

        CondensedEntryRegistry.fromItemStacks(
            AnvilExtensionFoodMod.of("glass_milk"),
            ModDrinkItems.COLORFUL_GLASS_MILKS.getFirst().getItem(),
            ModDrinkItems.COLORFUL_GLASS_MILKS
        ).addToItemGroup(ModCreativeModeTabs.DRINKS.get());

        CondensedEntryRegistry.fromItemStacks(
            AnvilExtensionFoodMod.of("glass_hot_cocoa"),
            ModDrinkItems.COLORFUL_GLASS_HOT_COCOAS.getFirst().getItem(),
            ModDrinkItems.COLORFUL_GLASS_HOT_COCOAS
        ).addToItemGroup(ModCreativeModeTabs.DRINKS.get());
    }
}
