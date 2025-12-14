package dev.burin.anvilextension.food.api;

import dev.burin.anvilextension.food.ItemPredicate;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.experimental.UtilityClass;
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

/**
 * 管理与炼药锅的交互事件的类。
 * 允许注册自定义物品与炼药锅的交互逻辑。
 */
@EventBusSubscriber
@UtilityClass
public class CauldronInteractionManager {
    /**
     * 存储物品谓词和对应炼药锅交互逻辑的映射表
     */
    private static final Map<ItemPredicate, CauldronInteraction> CAULDRON_INTERACTION_MAP = new Object2ObjectOpenHashMap<>();

    /**
     * 注册一个新的炼药锅交互逻辑
     *
     * @param predicate 用于判断物品是否适用此交互逻辑的谓词
     * @param interaction 对应的炼药锅交互逻辑实现
     */
    public static void register(ItemPredicate predicate, CauldronInteraction interaction) {
        CAULDRON_INTERACTION_MAP.put(predicate, interaction);
    }

    /**
     * 处理玩家右键点击炼药锅方块的事件
     *
     * @param event 玩家右键点击方块事件
     */
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
            for (Map.Entry<ItemPredicate, CauldronInteraction> entry : CAULDRON_INTERACTION_MAP.entrySet()) {
                if (entry.getKey().test(itemStack)) {
                    event.setCancellationResult(entry.getValue().use(level, player, hand, pos, state, itemStack, hitResult));
                    event.setCanceled(true);
                    break;
                }
            }
        }
    }

    /**
     * 炼药锅交互逻辑的接口
     */
    public interface CauldronInteraction {
        /**
         * 执行炼药锅交互逻辑
         *
         * @param level 当前世界
         * @param player 交互的玩家
         * @param hand 使用的手（主手或副手）
         * @param pos 点击的方块位置
         * @param state 被点击的方块状态
         * @param itemStack 使用的物品堆
         * @param hitResult 点击结果信息
         * @return 交互结果
         */
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
