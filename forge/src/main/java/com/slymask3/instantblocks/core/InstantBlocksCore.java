package com.slymask3.instantblocks.core;

import com.slymask3.instantblocks.core.builder.Builder;
import com.slymask3.instantblocks.core.config.ClothConfig;
import com.slymask3.instantblocks.core.config.ForgeConfig;
import com.slymask3.instantblocks.core.registry.CoreItems;
import com.slymask3.instantblocks.core.init.IRegistryHelper;
import com.slymask3.instantblocks.core.init.Registration;
import com.slymask3.instantblocks.core.network.ForgePacketHandler;
import com.slymask3.instantblocks.core.network.IPacketHandler;
import com.slymask3.instantblocks.core.network.packet.AbstractPacket;
import com.slymask3.instantblocks.core.platform.Services;
import com.slymask3.instantblocks.core.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;

@Mod(Core.FORGE_MOD_ID)
public class InstantBlocksCore {
	public InstantBlocksCore() {
		Core.ITEM_GROUP = new CreativeModeTab(CreativeModeTab.TABS.length, Core.MOD_BASE) { public @NotNull ItemStack makeIcon() { return new ItemStack(CoreItems.WAND_IRON); } };
		Core.NETWORK = new PacketHandler();

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

		MinecraftForge.EVENT_BUS.addListener(this::onServerTick);
		MinecraftForge.EVENT_BUS.addListener(this::onBlockBreak);
	}

	private void setupCommon(final FMLCommonSetupEvent event) {
		ForgePacketHandler.register();
		Core.init();
	}

	private void setupRegistry(final RegisterEvent event) {
		if(event.getForgeRegistry() != null) {
			if(event.getForgeRegistry().getRegistryKey().equals(Registry.ITEM_REGISTRY)) {
				Registration.registerItems(new ForgeRegistryHelper<>(event.getForgeRegistry()));
			}
		}
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
			ForgePacketHandler.INSTANCE.sendToServer(message);
		}
		public void sendToClient(Player player, AbstractPacket message) {
			if(Helper.isServer(player.getLevel())) {
				ForgePacketHandler.INSTANCE.sendTo(message, ((ServerPlayer)player).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
			}
		}
		public void sendToAllAround(Level world, BlockPos pos, AbstractPacket message) {
			ForgePacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), message);
		}
	}
}