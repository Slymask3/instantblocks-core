package com.slymask3.instantblocks.core;

import com.slymask3.instantblocks.core.builder.Builder;
import com.slymask3.instantblocks.core.builder.Fuel;
import com.slymask3.instantblocks.core.config.ClothConfig;
import com.slymask3.instantblocks.core.config.ForgeConfig;
import com.slymask3.instantblocks.core.init.ForgeMenus;
import com.slymask3.instantblocks.core.init.ForgeTiles;
import com.slymask3.instantblocks.core.init.IRegistryHelper;
import com.slymask3.instantblocks.core.init.Registration;
import com.slymask3.instantblocks.core.network.CoreForgePacketHandler;
import com.slymask3.instantblocks.core.network.IPacketHandler;
import com.slymask3.instantblocks.core.network.packet.AbstractPacket;
import com.slymask3.instantblocks.core.platform.Services;
import com.slymask3.instantblocks.core.registry.CoreBlocks;
import com.slymask3.instantblocks.core.util.Helper;
import com.slymask3.instantblocks.core.util.IModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.tags.ITag;
import org.jetbrains.annotations.NotNull;

@Mod(Core.FORGE_MOD_ID)
public class InstantBlocksCore {
	public InstantBlocksCore() {
		Core.init();
		Core.ITEM_GROUP = new CreativeModeTab(CreativeModeTab.TABS.length, Core.MOD_BASE) { public @NotNull ItemStack makeIcon() { return new ItemStack(CoreBlocks.WAND_CHARGE); } };
		Core.NETWORK = new PacketHandler();
		Core.TILES = new ForgeTiles();
		Core.CONTAINERS = new ForgeMenus();
		Core.LOADER = new ModLoader();

		if(Services.PLATFORM.isModLoaded("cloth_config")) {
			ClothConfig.register();
			Core.CONFIG = ClothConfig.get();
		} else {
			ForgeConfig.init();
			Core.CONFIG = new ForgeConfig();
		}

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::setupCommon);
		modEventBus.addListener(this::setupRegistry);
		MinecraftForge.EVENT_BUS.register(this);

		MinecraftForge.EVENT_BUS.addListener(this::onTagsLoaded);
		MinecraftForge.EVENT_BUS.addListener(this::onItemTooltip);
		MinecraftForge.EVENT_BUS.addListener(this::onServerTick);
		MinecraftForge.EVENT_BUS.addListener(this::onBlockBreak);
	}

	private void setupCommon(final FMLCommonSetupEvent event) {
		CoreForgePacketHandler.register();
	}

	private void setupRegistry(final RegisterEvent event) {
		if(event.getForgeRegistry() != null) {
			if(event.getForgeRegistry().getRegistryKey().equals(Registry.BLOCK_REGISTRY)) {
				Registration.registerBlocks(new ForgeRegistryHelper<>(event.getForgeRegistry()));
			} else if(event.getForgeRegistry().getRegistryKey().equals(Registry.ITEM_REGISTRY)) {
				Registration.registerItems(new ForgeRegistryHelper<>(event.getForgeRegistry()));
			} else if(event.getForgeRegistry().getRegistryKey().equals(Registry.BLOCK_ENTITY_TYPE_REGISTRY)) {
				Registration.registerTiles(new ForgeRegistryHelper<>(event.getForgeRegistry()));
			} else if(event.getForgeRegistry().getRegistryKey().equals(Registry.MENU_REGISTRY)) {
				Registration.registerContainers(new ForgeRegistryHelper<>(event.getForgeRegistry()));
			}
		}
	}

	private void onTagsLoaded(final TagsUpdatedEvent event) {
		Fuel.setup();
	}

	private void onItemTooltip(final ItemTooltipEvent event) {
		Fuel.addTooltip(event.getItemStack(),event.getToolTip());
	}

	private void onServerTick(final TickEvent.ServerTickEvent event) {
		if(event.phase == TickEvent.Phase.END) {
			Builder.globalTick();
		}
	}

	private void onBlockBreak(final BlockEvent.BreakEvent event) {
		if(Builder.inProgress(event.getLevel(),event.getPos())) {
			event.setCanceled(true);
		}
	}

	public static class ForgeRegistryHelper<T> implements IRegistryHelper<T> {
		final IForgeRegistry<T> registry;
		public ForgeRegistryHelper(IForgeRegistry<T> registry) {
			this.registry = registry;
		}
		public void register(ResourceLocation name, T entry) {
			this.registry.register(name,entry);
		}
	}

	public static class PacketHandler implements IPacketHandler {
		public void sendToServer(AbstractPacket message) {
			CoreForgePacketHandler.INSTANCE.sendToServer(message);
		}
		public void sendToClient(Player player, AbstractPacket message) {
			if(Helper.isServer(player.getLevel())) {
				CoreForgePacketHandler.INSTANCE.sendTo(message, ((ServerPlayer)player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
			}
		}
		public void sendToAllAround(Level world, BlockPos pos, AbstractPacket message) {
			CoreForgePacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), message);
		}
		public void openScreen(Player player, MenuProvider menu, BlockPos pos) {
			NetworkHooks.openScreen((ServerPlayer)player,menu,pos);
		}
	}

	public static class ModLoader implements IModLoader {
		public Item getItem(String string) {
			return ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));
		}
		public String getKey(Block block) {
			ResourceLocation resourceLocation = ForgeRegistries.BLOCKS.getKey(block);
			return resourceLocation != null ? resourceLocation.toString() : "";
		}
		public String getKey(Item item) {
			ResourceLocation resourceLocation = ForgeRegistries.ITEMS.getKey(item);
			return resourceLocation != null ? resourceLocation.toString() : "";
		}
		public TagKey<Item> getItemTagKey(String tag) {
			return ItemTags.create(new ResourceLocation(tag));
		}
		public TagKey<Block> getBlockTagKey(String tag) {
			return BlockTags.create(new ResourceLocation(tag));
		}
		public void forEachItem(TagKey<?> tag, ItemCallable itemCallable) {
			if(tag.registry().equals(ForgeRegistries.ITEMS.getRegistryKey())) {
				ITag<Item> items = ForgeRegistries.ITEMS.tags().getTag((TagKey<Item>)tag);
				for(Item item : items) {
					itemCallable.call(item);
				}
			} else if(tag.registry().equals(ForgeRegistries.BLOCKS.getRegistryKey())) {
				ITag<Block> blocks = ForgeRegistries.BLOCKS.tags().getTag((TagKey<Block>)tag);
				for(Block block : blocks) {
					itemCallable.call(block.asItem());
				}
			}
		}
	}
}