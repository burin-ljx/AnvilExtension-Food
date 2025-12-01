package dev.burin.anvilextension.food.init;

import com.tterrag.registrate.util.entry.BlockEntry;
import dev.dubhe.anvilcraft.util.DataGenUtil;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;

import static dev.burin.anvilextension.food.AnvilExtensionFoodMod.REGISTRATE;

public class ModBlocks {
    public static final BlockEntry<LayeredCauldronBlock> MILK_CAULDRON = REGISTRATE
        .block("milk_cauldron", (properties) -> new LayeredCauldronBlock(Biome.Precipitation.NONE, ModInteractionMap.MILK, properties))
        .initialProperties(() -> Blocks.WATER_CAULDRON)
        .blockstate(DataGenUtil::noExtraModelOrState)
        .loot((tables, block) -> tables.dropOther(block, Items.CAULDRON))
        .tag(BlockTags.CAULDRONS)
        .register();

    public static void register() {
    }
}
