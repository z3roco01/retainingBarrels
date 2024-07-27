package z3roco01.retainingbarrels.util;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;
import java.util.Optional;

public class BarrelTrinketUtil {
    public static Optional<ItemStack> getWornBarrel(LivingEntity entity) {
        // Get the trinket component from the entity
        Optional<TrinketComponent> optional = TrinketsApi.getTrinketComponent(entity);
        if(optional.isEmpty())
            return Optional.empty();

        TrinketComponent component = optional.get();
        for(Pair<SlotReference, ItemStack> pair : component.getEquipped(Items.BARREL)) {
            // Check if this is the back and check if it contains a barrel
            if(pair.getLeft().inventory().getSlotType().getName().equals("back") && pair.getRight().getItem() == Items.BARREL)
                return Optional.of(pair.getRight());
        }

        return Optional.empty();
    }

    public static boolean isWearingBarrel(LivingEntity entity) {
        return getWornBarrel(entity).isPresent();
    }

    public static List<ItemStack> getBarrelContents(LivingEntity entity) {
        if(!isWearingBarrel(entity)) return List.of();

        // Get the barrel stack
        ItemStack barrelStack = getWornBarrel(entity).get();
        List<ItemStack> items = DefaultedList.ofSize(27, ItemStack.EMPTY);
        // Get the container component and add a list of the streamed objects to the defaulted list
        List<ItemStack> barrelItems = barrelStack.get(DataComponentTypes.CONTAINER).stream().toList();

        // Add the barrel items to the defaulted item list
        for(int i = 0; i < barrelItems.size(); ++i)
            items.set(i, barrelItems.get(i));

        return items;
    }

    public static void setBarrelContents(LivingEntity entity, List<ItemStack> stacks) {
        if(!isWearingBarrel(entity)) return;

        ItemStack barrel = getWornBarrel(entity).get();

        barrel.set(DataComponentTypes.CONTAINER, ContainerComponent.fromStacks(stacks));
    }
}
