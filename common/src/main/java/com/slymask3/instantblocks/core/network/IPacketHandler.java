package com.slymask3.instantblocks.core.network;

import com.slymask3.instantblocks.core.network.packet.AbstractPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface IPacketHandler {
    void sendToServer(AbstractPacket message);
    void sendToClient(Player player, AbstractPacket message);
    void sendToAllAround(Level world, BlockPos pos, AbstractPacket message);
    void openScreen(Player player, MenuProvider menu, BlockPos pos);
}