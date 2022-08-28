package com.slymask3.instantblocks.core.gui.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.inventory.WandChargeMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WandChargeScreen extends AbstractContainerScreen<WandChargeMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Core.FABRIC_MOD_ID,"textures/gui/container/wand.png");

    public WandChargeScreen(WandChargeMenu menu, Inventory $$1, Component $$2) {
        super(menu,$$1,$$2);
    }

    public void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    public void render(PoseStack $$0, int $$1, int $$2, float $$3) {
        this.renderBackground($$0);
        super.render($$0, $$1, $$2, $$3);
        this.renderTooltip($$0, $$1, $$2);
    }

    protected void renderBg(PoseStack $$0, float $$1, int $$2, int $$3) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int $$4 = this.leftPos;
        int $$5 = this.topPos;
        this.blit($$0, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
    }
}
