package org.samo_lego.golfiv.mixin.packets;

import net.minecraft.network.packet.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerCommonNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.samo_lego.golfiv.event.S2CPacket.S2CPacketCallback;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.lang.reflect.Field;

@Mixin(ServerCommonNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin_PacketEvents {

    /**
     * If player teleports out of render distance, we modify the coordinates of the
     * packet, in order to hide player's original TP coordinates.
     *
     * @param packet
     * @param ci
     */
    @Inject(method = "sendPacket(Lnet/minecraft/network/packet/Packet;)V",
            at = @At("HEAD"))
    private void onPacket(Packet<?> packet, CallbackInfo ci) {
        try {
            Field player_field = this.getClass().getDeclaredField("player");
            ServerPlayerEntity player = (ServerPlayerEntity)player_field.get(this);
            S2CPacketCallback.EVENT.invoker().preSendPacket(packet, player);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return;
        }
    }
}