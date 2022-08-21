package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.Common;
import net.minecraft.resources.ResourceLocation;

public interface IRegistryHelper<T> {
    void register(ResourceLocation name, T entry);
    default void register(String name, T entry) {
        register(new ResourceLocation(Common.MOD_ID, name), entry);
    }
}