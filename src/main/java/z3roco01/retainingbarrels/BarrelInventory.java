package z3roco01.retainingbarrels;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
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
}
