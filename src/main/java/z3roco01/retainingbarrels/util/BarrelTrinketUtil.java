package z3roco01.retainingbarrels.util;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;

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

}
