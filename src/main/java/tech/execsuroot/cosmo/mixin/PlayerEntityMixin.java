package tech.execsuroot.cosmo.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.execsuroot.cosmo.CosmoMod;

@SuppressWarnings("DataFlowIssue")
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow
    public abstract GameProfile getGameProfile();

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void getDisplayName(CallbackInfoReturnable<Text> cir) {
        Pair<Text, Text> prefixAndSuffix = CosmoMod.getPrefixAndSuffixForPlayer((ServerPlayerEntity) (Object) this);
        Text prefix = prefixAndSuffix.getLeft();
        Text suffix = prefixAndSuffix.getRight();
        cir.setReturnValue(Text.empty().append(prefix).append(getGameProfile().getName()).append(suffix));
    }
}