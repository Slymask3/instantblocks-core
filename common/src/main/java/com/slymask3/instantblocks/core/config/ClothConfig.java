package com.slymask3.instantblocks.core.config;

import com.slymask3.instantblocks.core.Core;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;

@Config(name = Core.MOD_BASE + "/" + Core.MOD_ADDON)
public class ClothConfig implements ConfigData, IConfig {
    public static void register() {
        AutoConfig.register(ClothConfig.class, Toml4jConfigSerializer::new);
    }

    public static ClothConfig get() {
        return AutoConfig.getConfigHolder(ClothConfig.class).getConfig();
    }

    public void reload() {
        AutoConfig.getConfigHolder(ClothConfig.class).load();
        Core.CONFIG = get();
    }

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.PrefixText()
    @ConfigEntry.Gui.TransitiveObject
    SectionGeneral general = new SectionGeneral();
    static class SectionGeneral {
        boolean USE_WANDS = Defaults.USE_WANDS;
        boolean WAND_OVER_DURABILITY = Defaults.WAND_OVER_DURABILITY;
        boolean KEEP_BLOCKS = Defaults.KEEP_BLOCKS;
        boolean ALLOW_WATER_IN_NETHER = Defaults.ALLOW_WATER_IN_NETHER;
        boolean ORIGINAL_INSTANT = Defaults.ORIGINAL_INSTANT;
        int XP_AMOUNT = Defaults.XP_AMOUNT;
        boolean GENERATE_IN_CHESTS = Defaults.GENERATE_IN_CHESTS;
        boolean GENERATE_IN_CHESTS_BONUS = Defaults.GENERATE_IN_CHESTS_BONUS;
    }

    @ConfigEntry.Category("blocks")
    @ConfigEntry.Gui.CollapsibleObject
    SectionLight light = new SectionLight();
    static class SectionLight {
        @ConfigEntry.BoundedDiscrete(min = 0, max = 15)
        int LIGHT_MAX = Defaults.LIGHT_MAX;
    }

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.PrefixText()
    @ConfigEntry.Gui.TransitiveObject
    SectionClient client = new SectionClient();
    static class SectionClient {
        boolean SHOW_MESSAGES = Defaults.SHOW_MESSAGES;
        boolean SHOW_EFFECTS = Defaults.SHOW_EFFECTS;
        String SOUND_GENERATE = Defaults.SOUND_GENERATE;
        String SOUND_NO_LIQUID = Defaults.SOUND_NO_LIQUID;
    }

    public boolean USE_WANDS() { return general.USE_WANDS; }
    public boolean WAND_OVER_DURABILITY() { return general.WAND_OVER_DURABILITY; }
    public boolean KEEP_BLOCKS() { return general.KEEP_BLOCKS; }
    public boolean ALLOW_WATER_IN_NETHER() { return general.ALLOW_WATER_IN_NETHER; }
    public boolean ORIGINAL_INSTANT() { return general.ORIGINAL_INSTANT; }
    public int LIGHT_MAX() { return light.LIGHT_MAX; }
    public int XP_AMOUNT() { return general.XP_AMOUNT; }
    public boolean GENERATE_IN_CHESTS() { return general.GENERATE_IN_CHESTS; }
    public boolean GENERATE_IN_CHESTS_BONUS() { return general.GENERATE_IN_CHESTS_BONUS; }
    public boolean SHOW_MESSAGES() { return client.SHOW_MESSAGES; }
    public boolean SHOW_EFFECTS() { return client.SHOW_EFFECTS; }
    public String SOUND_GENERATE() { return client.SOUND_GENERATE; }
    public String SOUND_NO_LIQUID() { return client.SOUND_NO_LIQUID; }
}