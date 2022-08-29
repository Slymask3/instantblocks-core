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

    public WandChargeScreen(WandChargeMenu menu, Inventory inventory, Component title) {
        super(menu,inventory,title);
        this.imageHeight = 133;
    }

    public void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void render(PoseStack poseStack, int $$1, int $$2, float $$3) {
        this.renderBackground(poseStack);
        super.render(poseStack, $$1, $$2, $$3);
        this.renderTooltip(poseStack, $$1, $$2);
    }

    protected void renderBg(PoseStack poseStack, float $$1, int $$2, int $$3) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int left = (this.width - this.imageWidth) / 2;
        int top = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, left, top, 0, 0, this.imageWidth, this.imageHeight);
    }
}