package com.slymask3.instantblocks.core.network.packet.client;

import com.slymask3.instantblocks.core.network.CorePacketHelper;
import com.slymask3.instantblocks.core.network.packet.AbstractPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class ParticlePacket extends AbstractPacket {
	public final BlockPos pos;
	public final int particles;

	public ParticlePacket(BlockPos pos, int particles) {
		super(CorePacketHelper.PACKET_PARTICLE);
		this.pos = pos;
		this.particles = particles;
	}

	public <PKT extends AbstractPacket> FriendlyByteBuf write(PKT packet, FriendlyByteBuf buffer) {
		ParticlePacket message = (ParticlePacket)packet;
		buffer.writeBlockPos(message.pos);
		buffer.writeInt(message.particles);
		return buffer;
	}

	public static ParticlePacket decode(FriendlyByteBuf buffer) {
		BlockPos pos = buffer.readBlockPos();
		int particles = buffer.readInt();
		return new ParticlePacket(pos,particles);
	}
}