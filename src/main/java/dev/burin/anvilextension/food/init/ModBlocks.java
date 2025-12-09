package dev.burin.anvilextension.food.init;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import dev.dubhe.anvilcraft.block.Layered4LevelCauldronBlock;
import dev.dubhe.anvilcraft.util.DataGenUtil;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import static dev.burin.anvilextension.food.AnvilExtensionFoodMod.REGISTRATE;

public class ModBlocks {
    public static final BlockEntry<Layered4LevelCauldronBlock> MILK_CAULDRON = REGISTRATE
        .block("milk_cauldron", (properties) -> new Layered4LevelCauldronBlock(properties, ModCauldronInteraction.MILK))
        .blockstate(DataGenUtil::noExtraModelOrState)
        .loot((tables, block) -> tables.dropOther(block, Items.CAULDRON))
        .tag(BlockTags.CAULDRONS)
        .onRegister(block -> Item.BY_BLOCK.put(block, Items.CAULDRON))
        .register();

    public static final BlockEntry<Layered4LevelCauldronBlock> HOT_COCOA_CAULDRON = REGISTRATE
        .block("hot_cocoa_cauldron", (properties) -> new Layered4LevelCauldronBlock(properties, ModCauldronInteraction.HOT_COCOA))
        .blockstate(DataGenUtil::noExtraModelOrState)
        .loot((tables, block) -> tables.dropOther(block, Items.CAULDRON))
        .tag(BlockTags.CAULDRONS)
        .onRegister(block -> Item.BY_BLOCK.put(block, Items.CAULDRON))
        .register();

    public static void register() {
        AnvilExtensionFoodMod.LOGGER.info("AnvilExtension Food Mod Blocks loading.");
    }
}
