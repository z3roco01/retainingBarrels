package z3roco01.retainingbarrels.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class BarrelScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    public BarrelScreenHandler(int syncId, PlayerInventory playerInventory) {
        // Calls this constructor with empty simple inventory
        this(syncId, playerInventory, new SimpleInventory(27));
    }

    public BarrelScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        // Calls super passing all the parameters and the screen handler type
        super(RBScreenHandlers.BARREL_SCREEN_HANDLER, syncId);
        int k;
        int j;
        GenericContainerScreenHandler.checkSize(inventory, 27);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);
        // ths creates all the slots for this inventory but doesnt render them, thats the job of the screen.
        int i = -18;
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new BarrelSlot(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        for (j = 0; j < 3; ++j) {
            for (k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }
        for (j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 161 + i));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot2 = this.slots.get(slot);
        if (slot2.hasStack()) {
            ItemStack itemStack2 = slot2.getStack();
            itemStack = itemStack2.copy();
            if (slot < 27 ? !this.insertItem(itemStack2, 27, this.slots.size(), true) : !this.insertItem(itemStack2, 0, 27, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot2.setStack(ItemStack.EMPTY);
            } else {
                slot2.markDirty();
            }
        }
        return itemStack;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }

    // slot that allows everything except barrels and shulker boxes
    private class BarrelSlot extends Slot{
        public BarrelSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            Item item = stack.getItem();
            return item != Items.BARREL && item.canBeNested();
        }
    }
}
