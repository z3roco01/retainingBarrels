package z3roco01.retainingbarrels.mixin;

import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BarrelBlock.class)
public abstract class BarrelBlockMixin extends BlockMixin {
    private static final Text UNGENERATED_LOOT_TEXT = Text.translatable("container.barrel.unknownContents");

    @Inject(method = "onStateReplaced", at = @At("HEAD"), cancellable = true)
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo ci) {
        ci.cancel();
        if (state.hasBlockEntity() && !state.isOf(newState.getBlock())) {
            world.removeBlockEntity(pos);
        }
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        cir.cancel();
        if(!state.isOf(Blocks.BARREL)) {
            return;
        }

        // Get the block entity of the barrel
        BarrelBlockEntity blockEntity = (BarrelBlockEntity)world.getBlockEntity(pos);
        if(blockEntity == null)
            return;

        if(!world.isClient && player.isCreative() && !blockEntity.isEmpty()) {
            // Create an item stack that has the components of the barrel
            ItemStack stack = new ItemStack(Items.BARREL);
            stack.applyComponentsFrom(blockEntity.createComponentMap());

            // Create an item entity from the stack so it can be spawned into the world
            ItemEntity itemEntity = new ItemEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, stack);
            itemEntity.setToDefaultPickupDelay();
            world.spawnEntity(itemEntity);
        }
        super.onBreak(world, pos, state, player, cir);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options, CallbackInfo ci) {
        if (stack.contains(DataComponentTypes.CONTAINER_LOOT)) {
            tooltip.add(UNGENERATED_LOOT_TEXT);
        }
        int i = 0;
        int j = 0;
        for (ItemStack itemStack : stack.getOrDefault(DataComponentTypes.CONTAINER, ContainerComponent.DEFAULT).iterateNonEmpty()) {
            ++j;
            if (i > 4) continue;
            ++i;
            tooltip.add(Text.translatable("container.shulkerBox.itemCount", itemStack.getName(), itemStack.getCount()));
        }
        if (j - i > 0) {
            tooltip.add(Text.translatable("container.shulkerBox.more", j - i).formatted(Formatting.ITALIC));
        }
    }
}

