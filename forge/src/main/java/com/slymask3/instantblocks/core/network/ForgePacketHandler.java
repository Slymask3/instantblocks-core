package com.slymask3.instantblocks.core.network;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.network.packet.AbstractPacket;
import com.slymask3.instantblocks.core.network.packet.client.MessagePacket;
import com.slymask3.instantblocks.core.network.packet.client.ParticlePacket;
import com.slymask3.instantblocks.core.network.packet.client.SoundPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ForgePacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Core.FABRIC_MOD_ID,"main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void register() {
        int index = 100;
        INSTANCE.registerMessage(++index, MessagePacket.class, (MessagePacket message, FriendlyByteBuf buffer) -> message.write(message,buffer), MessagePacket::decode, Handler::client);
        INSTANCE.registerMessage(++index, ParticlePacket.class, (ParticlePacket message, FriendlyByteBuf buffer) -> message.write(message,buffer), ParticlePacket::decode, Handler::client);
        INSTANCE.registerMessage(++index, SoundPacket.class, (SoundPacket message, FriendlyByteBuf buffer) -> message.write(message,buffer), SoundPacket::decode, Handler::client);
    }

    public static class Handler {
        public static void client(AbstractPacket message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(() -> {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientHandler.handle(message,context));
            });
            context.get().setPacketHandled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class ClientHandler {
        public static void handle(AbstractPacket message, Supplier<NetworkEvent.Context> context) {
            Player player = Minecraft.getInstance().player;
            if(player != null) {
                if(message.getClass().equals(MessagePacket.class)) {
                    PacketHelper.handleMessage((MessagePacket)message, player);
                } else if(message.getClass().equals(ParticlePacket.class)) {
                    PacketHelper.handleParticle((ParticlePacket)message, player);
                } else if(message.getClass().equals(SoundPacket.class)) {
                    PacketHelper.handleSound((SoundPacket)message, player);
                }
            }
        }
    }
}