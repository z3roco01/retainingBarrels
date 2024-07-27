package z3roco01.retainingbarrels.mixin;

import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z3roco01.retainingbarrels.screen.BarrelScreenHandler;

@Mixin(BarrelBlockEntity.class)
public class BarrelBlockEntityMixin {
    @Inject(method = "createScreenHandler", cancellable = true, at = @At("HEAD"))
    public void createScreenHandler(int syncId, PlayerInventory playerInventory, CallbackInfoReturnable<ScreenHandler> cir) {
        cir.setReturnValue(new BarrelScreenHandler(syncId, playerInventory, (BarrelBlockEntity)(Object)this));
        cir.cancel();
    }
}
