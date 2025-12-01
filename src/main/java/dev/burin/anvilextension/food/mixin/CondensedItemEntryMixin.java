package dev.burin.anvilextension.food.mixin;

import io.wispforest.condensed_creative.entry.impl.CondensedItemEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CondensedItemEntry.class)
public class CondensedItemEntryMixin {
    @Shadow
    private Component condensedEntryTitle;

    @Inject(
        method = "<init>",
        at = @At("RETURN")
    )
    private void init(ResourceLocation identifier, ItemStack defaultStack, boolean isChild, CallbackInfo ci) {
        String modId = identifier.getNamespace();
        String name = identifier.getPath();
        this.condensedEntryTitle = Component.translatable("condensed_creative." + modId + "." + name);
    }
}
