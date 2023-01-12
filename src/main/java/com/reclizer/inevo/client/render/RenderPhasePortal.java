package com.reclizer.inevo.client.render;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.entity.bullet.EntityBullet;
import com.reclizer.inevo.entity.construct.EntityPhasePortal;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class RenderPhasePortal extends Render<EntityPhasePortal> {
    private static ResourceLocation portal = new ResourceLocation(IndustrialEvolved.MODID, "textures/effects/portal.png");

    public RenderPhasePortal(RenderManager renderManager) {
        super(renderManager);
    }



    @Override
    public void doRender(EntityPhasePortal entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.depthMask(false);

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);


        // ----------------------------------------

        this.bindTexture(portal);

        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0f, 1.0f, 1.0f);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

        long t = System.currentTimeMillis() % 6;
        renderBillboardQuad(1.7f, t * (1.0f / 6.0f), (1.0f / 6.0f));

        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.depthMask(true);

        GlStateManager.popMatrix();
    }

    private void renderBillboardQuad(double scale, float vAdd1, float vAdd2) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, -scale, 0).tex(0, 0 + vAdd1).endVertex();
        buffer.pos(-scale, +scale, 0).tex(0, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, +scale, 0).tex(1, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, -scale, 0).tex(1, 0 + vAdd1).endVertex();
        tessellator.draw();
    }

    public static final RenderPhasePortal.Factory FACTORY = new RenderPhasePortal.Factory();


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPhasePortal entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityPhasePortal> {

        @Override
        public Render<? super EntityPhasePortal> createRenderFor(RenderManager manager) {
            return new RenderPhasePortal(manager);
        }

    }
}
