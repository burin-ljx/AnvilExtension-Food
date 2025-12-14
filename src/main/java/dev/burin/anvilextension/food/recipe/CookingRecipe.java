package dev.burin.anvilextension.food.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.anvilcraft.lib.recipe.component.BlockStatePredicate;
import dev.anvilcraft.lib.recipe.component.ChanceItemStack;
import dev.anvilcraft.lib.recipe.component.ItemIngredientPredicate;
import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import dev.burin.anvilextension.food.init.ModRecipeTypes;
import dev.dubhe.anvilcraft.recipe.anvil.predicate.block.HasCauldron;
import dev.dubhe.anvilcraft.recipe.anvil.util.WrapUtils;
import dev.dubhe.anvilcraft.recipe.anvil.wrap.AbstractProcessRecipe;
import dev.dubhe.anvilcraft.recipe.component.HasCauldronSimple;
import net.minecraft.core.Vec3i;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CookingRecipe extends AbstractProcessRecipe<CookingRecipe> {
    public CookingRecipe(
        List<ItemIngredientPredicate> itemIngredients,
        List<ChanceItemStack> results,
        HasCauldronSimple hasCauldron
    ) {
        super(
            new Property()
                .setItemInputOffset(new Vec3(0, -0.375, 0))
                .setItemInputRange(new Vec3(0.75, 0.75, 0.75))
                .setInputItems(itemIngredients)
                .setItemOutputOffset(new Vec3(0, -0.75, 0))
                .setResultItems(results)
                .setCauldronOffset(new Vec3i(0, -1, 0))
                .setHasCauldron(hasCauldron)
                .setBlockInputOffset(new Vec3i(0, -2, 0))
                .setInputBlocks(
                    BlockStatePredicate.builder()
                        .of(Blocks.CAMPFIRE)
                        .with(CampfireBlock.LIT, true)
                        .build()
                )
                .setPriority(1)
        );
    }

    @Override
    public RecipeSerializer<CookingRecipe> getSerializer() {
        return ModRecipeTypes.COOKING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<CookingRecipe> getType() {
        return ModRecipeTypes.COOKING_RECIPE_TYPE.get();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends SimpleAbstractBuilder<CookingRecipe, Builder> {
        private final HasCauldronSimple.Builder hasCauldron = HasCauldronSimple.empty();

        public Builder cauldron(ResourceLocation fluid) {
            this.hasCauldron.fluid(fluid);
            return this;
        }

        public Builder cauldron(Block cauldron) {
            this.cauldron(WrapUtils.cauldron2Fluid(cauldron));
            return this;
        }

        public Builder transform(ResourceLocation transform) {
            this.hasCauldron.transform(transform);
            return this;
        }

        public Builder transform(Block cauldron) {
            this.transform(WrapUtils.cauldron2Fluid(cauldron));
            return this;
        }

        public Builder produce(int produce) {
            if (produce <= 0) {
                return this;
            }
            this.hasCauldron.consume(-produce);
            return this;
        }

        public Builder consume(int consume) {
            if (consume <= 0) {
                return this;
            }
            this.hasCauldron.consume(consume);
            return this;
        }

        @Override
        protected CookingRecipe of(List<ItemIngredientPredicate> itemIngredients, List<ChanceItemStack> results) {
            return new CookingRecipe(itemIngredients, results, this.hasCauldron.build());
        }

        @Override
        public void validate(ResourceLocation id) {
            if (itemIngredients.isEmpty() && this.hasCauldron.build().fluid().equals(HasCauldron.EMPTY)) {
                throw new IllegalArgumentException("CookingRecipe: " + id + " has no ingredients or no cauldron");
            }

            if (results.isEmpty() && this.hasCauldron.build().transform().equals(HasCauldron.NULL)) {
                throw new IllegalArgumentException("CookingRecipe: " + id + " has no results or no transform cauldron");
            }
        }

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public String getType() {
            return "food_cooking";
        }

        @Override
        public void save(RecipeOutput recipeOutput, String id) {
            save(recipeOutput, AnvilExtensionFoodMod.of(getType() + "/" + id));
        }
    }

    public static class Serializer implements RecipeSerializer<CookingRecipe> {
        public static final MapCodec<CookingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            ItemIngredientPredicate.CODEC.listOf().fieldOf("ingredients").forGetter(CookingRecipe::getInputItems),
            ChanceItemStack.CODEC.listOf().fieldOf("results").forGetter(CookingRecipe::getResultItems),
            HasCauldronSimple.CODEC.forGetter(CookingRecipe::getHasCauldron)
        ).apply(instance, CookingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CookingRecipe> STREAM_CODEC = StreamCodec.composite(
            ItemIngredientPredicate.STREAM_CODEC.apply(ByteBufCodecs.list()),
            CookingRecipe::getInputItems,
            ChanceItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
            CookingRecipe::getResultItems,
            HasCauldronSimple.STREAM_CODEC,
            CookingRecipe::getHasCauldron,
            CookingRecipe::new
        );

        @Override
        public MapCodec<CookingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CookingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
