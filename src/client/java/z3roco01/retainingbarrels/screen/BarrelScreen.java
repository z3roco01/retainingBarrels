package z3roco01.retainingbarrels.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BarrelScreen extends HandledScreen<BarrelScreenHandler>
        implements ScreenHandlerProvider<BarrelScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of("textures/gui/container/generic_54.png");

    public BarrelScreen(BarrelScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 114 + 54;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    // This renders all of the stuff like items and tooltips that are on top of the background.
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    // This renders the background texture
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, 54 + 17);
        context.drawTexture(TEXTURE, i, j + 54 + 17, 0, 126, this.backgroundWidth, 96);
    }
}
