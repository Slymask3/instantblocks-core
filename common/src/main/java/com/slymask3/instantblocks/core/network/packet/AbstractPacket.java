package com.slymask3.instantblocks.core.network.packet;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractPacket {
    private final ResourceLocation key;
    public AbstractPacket(ResourceLocation key) {
        this.key = key;
    }
    public ResourceLocation getKey() {
        return this.key;
    }
    public FriendlyByteBuf getBuffer() {
        return write(this, new FriendlyByteBuf(Unpooled.buffer()));
    }
    public <PKT extends AbstractPacket> FriendlyByteBuf write(PKT message, FriendlyByteBuf buffer) {
        return buffer;
    }
}