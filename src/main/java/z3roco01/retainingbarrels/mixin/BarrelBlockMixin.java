package z3roco01.retainingbarrels.mixin;

import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BarrelBlock.class)
public abstract class BarrelBlockMixin extends BlockWithEntity {
    public BarrelBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "onStateReplaced", at = @At("HEAD"), cancellable = true)
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        ci.cancel();
        if(state.isOf(newState.getBlock()) || !state.isOf(Blocks.BARREL)) {
            return;
        }

        // Get the block entity of the barrel
        BarrelBlockEntity blockEntity = (BarrelBlockEntity)world.getBlockEntity(pos);
        if(blockEntity == null)
            return;

        // Create an item stack that has the components of the barrel
        ItemStack stack = new ItemStack(Items.BARREL);
        stack.applyComponentsFrom(blockEntity.createComponentMap());

        // Create an item entity from the stack so it can be spawned into the world
        ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, stack);
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}

