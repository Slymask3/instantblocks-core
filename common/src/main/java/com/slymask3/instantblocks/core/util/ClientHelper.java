package com.slymask3.instantblocks.core.util;

import com.slymask3.instantblocks.core.Common;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ClientHelper {
    public enum Screen { STATUE, HARVEST, SKYDIVE, SCHEMATIC, TREE }
    public enum Particles { NONE, GENERATE, NO_LIQUID, PLACE_BLOCK, CLEAR_BLOCK }

    public static void playSound(Player player, BlockPos pos, SoundEvent sound, float volume) {
        if(sound != null) {
            player.level.playSound(player, pos, sound, SoundSource.BLOCKS, volume, 1.0F);
        }
    }

    public static void showParticles(Player player, BlockPos pos, Particles particles) {
        Level world = player.getLevel();
        switch(particles) {
            case GENERATE -> {
                for(double i = 0; i <= 1; i = i + 0.2) {
                    for(double n = 0; n <= 1; n = n + 0.2) {
                        for(double v = 0; v <= 1; v = v + 0.2) {
                            addParticle(world,pos,DustParticleOptions.REDSTONE,i,v,n);
                        }
                    }
                }
                playSound(player, pos, new SoundEvent(new ResourceLocation("minecraft", Common.CONFIG.SOUND_GENERATE())), 0.4F);
            }
            case NO_LIQUID -> {
                addParticle(world,pos,ParticleTypes.SMOKE,0.5D,1.2D,0.5D);
                addParticle(world,pos,ParticleTypes.SMOKE,1.2D,0.5D,0.5D);
                addParticle(world,pos,ParticleTypes.SMOKE,0.5D,0.5D,1.2D);
                addParticle(world,pos,ParticleTypes.SMOKE,0.5D,-0.2D,0.5D);
                addParticle(world,pos,ParticleTypes.SMOKE,-0.2D,0.5D,0.5D);
                addParticle(world,pos,ParticleTypes.SMOKE,0.5D,0.5D,-0.2D);
                playSound(player, pos, new SoundEvent(new ResourceLocation("minecraft", Common.CONFIG.SOUND_NO_LIQUID())), 0.4F);
            }
            case PLACE_BLOCK -> addParticle(world,pos, ParticleTypes.ENCHANT,0.5D,0.5D,0.5D);
            case CLEAR_BLOCK -> addParticle(world,pos, ParticleTypes.LAVA,0.5D,0.5D,0.5D);
        }
    }

    private static void addParticle(Level world, BlockPos pos, ParticleOptions particle, double x, double y, double z) {
        if(Common.CONFIG.SHOW_EFFECTS()) {
            world.addParticle(particle, (double)pos.getX() + x, (double)pos.getY() + y, (double)pos.getZ() + z, 0.0D, 0.0D, 0.0D);
        }
    }

    public static void sendMessage(Player player, String message, String variable) {
        if(Common.CONFIG.SHOW_MESSAGES() && Helper.isClient(player.getLevel())) {
            player.displayClientMessage(Component.translatable(message, variable.isEmpty() ? new Object[0] : variable),true);
        }
    }

    //todo
    public static void showScreen(Screen screen, Player player, Level world, BlockPos pos) {
        if(Helper.isClient(world)) {
//            switch(screen) {
//                case SKYDIVE -> Minecraft.getInstance().setScreen(new SkydiveScreen(player,world,pos));
//                case STATUE -> Minecraft.getInstance().setScreen(new StatueScreen(player,world,pos));
//                case HARVEST -> Minecraft.getInstance().setScreen(new HarvestScreen(player,world,pos));
//                case TREE -> Minecraft.getInstance().setScreen(new TreeScreen(player,world,pos));
//                case SCHEMATIC -> Minecraft.getInstance().setScreen(new SchematicScreen(player,world,pos));
//            }
        }
    }
}