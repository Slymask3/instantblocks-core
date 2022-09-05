package com.slymask3.instantblocks.core;

import com.slymask3.instantblocks.core.builder.Builder;
import com.slymask3.instantblocks.core.config.ClothConfig;
import com.slymask3.instantblocks.core.handler.LootHandler;
import com.slymask3.instantblocks.core.init.FabricMenus;
import com.slymask3.instantblocks.core.init.FabricTiles;
import com.slymask3.instantblocks.core.init.IRegistryHelper;
import com.slymask3.instantblocks.core.init.Registration;
import com.slymask3.instantblocks.core.network.IPacketHandler;
import com.slymask3.instantblocks.core.network.packet.AbstractPacket;
import com.slymask3.instantblocks.core.platform.Services;
import com.slymask3.instantblocks.core.registry.CoreBlocks;
import com.slymask3.instantblocks.core.util.Helper;
import com.slymask3.instantblocks.core.util.IModLoader;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class InstantBlocksCore implements ModInitializer {
    @Override
    public void onInitialize() {
        Core.LOG.info("loading mod: {}", Core.FABRIC_MOD_ID);

        Core.init();
        Core.ITEM_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(Core.MOD_BASE, "general"), () -> new ItemStack(CoreBlocks.WAND_CHARGE));
        Core.NETWORK = new PacketHandler();
        Core.TILES = new FabricTiles();
        Core.MENUS = new FabricMenus();
        Core.LOADER = new ModLoader();

        if(Services.PLATFORM.isModLoaded("cloth-config")) {
            ClothConfig.register();
            Core.CONFIG = ClothConfig.get();
        }

        Registration.registerBlocks(new FabricRegistryHelper<>(Registry.BLOCK));
        Registration.registerItems(new FabricRegistryHelper<>(Registry.ITEM));
        Registration.registerTiles(new FabricRegistryHelper<>(Registry.BLOCK_ENTITY_TYPE));
        Registration.registerMenus(new FabricRegistryHelper<>(Registry.MENU));

        Core.overwriteFuel();

        ServerTickEvents.END_SERVER_TICK.register((tick) -> Builder.globalTick());
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> !Builder.inProgress(world,pos));

        LootHandler.register();
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
        public void openScreen(Player player, MenuProvider menu, BlockPos pos) {
            player.openMenu(new ExtendedScreenHandlerFactory() {
                public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                    return menu.createMenu(i,inventory,player);
                }
                public Component getDisplayName() {
                    return menu.getDisplayName();
                }
                public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                    buf.writeBlockPos(pos);
                }
            });
        }
    }

    public static class ModLoader implements IModLoader {
        public Item getItem(String string) {
            return Registry.ITEM.get(new ResourceLocation(string));
        }
        public String getKey(Block block) {
            return Registry.BLOCK.getKey(block).toString();
        }
        public String getKey(Item item) {
            return Registry.ITEM.getKey(item).toString();
        }
    }
}