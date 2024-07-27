package z3roco01.retainingbarrels;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z3roco01.retainingbarrels.network.OpenBarrelPayload;
import z3roco01.retainingbarrels.screen.BarrelScreenHandler;
import z3roco01.retainingbarrels.util.BarrelTrinketUtil;

import java.util.List;

public class RetainingBarrels implements ModInitializer {
	public static final String MODID = "retaining_barrels";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		LOGGER.info("init !");
		PayloadTypeRegistry.playC2S().register(OpenBarrelPayload.ID, OpenBarrelPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(OpenBarrelPayload.ID, (payload, context) ->  {
			OpenBarrelPayload Payload = (OpenBarrelPayload)payload;
			ItemStack barrelStack = Payload.barrelStack();
			ServerPlayerEntity player = context.player();

			// Get the items in the barrel and make an inventory with those items
			List<ItemStack> barrelItems = BarrelTrinketUtil.getBarrelContents(player);
			BarrelInventory barrelInventory = new BarrelInventory(barrelItems, barrelStack, player);

			// Create and open a screen handler for the barrel
			player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, Player) -> new BarrelScreenHandler(syncId, playerInventory, barrelInventory),
					barrelStack.getName()));
		});
	}
}