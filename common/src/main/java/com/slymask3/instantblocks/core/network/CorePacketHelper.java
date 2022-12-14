package com.slymask3.instantblocks.core.network;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.block.InstantBlock;
import com.slymask3.instantblocks.core.builder.BuildSound;
import com.slymask3.instantblocks.core.network.packet.InstantPacket;
import com.slymask3.instantblocks.core.network.packet.client.MessagePacket;
import com.slymask3.instantblocks.core.network.packet.client.ParticlePacket;
import com.slymask3.instantblocks.core.network.packet.client.SoundPacket;
import com.slymask3.instantblocks.core.util.ClientHelper;
import com.slymask3.instantblocks.core.util.Helper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class CorePacketHelper {
    public static ResourceLocation PACKET_MESSAGE = new ResourceLocation(Core.FABRIC_MOD_ID,"message");
    public static ResourceLocation PACKET_PARTICLE = new ResourceLocation(Core.FABRIC_MOD_ID,"particle");
    public static ResourceLocation PACKET_SOUND = new ResourceLocation(Core.FABRIC_MOD_ID,"sound");

    public static void activate(InstantPacket message, Level world, Player player) {
        if(message.activate) {
            InstantBlock block = (InstantBlock)Helper.getBlock(world,message.pos);
            block.activate(world,message.pos,player);
        }
    }

    public static void handleMessage(MessagePacket message, Player player) {
        if(!message.message.isEmpty()) {
            ClientHelper.sendMessage(player, message.message, message.variable);
        }
    }

    public static void handleParticle(ParticlePacket message, Player player) {
        ClientHelper.showParticles(player, message.pos, ClientHelper.Particles.values()[message.particles]);
    }

    public static void handleSound(SoundPacket message, Player player) {
        for(BuildSound buildSound : message.buildSounds) {
            if(buildSound.getBreakSound() != null || buildSound.getPlaceSound() != null) {
                ClientHelper.showParticles(player, buildSound.getBlockPos(), buildSound.getParticles());
            }
            ClientHelper.playSound(player, buildSound.getBlockPos(), buildSound.getBreakSound(), buildSound.getVolume());
            ClientHelper.playSound(player, buildSound.getBlockPos(), buildSound.getPlaceSound(), buildSound.getVolume());
        }
    }
}