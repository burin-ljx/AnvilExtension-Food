package dev.burin.anvilextension.food.init;

import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import dev.burin.anvilextension.food.CauldronInteractionManager;
import dev.burin.anvilextension.food.init.item.ModComponents;
import dev.burin.anvilextension.food.init.item.ModFoods;
import dev.burin.anvilextension.food.init.item.ModItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class ModCauldronInteraction {
    public static final CauldronInteraction.InteractionMap MILK = CauldronInteraction.newInteractionMap("milk");
    public static final CauldronInteraction.InteractionMap HOT_COCOA = CauldronInteraction.newInteractionMap("hot_cocoa");

    public static void register() {
        var milkMap = MILK.map();
        milkMap.put(
            Items.BUCKET,
            (state, level, pos, player, hand, stack) -> CauldronInteraction.fillBucket(
                state, level, pos, player, hand, stack, Items.MILK_BUCKET.getDefaultInstance(),
                (bs) -> ModBlocks.MILK_CAULDRON.get().isFull(state),
                SoundEvents.BUCKET_FILL
            )
        );
        var emptyMap = CauldronInteraction.EMPTY.map();
        emptyMap.put(
            Items.MILK_BUCKET.getDefaultInstance().getItem(),
            (state, level, pos, player, hand, stack) -> {
                if (state.is(Blocks.CAULDRON)) {
                    return CauldronInteraction.emptyBucket(
                        level, pos, player, hand, stack,
                        ModBlocks.MILK_CAULDRON.getDefaultState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY
                    );
                }
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        );

        // 空杯右键满锅
        CauldronInteractionManager.register(
            (stack) -> stack.is(ModItemTags.GLASS_CUP) && stack.get(ModComponents.EXTRA_DATA) == null,
            (level, player, hand, pos, state, stack, hitResult) -> switch (state) {
                case BlockState bs when bs.is(ModBlocks.MILK_CAULDRON) -> fullCup(level, player, hand, pos, state, stack, 0, ModFoods.MILK);
                case BlockState bs when bs.is(ModBlocks.HOT_COCOA_CAULDRON) -> fullCup(level, player, hand, pos, state, stack, 1, ModFoods.HOT_COCOA);
                default -> InteractionResult.PASS;
            }
        );

        // 满杯右键空锅
        CauldronInteractionManager.register(
            (stack) -> stack.is(ModItemTags.GLASS_CUP) && stack.getOrDefault(ModComponents.EXTRA_DATA, -1) == 0,
            (level, player, hand, pos, state, stack, hitResult) -> switch (state) {
                case BlockState bs when bs.is(Blocks.CAULDRON) ->
                    emptyCup(level, player, hand, pos, stack, ModBlocks.MILK_CAULDRON.getDefaultState());
                case BlockState bs when bs.is(ModBlocks.MILK_CAULDRON) && bs.getValue(LayeredCauldronBlock.LEVEL) < 3 ->
                    emptyCup(level, player, hand, pos, stack, state.cycle(LayeredCauldronBlock.LEVEL));
                default -> InteractionResult.PASS;
            }
        );

        AnvilExtensionFoodMod.LOGGER.info("AnvilExtension Food Mod Cauldron Interactions loading.");
    }

    private static InteractionResult fullCup(Level level, Player player, InteractionHand hand, BlockPos pos, BlockState state, ItemStack stack, int extraDta, FoodProperties food) {
        ItemStack copied = stack.copyWithCount(1);
        copied.set(ModComponents.EXTRA_DATA, extraDta);
        copied.set(DataComponents.FOOD, food);
        player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, copied, false));
        player.awardStat(Stats.USE_CAULDRON);
        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        LayeredCauldronBlock.lowerFillLevel(state, level, pos);
        level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static InteractionResult emptyCup(Level level, Player player, InteractionHand hand, BlockPos pos, ItemStack stack, BlockState newCauldronState) {
        ItemStack copied = stack.copyWithCount(1);
        copied.remove(ModComponents.EXTRA_DATA);
        player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, copied, false));
        player.awardStat(Stats.USE_CAULDRON);
        player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
        level.setBlockAndUpdate(pos, newCauldronState);
        level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
