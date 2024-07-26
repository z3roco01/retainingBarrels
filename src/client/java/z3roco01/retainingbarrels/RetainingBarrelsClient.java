package z3roco01.retainingbarrels;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import z3roco01.retainingbarrels.render.BarrelTrinketFeatureRenderer;

public class RetainingBarrelsClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(RetainingBarrels.MODID + "_client");
	@Override
	public void onInitializeClient() {
		LOGGER.info("client init ! :D");

		LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
				(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> entityRenderer,
				 LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper, EntityRendererFactory.Context context) -> {
					registrationHelper.register(new BarrelTrinketFeatureRenderer<>(entityRenderer, context.getModelLoader()));
				}
		);
	}
}