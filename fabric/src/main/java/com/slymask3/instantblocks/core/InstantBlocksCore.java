package com.slymask3.instantblocks.core;

import com.slymask3.instantblocks.core.builder.Builder;
import com.slymask3.instantblocks.core.config.ClothConfig;
import com.slymask3.instantblocks.core.handler.LootHandler;
import com.slymask3.instantblocks.core.init.IRegistryHelper;
import com.slymask3.instantblocks.core.init.Registration;
import com.slymask3.instantblocks.core.network.IPacketHandler;
import com.slymask3.instantblocks.core.network.packet.AbstractPacket;
import com.slymask3.instantblocks.core.platform.Services;
import com.slymask3.instantblocks.core.registry.CoreItems;
import com.slymask3.instantblocks.core.util.Helper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InstantBlocksCore implements ModInitializer {
    @Override
    public void onInitialize() {
        Core.LOG.info("loading mod: {}", Core.FABRIC_MOD_ID);
        Core.ITEM_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(Core.MOD_BASE, "general"), () -> new ItemStack(CoreItems.WAND_IRON));
        Core.NETWORK = new PacketHandler();

        if(Services.PLATFORM.isModLoaded("cloth-config")) {
            ClothConfig.register();
            Core.CONFIG = ClothConfig.get();
        }

        Registration.registerItems(new FabricRegistryHelper<>(Registry.ITEM));

        ServerTickEvents.END_SERVER_TICK.register((tick) -> Builder.globalTick());
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> !Builder.inProgress(world,pos));

        LootHandler.register();
        Core.init();
    }

    public static class FabricRegistryHelper<T> implements IRegistryHelper<T> {
        final Registry<T> registry;
        public FabricRegistryHelper(Registry<T> registry) {
            this.registry = registry;
        }
        public void register(ResourceLocation name, T entry) {
            Registry.register(this.registry, name, entry);
        }
    }

    public static class PacketHandler implements IPacketHandler {
        public void sendToServer(AbstractPacket message) {
            ClientPlayNetworking.send(message.getKey(), message.getBuffer());
        }
        public void sendToClient(Player player, AbstractPacket message) {
            if(Helper.isServer(player.getLevel())) {
                ServerPlayNetworking.send((ServerPlayer)player, message.getKey(), message.getBuffer());
            }
        }
        public void sendToAllAround(Level world, BlockPos pos, AbstractPacket message) {
            for(ServerPlayer player : PlayerLookup.tracking((ServerLevel)world, pos)) {
                ServerPlayNetworking.send(player, message.getKey(), message.getBuffer());
            }
        }
    }
}