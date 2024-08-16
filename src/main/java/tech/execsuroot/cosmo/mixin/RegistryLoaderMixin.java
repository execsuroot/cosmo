package tech.execsuroot.cosmo.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.message.MessageType;
import net.minecraft.registry.*;
import net.minecraft.text.Decoration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.execsuroot.cosmo.CosmoMod;

import java.util.List;

@SuppressWarnings("unchecked")
@Mixin(RegistryLoader.class)
public class RegistryLoaderMixin {

    @Inject(method = "load(Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/registry/DynamicRegistryManager;Ljava/util/List;)Lnet/minecraft/registry/DynamicRegistryManager$Immutable;", at = @At(value = "INVOKE", target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V", ordinal = 0, shift = At.Shift.AFTER))
    private static void injectMessageType(@Coerce Object registryLoadable, DynamicRegistryManager registryManager, List<RegistryLoader.Entry<?>> entries, CallbackInfoReturnable<DynamicRegistryManager.Immutable> cir, @Local(ordinal = 1) List<Pair<MutableRegistry<?>, Object>> list) {
        for (var pair : list) {
            var reg = pair.getFirst();
            if (reg.getKey().equals(RegistryKeys.MESSAGE_TYPE)) {
                Registry.register((Registry<MessageType>) reg, CosmoMod.MESSAGE_TYPE_ID, new MessageType(Decoration.ofChat("%s"), Decoration.ofChat("%s")));
            }
        }
    }
}