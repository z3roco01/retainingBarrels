package z3roco01.retainingbarrels.render;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Pair;
import net.minecraft.util.math.RotationAxis;

import java.util.Optional;

public class BarrelTrinketFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    public BarrelTrinketFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
    }

    private Optional<ItemStack> getWornBarrel(LivingEntity entity) {
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

    private boolean isWearingBarrel(LivingEntity entity) {
        return getWornBarrel(entity).isPresent();
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        // Dont render if the entity doesnt have a barrel on
        if(!isWearingBarrel(entity))
            return;

        ItemStack barrelStack = getWornBarrel(entity).get();

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
