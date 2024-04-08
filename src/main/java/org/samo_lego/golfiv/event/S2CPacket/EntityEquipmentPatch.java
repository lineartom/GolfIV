package org.samo_lego.golfiv.event.S2CPacket;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.samo_lego.golfiv.mixin.accessors.EntityEquipmentUpdateS2CPacketAccessor;

import java.util.List;

import static org.samo_lego.golfiv.GolfIV.golfConfig;
import static org.samo_lego.golfiv.casts.ItemStackChecker.fakeStack;

public class EntityEquipmentPatch implements S2CPacketCallback {

    public EntityEquipmentPatch() {
    }

    /**
     * Loops through entity equipment packet data
     * and removes attributes that cannot be seen.
     *
     * @param packet packet being sent
     * @param player player getting the packet
     */
    @Override
    public void preSendPacket(Packet<?> packet, ServerPlayerEntity player) {
        if(golfConfig.packet.removeEquipmentTags && packet instanceof EntityEquipmentUpdateS2CPacket) {
            EntityEquipmentUpdateS2CPacketAccessor packetAccessor = (EntityEquipmentUpdateS2CPacketAccessor) packet;

            if(packetAccessor.getEntityId() == player.getId())
                return;

            List<Pair<EquipmentSlot, ItemStack>> newEquipment = Lists.newArrayList();
            packetAccessor.getEquipment().forEach(pair -> {
                ItemStack fakedStack = fakeStack(pair.getSecond(), true);
                newEquipment.add(new Pair<>(pair.getFirst(), fakedStack));
            });

            packetAccessor.setEquipment(newEquipment);
        }
    }
}
