package tech.execsuroot.cosmo.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(method = "getPlayerListName", at = @At("HEAD"), cancellable = true)
    private void getPlayerListName(CallbackInfoReturnable<Text> cir) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        cir.setReturnValue(playerEntity.getDisplayName());
    }
}