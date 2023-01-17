package com.reclizer.inevo.client.render;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.entity.bullet.EntitySwordLight;
import com.reclizer.inevo.entity.bullet.EntitySwordLight;
import net.minecraft.client.Minecraft;
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

public class RenderSwordLight extends Render<EntitySwordLight> {
    private static ResourceLocation SWORD_LIGHT = new ResourceLocation(IndustrialEvolved.MODID, "textures/effects/sword_light.png");

    public RenderSwordLight(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntitySwordLight entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.depthMask(false);

        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        //GlStateManager.rotate(entity.getRotation(), 0F, 1F, 0F);

        // ----------------------------------------
        GlStateManager.disableCull();
        this.bindTexture(SWORD_LIGHT);

        GlStateManager.enableRescaleNormal();
        GlStateManager.color(1.0f, 1.0f, 1.0f);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

        double scale=1f;
        float vAdd1=(1.0f / 6.0f);
        float vAdd2=(1.0f / 6.0f);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, -scale, 0).tex(0, 0 + vAdd1).endVertex();
        buffer.pos(-scale, +scale, 0).tex(0, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, +scale, 0).tex(1, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, -scale, 0).tex(1, 0 + vAdd1).endVertex();
        tessellator.draw();





        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.depthMask(true);

        GlStateManager.popMatrix();
    }






    public static final RenderSwordLight.Factory FACTORY = new RenderSwordLight.Factory();

    @Override
    protected ResourceLocation getEntityTexture(EntitySwordLight entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntitySwordLight> {

        @Override
        public Render<? super EntitySwordLight> createRenderFor(RenderManager manager) {
            return new RenderSwordLight(manager);
        }

    }
}
