package z3roco01.retainingbarrels.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import z3roco01.retainingbarrels.util.BarrelTrinketUtil;

public class BarrelTrinketFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    public BarrelTrinketFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // Dont render if the entity doesnt have a barrel on
        if(!BarrelTrinketUtil.isWearingBarrel(entity))
            return;

        ItemStack barrelStack = BarrelTrinketUtil.getWornBarrel(entity).get();

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180f));
        matrices.translate(0, -0.3f, 0.35f);

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        itemRenderer.renderItem(barrelStack, ModelTransformationMode.FIXED, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 0);
        /*this.getContextModel().setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        this.getContextModel().render(matrices, vertex, light, OverlayTexture.DEFAULT_UV);*/

        matrices.pop();
    }
}
