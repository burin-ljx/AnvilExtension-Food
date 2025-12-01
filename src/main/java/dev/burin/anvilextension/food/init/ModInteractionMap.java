package dev.burin.anvilextension.food.init;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.burin.anvilextension.food.Color;
import dev.burin.anvilextension.food.init.item.ModComponents;
import dev.burin.anvilextension.food.init.item.ModFoods;
import dev.burin.anvilextension.food.init.item.ModTablewareItems;
import dev.burin.anvilextension.food.item.CupItem;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Map;

public class ModInteractionMap {
    public static final CauldronInteraction.InteractionMap MILK = CauldronInteraction.newInteractionMap("milk");

    public static void initInteractionMap() {
        bucketInteraction(MILK, Items.MILK_BUCKET.getDefaultInstance(), ModBlocks.MILK_CAULDRON.getDefaultState().setValue(LayeredCauldronBlock.LEVEL, 3));
        for (Map.Entry<Color, ItemEntry<CupItem>> entry : ModTablewareItems.COLORFUL_CUPS.entrySet()) {
            glassCupInteraction(MILK, entry.getValue().asStack());
        }
    }

    private static void bucketInteraction(CauldronInteraction.InteractionMap map, ItemStack fullBucketItem, BlockState blockState) {
        var milkMap = map.map();
        milkMap.put(
            Items.BUCKET,
            (state, level, pos, player, hand, stack) -> CauldronInteraction.fillBucket(
                state, level, pos, player, hand, stack, fullBucketItem,
                (bs) -> ModBlocks.MILK_CAULDRON.get().isFull(state),
                SoundEvents.BUCKET_FILL
            )
        );
        var emptyMap = CauldronInteraction.EMPTY.map();
        emptyMap.put(
            fullBucketItem.getItem(),
            (state, level, pos, player, hand, stack) -> {
                if (state.is(Blocks.CAULDRON)) {
                    return CauldronInteraction.emptyBucket(
                        level, pos, player, hand, stack,
                        blockState,
                        SoundEvents.BUCKET_EMPTY
                    );
                }
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        );
    }

    private static void glassCupInteraction(CauldronInteraction.InteractionMap map, ItemStack emptyCupItem) {
        var milkMap = map.map();
        ItemStack fullCupItem = emptyCupItem.copy();
        fullCupItem.set(ModComponents.EXTRA_DATA, 0);
        fullCupItem.set(DataComponents.FOOD, ModFoods.MILK);
        fullCupItem.setCount(1);
        milkMap.put(
            emptyCupItem.getItem(),
            (state, level, pos, player, hand, stack) -> {
                if (!level.isClientSide()) {
                    int extraData = stack.getOrDefault(ModComponents.EXTRA_DATA, -1);
                    if (extraData == -1) {
                        // 空杯
                        player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, fullCupItem.copy(), false));
                        player.awardStat(Stats.USE_CAULDRON);
                        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                        LayeredCauldronBlock.lowerFillLevel(state, level, pos);
                        level.playSound(
                            null,
                            pos,
                            SoundEvents.BOTTLE_FILL,
                            SoundSource.BLOCKS,
                            1,
                            1
                        );
                        level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
                        return ItemInteractionResult.sidedSuccess(level.isClientSide());
                    } else if (extraData == 0) {
                        // 满杯
                        if (state.getValue(LayeredCauldronBlock.LEVEL) == 3) {
                            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                        }
                        player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, emptyCupItem.copy()));
                        player.awardStat(Stats.USE_CAULDRON);
                        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                        level.setBlockAndUpdate(pos, state.cycle(LayeredCauldronBlock.LEVEL));
                        level.playSound(
                            null,
                            pos,
                            SoundEvents.BOTTLE_EMPTY,
                            SoundSource.BLOCKS,
                            1,
                            1
                        );
                        level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
                        return ItemInteractionResult.sidedSuccess(level.isClientSide());
                    }
                }
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        );
        var emptyMap = CauldronInteraction.EMPTY.map();
        emptyMap.put(
            emptyCupItem.getItem(),
            (state, level, pos, player, hand, stack) -> {
                int extraData = stack.getOrDefault(ModComponents.EXTRA_DATA, -1);
                if (extraData == 0) {
                    if (!level.isClientSide()) {
                        player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, emptyCupItem.copy()));
                        player.awardStat(Stats.USE_CAULDRON);
                        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                        level.setBlockAndUpdate(pos, ModBlocks.MILK_CAULDRON.getDefaultState());
                        level.playSound(
                            null,
                            pos,
                            SoundEvents.BOTTLE_EMPTY,
                            SoundSource.BLOCKS,
                            1,
                            1
                        );
                        level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
                    }
                    return ItemInteractionResult.sidedSuccess(level.isClientSide());
                } else {
                    return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
            }
        );
    }
}
