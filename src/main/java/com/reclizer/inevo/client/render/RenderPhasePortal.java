package com.reclizer.inevo.client.render;


import com.reclizer.inevo.entity.construct.EntityPhasePortal;

import com.reclizer.inevo.tools.ShaderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
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
private static final ResourceLocation PHASE_PORTAL = new ResourceLocation("inevo:textures/effects/portal.png");
    public static int renderTime;
    public static int renderMode;
    protected RenderPhasePortal(RenderManager renderManager) {
        super(renderManager);
    }


    @Override
    public void doRender(EntityPhasePortal entity, double x, double y, double z, float entityYaw, float partialTicks) {
        //GlStateManager.depthMask(false);
        //GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        //GlStateManager.enableBlend();
//        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);
        //GlStateManager.rotate(-90F, 1F, 0F, 0F);
        //GlStateManager.color(1.0f, 1.0f, 1.0f);
        //GlStateManager.shadeModel(GL11.GL_SMOOTH);
        //long t = System.currentTimeMillis() % 6;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y+1.5f, (float)z);
        GlStateManager.rotate(entity.getRotation(), 0F, 1F, 0F);
        //GlStateManager.rotate(45F, 0F, 1F, 0F);
        //float a =entity.getRotation();
        // ----------------------------------------
        GlStateManager.disableCull();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();

        renderTime++;

        if(renderTime>100){
            renderMode++;
            if(renderMode>5){
                renderMode=0;
            }
            renderTime=0;
        }

        //renderBillboardQuad(1.7f, t * (1.0f / 6.0f), (1.0f / 6.0f));
        //==================================================================
        double scale=1.7f;
        float vAdd1=0*(1.0f / 6.0f);
        float vAdd2=(1.0f / 6.0f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        this.bindTexture(PHASE_PORTAL);

        GlStateManager.rotate((entity.ticksExisted + partialTicks) * 4, 0, 0, 1);
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, -scale, 0).tex(0, 0 + vAdd1).endVertex();
        buffer.pos(-scale, +scale, 0).tex(0, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, +scale, 0).tex(1, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, -scale, 0).tex(1, 0 + vAdd1).endVertex();
        tessellator.draw();



        ////GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();






//        GlStateManager.enableLighting();
//        GlStateManager.shadeModel(GL11.GL_FLAT);
//        GlStateManager.enableCull();
//        GlStateManager.popMatrix();
    }

//    private void renderBillboardQuad(double scale, float vAdd1, float vAdd2) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuffer();
//        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
//        buffer.pos(-scale, -scale, 0).tex(0, 0 + vAdd1).endVertex();
//        buffer.pos(-scale, +scale, 0).tex(0, 0 + vAdd1 + vAdd2).endVertex();
//        buffer.pos(+scale, +scale, 0).tex(1, 0 + vAdd1 + vAdd2).endVertex();
//        buffer.pos(+scale, -scale, 0).tex(1, 0 + vAdd1).endVertex();
//        tessellator.draw();
//    }

    private void rotateToPlayer() {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        //GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(renderManager.viewerPosX, 1.0F, 0.0F, 0.0F);

    }


    public static final RenderPhasePortal.Factory FACTORY = new RenderPhasePortal.Factory();

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
