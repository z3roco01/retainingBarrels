package z3roco01.retainingbarrels;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import z3roco01.retainingbarrels.util.BarrelTrinketUtil;

import java.util.List;

public class BarrelInventory extends SimpleInventory {
    private final ItemStack barrelStack;
    private final ServerPlayerEntity player;

    public BarrelInventory(List<ItemStack> items, ItemStack barrel, ServerPlayerEntity player) {
        this(barrel, player, items.toArray(new ItemStack[0]));
    }
    public BarrelInventory(ItemStack barrel, ServerPlayerEntity player, ItemStack... items) {
        super(items);
        this.barrelStack = barrel;
        this.player = player;
    }

    @Override
    public void markDirty() {
        // Set the contents of the players worn barrel
        BarrelTrinketUtil.setBarrelContents(player, this.getHeldStacks());
        super.markDirty();
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        RetainingBarrels.LOGGER.info(stack.toString());
        return stack.getItem() != Items.BARREL;
    }

    @Override
    public void onOpen(PlayerEntity player) {
        player.playSoundToPlayer(SoundEvents.BLOCK_BARREL_OPEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    public void onClose(PlayerEntity player) {
        player.playSoundToPlayer(SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }
}
