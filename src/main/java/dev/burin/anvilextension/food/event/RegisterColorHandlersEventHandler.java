package dev.burin.anvilextension.food.event;

import dev.burin.anvilextension.food.init.block.ModCauldronBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber
public class RegisterColorHandlersEventHandler {
    @SubscribeEvent
    public static void register(final RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, index) ->
            level != null && pos != null
                ? BiomeColors.getAverageWaterColor(level, pos)
                : -1, ModCauldronBlocks.SEAWATER_CAULDRON.get());
    }
}
