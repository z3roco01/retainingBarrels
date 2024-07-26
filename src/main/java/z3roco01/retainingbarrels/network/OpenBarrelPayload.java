package z3roco01.retainingbarrels.network;

import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import z3roco01.retainingbarrels.RetainingBarrels;

public record OpenBarrelPayload(ItemStack barrelStack) implements CustomPayload {
    public static final Id ID = new Id(Identifier.of(RetainingBarrels.MODID, "open_barrel"));
    public static final PacketCodec<RegistryByteBuf, OpenBarrelPayload> CODEC = PacketCodec.tuple(ItemStack.PACKET_CODEC, OpenBarrelPayload::barrelStack, OpenBarrelPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
