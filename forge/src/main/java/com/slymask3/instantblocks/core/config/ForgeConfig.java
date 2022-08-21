package com.slymask3.instantblocks.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeConfig implements IConfig {
	public static class Common {
		public static BooleanValue USE_WANDS;
		public static BooleanValue WAND_OVER_DURABILITY;
		public static BooleanValue KEEP_BLOCKS;
		public static BooleanValue ALLOW_WATER_IN_NETHER;
		public static BooleanValue ORIGINAL_INSTANT;
		public static IntValue RADIUS_HARVEST;
		public static IntValue RADIUS_LIGHT;
		public static IntValue LIGHT_MAX;
		public static IntValue XP_AMOUNT;

		public static BooleanValue GENERATE_IN_CHESTS;
		public static BooleanValue GENERATE_IN_CHESTS_BONUS;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("General settings").push("general");

			USE_WANDS = builder
					.comment("Use wands to activate instant blocks.\nDefault: true")
					.define("USE_WANDS", Defaults.USE_WANDS);

			WAND_OVER_DURABILITY = builder
					.comment("Allow activating instant blocks even if the wand doesn't have enough durability.\nDefault: false")
					.define("WAND_OVER_DURABILITY", Defaults.WAND_OVER_DURABILITY);

			KEEP_BLOCKS = builder
					.comment("Keep instant blocks after activation.\nDefault: false")
					.define("KEEP_BLOCKS", Defaults.KEEP_BLOCKS);

			ALLOW_WATER_IN_NETHER = builder
					.comment("Allow generating water in the nether.\nDefault: false")
					.define("ALLOW_WATER_IN_NETHER", Defaults.ALLOW_WATER_IN_NETHER);

			ORIGINAL_INSTANT = builder
					.comment("Generate structures instantaneously without a queue.\nDefault: false")
					.define("ORIGINAL_INSTANT", Defaults.ORIGINAL_INSTANT);

			RADIUS_HARVEST = builder
					.comment("Radius to harvest blocks around Instant Harvest.\nDefault: 25")
					.defineInRange("RADIUS_HARVEST", Defaults.RADIUS_HARVEST,1,1000);

			RADIUS_LIGHT = builder
					.comment("Radius to light up dark areas around Instant Light.\nDefault: 25")
					.defineInRange("RADIUS_LIGHT", Defaults.RADIUS_LIGHT,1,1000);

			LIGHT_MAX = builder
					.comment("Maximum light level for placing a torch.\nDefault: 7")
					.defineInRange("LIGHT_MAX", Defaults.RADIUS_LIGHT,0,15);

			XP_AMOUNT = builder
					.comment("How much experience activating instant blocks gives you.\nDefault: 0")
					.defineInRange("XP_AMOUNT", Defaults.XP_AMOUNT,0,10000);

			builder.pop();

			builder.comment("Generating items in structure chests").push("structures");

			GENERATE_IN_CHESTS = builder
					.comment("Add instant blocks in loot chests.\nDefault: true")
					.worldRestart()
					.define("GENERATE_IN_CHESTS", Defaults.GENERATE_IN_CHESTS);

			GENERATE_IN_CHESTS_BONUS = builder
					.comment("Add an Instant Wooden House and wand in the bonus chest.\nDefault: true")
					.worldRestart()
					.define("GENERATE_IN_CHESTS_BONUS", Defaults.GENERATE_IN_CHESTS_BONUS);

			builder.pop();
		}
	}

	public static class Client {
		public static BooleanValue SHOW_MESSAGES;
		public static BooleanValue SHOW_EFFECTS;
		public static ConfigValue<String> SOUND_GENERATE;
		public static ConfigValue<String> SOUND_NO_LIQUID;

		Client(ForgeConfigSpec.Builder builder) {
			builder.comment("Client only settings").push("client");

			SHOW_MESSAGES = builder
					.comment("Show messages.\nDefault: true")
					.define("SHOW_MESSAGES", Defaults.SHOW_MESSAGES);

			SHOW_EFFECTS = builder
					.comment("Show particle effects.\nDefault: true")
					.define("SHOW_EFFECTS", Defaults.SHOW_EFFECTS);

			SOUND_GENERATE = builder
					.comment("Sound that is played on activation.\nList of sounds can be found here: https://www.digminecraft.com/lists/sound_list_pc.php or by using the /playsound command in-game.\nDefault: entity.player.levelup")
					.define("SOUND_GENERATE", Defaults.SOUND_GENERATE);

			SOUND_NO_LIQUID = builder
					.comment("Sound that is played when no liquid is found.\nList of sounds can be found here: https://www.digminecraft.com/lists/sound_list_pc.php or by using the /playsound command in-game.\nDefault: entity.panda.sneeze")
					.define("SOUND_NO_LIQUID", Defaults.SOUND_NO_LIQUID);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec clientSpec;
	public static final Client CLIENT;

	static {
		final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Client::new);
		clientSpec = specPair.getRight();
		CLIENT = specPair.getLeft();
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	public static void init() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfig.commonSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ForgeConfig.clientSpec);
	}

	public boolean USE_WANDS() { return Common.USE_WANDS.get(); }
	public boolean WAND_OVER_DURABILITY() { return Common.WAND_OVER_DURABILITY.get(); }
	public boolean KEEP_BLOCKS() { return Common.KEEP_BLOCKS.get(); }
	public boolean ALLOW_WATER_IN_NETHER() { return Common.ALLOW_WATER_IN_NETHER.get(); }
	public boolean ORIGINAL_INSTANT() { return Common.ORIGINAL_INSTANT.get(); }
	public int LIGHT_MAX() { return Common.LIGHT_MAX.get(); }
	public int XP_AMOUNT() { return Common.XP_AMOUNT.get(); }
	public boolean GENERATE_IN_CHESTS() { return Common.GENERATE_IN_CHESTS.get(); }
	public boolean GENERATE_IN_CHESTS_BONUS() { return Common.GENERATE_IN_CHESTS_BONUS.get(); }
	public boolean SHOW_MESSAGES() { return Client.SHOW_MESSAGES.get(); }
	public boolean SHOW_EFFECTS() { return Client.SHOW_EFFECTS.get(); }
	public String SOUND_GENERATE() { return Client.SOUND_GENERATE.get(); }
	public String SOUND_NO_LIQUID() { return Client.SOUND_NO_LIQUID.get(); }
}