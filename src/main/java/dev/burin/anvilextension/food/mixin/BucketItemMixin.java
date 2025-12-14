package dev.burin.anvilextension.food.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.burin.anvilextension.food.init.item.ModIngredientItems;
import dev.dubhe.anvilcraft.util.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin {
    @Inject(
        method = "use",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/BucketPickup;"
                + "pickupBlock(Lnet/minecraft/world/entity/player/Player;"
                + "Lnet/minecraft/world/level/LevelAccessor;"
                + "Lnet/minecraft/core/BlockPos;"
                + "Lnet/minecraft/world/level/block/state/BlockState;)"
                + "Lnet/minecraft/world/item/ItemStack;",
            shift = At.Shift.AFTER
        ),
        cancellable = true
    )
    public void use(
        Level level,
        Player player,
        InteractionHand hand,
        CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir,
        @Local ItemStack itemStack,
        @Local(ordinal = 0) BlockPos blockPos,
        @Local BucketPickup bucketPickup,
        @Local BlockState blockState
    ) {
        if (level.getBiome(blockPos).is(BiomeTags.IS_OCEAN)) {
            player.awardStat(Stats.ITEM_USED.get(Util.cast(this)));
            bucketPickup.getPickupSound(blockState).ifPresent((event) -> player.playSound(event, 1.0f, 1.0f));
            level.gameEvent(player, GameEvent.FLUID_PICKUP, blockPos);
            ItemStack filledResult = ItemUtils.createFilledResult(itemStack, player, ModIngredientItems.SEAWATER_BUCKET.asStack());
            if (!level.isClientSide) {
                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, filledResult);
            }
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(filledResult, level.isClientSide()));
        }
    }
}
