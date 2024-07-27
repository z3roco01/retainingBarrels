package z3roco01.retainingbarrels;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z3roco01.retainingbarrels.network.OpenBarrelPayload;
import z3roco01.retainingbarrels.render.BarrelTrinketFeatureRenderer;
import z3roco01.retainingbarrels.screen.BarrelScreen;
import z3roco01.retainingbarrels.screen.RBScreenHandlers;
import z3roco01.retainingbarrels.util.BarrelTrinketUtil;

public class RetainingBarrelsClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(RetainingBarrels.MODID + "_client");
	public static final KeyBinding openBarrelKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.retainingbarrels.open",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_B,
			KeyBinding.INVENTORY_CATEGORY
	));

	@Override
	public void onInitializeClient() {
		LOGGER.info("client init ! :D");

		LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
				(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> entityRenderer,
				 LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper, EntityRendererFactory.Context context) -> {
					registrationHelper.register(new BarrelTrinketFeatureRenderer<>(entityRenderer));
				}
		);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			ClientPlayerEntity player = client.player;
			// if the keybind was pressed and the player is wearing a barrel
			if(openBarrelKeybind.isPressed() && BarrelTrinketUtil.isWearingBarrel(player))
				ClientPlayNetworking.send(new OpenBarrelPayload(BarrelTrinketUtil.getWornBarrel(player).get()));
		});

		HandledScreens.register(RBScreenHandlers.BARREL_SCREEN_HANDLER, BarrelScreen::new);
	}
}