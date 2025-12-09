package dev.burin.anvilextension.food.item;

import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import dev.burin.anvilextension.food.init.item.ModComponents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class CupItem extends Item {
    public CupItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("removal")
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        ItemProperties.register(
            this,
            AnvilExtensionFoodMod.of("extra_data"),
            (stack, level, entity, seed) ->
                stack.getOrDefault(ModComponents.EXTRA_DATA, -1)
        );
    }

    public static ItemStack applyData(ItemLike itemLike, int extraData) {
        ItemStack itemStack = itemLike.asItem().getDefaultInstance();
        itemStack.set(ModComponents.EXTRA_DATA, extraData);
        return itemStack;
    }

    public static ItemStack applyData(ItemLike itemLike, int extraData, FoodProperties foodProperties) {
        ItemStack itemStack = itemLike.asItem().getDefaultInstance();
        itemStack.set(ModComponents.EXTRA_DATA, extraData);
        itemStack.set(DataComponents.FOOD, foodProperties);
        return itemStack;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        int extraData = stack.getOrDefault(ModComponents.EXTRA_DATA, -1);
        if (extraData > -1) {
            return "item.cup." + extraData;
        }
        return super.getDescriptionId(stack);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide) {
            int extraData = stack.getOrDefault(ModComponents.EXTRA_DATA, -1);
            if (extraData == 0) {
                livingEntity.removeEffectsCuredBy(net.neoforged.neoforge.common.EffectCures.MILK);
            }
            FoodProperties foodProperties = stack.getFoodProperties(livingEntity);
            if (foodProperties != null) {
                level.playSound(
                    null,
                    livingEntity.getX(),
                    livingEntity.getY(),
                    livingEntity.getZ(),
                    livingEntity.getEatingSound(stack),
                    SoundSource.NEUTRAL,
                    1.0F,
                    1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F
                );
                for (FoodProperties.PossibleEffect effect : foodProperties.effects()) {
                    if (livingEntity.level().random.nextFloat() < effect.probability()) {
                        livingEntity.addEffect(effect.effect());
                    }
                }
            }
        }

        if (livingEntity instanceof Player player) {
            return ItemUtils.createFilledResult(stack, player, getDefaultInstance(), false);
        } else {
            stack.consume(1, livingEntity);
            return stack;
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide()) {
            ItemStack itemStack = player.getItemInHand(usedHand);
            int extraData = itemStack.getOrDefault(ModComponents.EXTRA_DATA, -1);
            if (extraData > -1) {
                return ItemUtils.startUsingInstantly(level, player, usedHand);
            }
        }
        return super.use(level, player, usedHand);
    }
}
