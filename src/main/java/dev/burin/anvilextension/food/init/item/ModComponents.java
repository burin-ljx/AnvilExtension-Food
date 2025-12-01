package dev.burin.anvilextension.food.init.item;

import com.mojang.serialization.Codec;
import dev.burin.anvilextension.food.AnvilExtensionFoodMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> REGISTER =
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, AnvilExtensionFoodMod.MOD_ID);

    public static final DataComponentType<Integer> EXTRA_DATA = register(
        "extra_data",
        (builder) -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT)
    );

    private static <T> DataComponentType<T> register(String name, Consumer<DataComponentType.Builder<T>> customizer) {
        var builder = DataComponentType.<T>builder();
        customizer.accept(builder);
        var componentType = builder.build();
        REGISTER.register(name, () -> componentType);
        return componentType;
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
