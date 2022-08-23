package com.slymask3.instantblocks.core.network.packet;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class InstantPacket extends AbstractPacket {
	public final boolean activate;
	public final BlockPos pos;

	public InstantPacket(ResourceLocation key, boolean activate, BlockPos pos) {
		super(key);
		this.activate = activate;
		this.pos = pos;
	}

	public <PKT extends AbstractPacket> FriendlyByteBuf write(PKT packet, FriendlyByteBuf buffer) {
		InstantPacket message = (InstantPacket)packet;
		buffer.writeBoolean(message.activate);
		buffer.writeBlockPos(message.pos);
		return buffer;
	}
}