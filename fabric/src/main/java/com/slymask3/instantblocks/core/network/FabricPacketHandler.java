package com.slymask3.instantblocks.core.network;

import com.slymask3.instantblocks.core.network.packet.client.MessagePacket;
import com.slymask3.instantblocks.core.network.packet.client.ParticlePacket;
import com.slymask3.instantblocks.core.network.packet.client.SoundPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class FabricPacketHandler {
    @Environment(EnvType.CLIENT)
    public static class Client {
        public static void init() {
            ClientPlayNetworking.registerGlobalReceiver(CorePacketHelper.PACKET_MESSAGE, (client, handler, buf, responseSender) -> {
                if(client.player != null) {
                    MessagePacket message = MessagePacket.decode(buf);
                    client.execute(() -> CorePacketHelper.handleMessage(message, client.player));
                }
            });

            ClientPlayNetworking.registerGlobalReceiver(CorePacketHelper.PACKET_PARTICLE, (client, handler, buf, responseSender) -> {
                if(client.player != null) {
                    ParticlePacket message = ParticlePacket.decode(buf);
                    client.execute(() -> CorePacketHelper.handleParticle(message, client.player));
                }
            });

            ClientPlayNetworking.registerGlobalReceiver(CorePacketHelper.PACKET_SOUND, (client, handler, buf, responseSender) -> {
                if(client.player != null) {
                    SoundPacket message = SoundPacket.decode(buf);
                    client.execute(() -> CorePacketHelper.handleSound(message, client.player));
                }
            });
        }
    }
}