package z3roco01.retainingbarrels.screen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import z3roco01.retainingbarrels.RetainingBarrels;

public class RBScreenHandlers {
    public static final ScreenHandlerType<BarrelScreenHandler> BARREL_SCREEN_HANDLER;

    static {
        BARREL_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER,
                Identifier.of(RetainingBarrels.MODID, "barrel"),
                new ScreenHandlerType<BarrelScreenHandler>(BarrelScreenHandler::new, FeatureSet.of(FeatureFlags.VANILLA)));
    }
}
