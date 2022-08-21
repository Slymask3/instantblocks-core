package com.slymask3.instantblocks.core.init;

import com.slymask3.instantblocks.core.Core;
import net.minecraft.resources.ResourceLocation;

public interface IRegistryHelper<T> {
    void register(ResourceLocation name, T entry);
    default void register(String name, T entry) {
        register(new ResourceLocation(Core.FABRIC_MOD_ID, name), entry);
    }
}