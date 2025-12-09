package dev.burin.anvilextension.food;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Map;
import java.util.function.Predicate;

@EventBusSubscriber
public class CauldronInteractionManager {
    private static final Map<Predicate<ItemStack>, CauldronInteraction> CAULDRON_INTERACTION_MAP = new Object2ObjectOpenHashMap<>();

    public static void register(Predicate<ItemStack> predicate, CauldronInteraction interaction) {
        CAULDRON_INTERACTION_MAP.put(predicate, interaction);
    }

    @SubscribeEvent
    public static void onCauldronInteraction(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        ItemStack itemStack = event.getItemStack();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        BlockHitResult hitResult = event.getHitVec();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();

        if (state.getBlock() instanceof AbstractCauldronBlock) {
            for (Map.Entry<Predicate<ItemStack>, CauldronInteraction> entry : CAULDRON_INTERACTION_MAP.entrySet()) {
                if (entry.getKey().test(itemStack)) {
                    event.setCancellationResult(entry.getValue().use(level, player, hand, pos, state, itemStack, hitResult));
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }

    public interface CauldronInteraction {
        InteractionResult use(
            Level level,
            Player player,
            InteractionHand hand,
            BlockPos pos,
            BlockState state,
            ItemStack itemStack,
            BlockHitResult hitResult
        );
    }
}
