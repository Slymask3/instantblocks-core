package com.slymask3.instantblocks.core.builder.type;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.builder.BlockType;
import com.slymask3.instantblocks.core.builder.Builder;
import com.slymask3.instantblocks.core.util.ColorHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.image.BufferedImage;

public abstract class Base<T extends Base<T>> {
    protected final Builder builder;
    public int priority;
    protected boolean replace;
    protected final Level world;
    public int x, y, z;
    protected BlockType blockType;
    protected Direction direction;
    protected final int flag;
    protected double charge;

    public Base(Builder builder, Level world, int x, int y, int z) {
        this.builder = builder;
        this.priority = 0;
        this.replace = true;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.flag = 2;
        this.charge = -1;
    }

    public T setBlock(BlockType blockType) {
        this.blockType = blockType;
        return (T)this;
    }

    public T setBlock(Block block) {
        return setBlock(block.defaultBlockState());
    }

    public T setBlock(BlockState state) {
        return setBlock(BlockType.block(state));
    }

    public T setColor(int color) {
        return setBlock(BlockType.color(color));
    }

    public T setImageColor(BufferedImage image, int x, int y, boolean useRGB) {
        return useRGB ? setColor(image.getRGB(x, y)) : setBlock(ColorHelper.getWoolColor(ColorHelper.getColorAt(image, x, y)));
    }

    public T setStone() {
        return setBlock(BlockType.stone());
    }

    public T setDirection(Direction direction) {
        this.direction = direction;
        return (T)this;
    }

    public T setCharge(double charge) {
        this.charge = charge;
        return (T)this;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(x,y,z);
    }

    public Level getLevel() {
        return this.world;
    }

    protected void setPriority(int priority) {
        this.priority = Core.CONFIG.ORIGINAL_INSTANT() ? 0 : priority;
    }

    public void queue() {
        this.queue(0);
    }

    public void queue(int priority) {
        this.queue(priority,true);
    }

    public void queue(int priority, boolean replace) {
        this.setPriority(priority);
        this.replace = replace;
        this.build();
    }

    public void build() {}
}