package com.slymask3.instantblocks.core.network;

import com.slymask3.instantblocks.core.network.packet.client.MessagePacket;
import com.slymask3.instantblocks.core.network.packet.client.ParticlePacket;
import com.slymask3.instantblocks.core.network.packet.client.SoundPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.resources.ResourceLocation;

public class FabricPacketHandler {
    @Environment(EnvType.CLIENT)
    public static class Client {
        public static void init() {
            ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(PacketHelper.PacketID.MESSAGE.toString().toLowerCase()), (client, handler, buf, responseSender) -> {
                if(client.player != null) {
                    MessagePacket message = MessagePacket.decode(buf);
                    client.execute(() -> PacketHelper.handleMessage(message, client.player));
                }
            });

            ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(PacketHelper.PacketID.PARTICLE.toString().toLowerCase()), (client, handler, buf, responseSender) -> {
                if(client.player != null) {
                    ParticlePacket message = ParticlePacket.decode(buf);
                    client.execute(() -> PacketHelper.handleParticle(message, client.player));
                }
            });

            ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(PacketHelper.PacketID.SOUND.toString().toLowerCase()), (client, handler, buf, responseSender) -> {
                if(client.player != null) {
                    SoundPacket message = SoundPacket.decode(buf);
                    client.execute(() -> PacketHelper.handleSound(message, client.player));
                }
            });
        }
    }
}