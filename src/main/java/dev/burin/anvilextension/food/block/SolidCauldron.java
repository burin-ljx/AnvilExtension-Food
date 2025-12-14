package dev.burin.anvilextension.food.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.burin.anvilextension.food.init.ModCauldronInteraction;
import dev.dubhe.anvilcraft.block.better.BetterAbstractCauldronBlock;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class SolidCauldron extends BetterAbstractCauldronBlock {
    public static final MapCodec<SolidCauldron> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
        propertiesCodec(),
        ItemStack.CODEC.fieldOf("itemStack").forGetter((block) -> block.getItemStack().get())
    ).apply(instance, (properties, itemStack) -> new SolidCauldron(properties, () -> itemStack)));

    @Getter
    private final Supplier<ItemStack> itemStack;

    public SolidCauldron(Properties properties, Supplier<ItemStack> itemStack) {
        super(properties, ModCauldronInteraction.SOLID);
        this.itemStack = itemStack;
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return 0.9375;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }
        Vec3 center = pos.getCenter();
        ItemEntity itemEntity = new ItemEntity(level, center.x, center.y, center.z, this.itemStack.get());
        itemEntity.setDefaultPickUpDelay();
        itemEntity.setDeltaMovement(0, 0, 0);
        itemEntity.anvilcraft$setIsAdsorbable(false);
        level.addFreshEntity(itemEntity);
        level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
        return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
