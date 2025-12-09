package dev.burin.anvilextension.food.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SolidCauldron extends Block {
    public static final MapCodec<SolidCauldron> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
        propertiesCodec(),
        ItemStack.CODEC.fieldOf("dropItem").forGetter(SolidCauldron::getItemStack)
    ).apply(instance, SolidCauldron::new));
    private static final VoxelShape INSIDE = box(2.0, 15.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape SHAPE = Shapes.join(
        Shapes.block(),
        Shapes.or(box(0.0, 0.0, 4.0, 16.0, 3.0, 12.0),
            box(4.0, 0.0, 0.0, 12.0, 3.0, 16.0),
            box(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), INSIDE),
        BooleanOp.ONLY_FIRST
    );

    @Getter
    private final ItemStack itemStack;

    public SolidCauldron(Properties properties, ItemStack itemStack) {
        super(properties);
        this.itemStack = itemStack;
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        Vec3 center = pos.getCenter();
        ItemEntity itemEntity = new ItemEntity(level, center.x, center.y, center.z , itemStack);
        itemEntity.setDefaultPickUpDelay();
        itemEntity.setDeltaMovement(0, 0, 0);
        itemEntity.anvilcraft$setIsAdsorbable(false);
        level.addFreshEntity(itemEntity);
        level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
