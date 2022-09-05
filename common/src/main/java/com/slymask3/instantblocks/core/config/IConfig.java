package com.slymask3.instantblocks.core.config;

public interface IConfig {
    default void reload() {}

    default boolean USE_WANDS() { return Defaults.USE_WANDS; }
    default boolean KEEP_BLOCKS() { return Defaults.KEEP_BLOCKS; }
    default boolean ALLOW_WATER_IN_NETHER() { return Defaults.ALLOW_WATER_IN_NETHER; }
    default boolean ORIGINAL_INSTANT() { return Defaults.ORIGINAL_INSTANT; }
    default int LIGHT_MAX() { return Defaults.LIGHT_MAX; }
    default int XP_AMOUNT() { return Defaults.XP_AMOUNT; }

    default boolean GENERATE_IN_CHESTS() { return Defaults.GENERATE_IN_CHESTS; }
    default boolean GENERATE_IN_CHESTS_BONUS() { return Defaults.GENERATE_IN_CHESTS_BONUS; }

    default boolean SHOW_MESSAGES() { return Defaults.SHOW_MESSAGES; }
    default boolean SHOW_EFFECTS() { return Defaults.SHOW_EFFECTS; }
    default String SOUND_GENERATE() { return Defaults.SOUND_GENERATE; }
    default String SOUND_NO_LIQUID() { return Defaults.SOUND_NO_LIQUID; }

    class Defaults {
        public static boolean USE_WANDS = true;
        public static boolean KEEP_BLOCKS = false;
        public static boolean ALLOW_WATER_IN_NETHER = false;
        public static boolean ORIGINAL_INSTANT = false;
        public static int RADIUS_HARVEST = 25;
        public static int RADIUS_LIGHT = 25;
        public static int LIGHT_MAX = 5;
        public static int XP_AMOUNT = 0;

        public static boolean GENERATE_IN_CHESTS = true;
        public static boolean GENERATE_IN_CHESTS_BONUS = true;

        public static boolean SHOW_MESSAGES = true;
        public static boolean SHOW_EFFECTS = true;
        public static String SOUND_GENERATE = "entity.player.levelup";
        public static String SOUND_NO_LIQUID = "entity.panda.sneeze";
    }
}