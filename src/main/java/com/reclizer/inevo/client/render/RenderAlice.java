package com.reclizer.inevo.client.render;

import com.reclizer.inevo.entity.creature.EntityAlice;
import com.reclizer.inevo.client.model.entity.ModelAlice;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAlice extends RenderBiped<EntityAlice> {
    private static final ResourceLocation ALICE_TEXTURES = new ResourceLocation("inevo:textures/entity/alice.png");

    public RenderAlice(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelAlice(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelAlice(0.5F, true);
                this.modelArmor = new ModelAlice(1.0F, true);
            }
        });
    }

    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }



    @Override
    protected ResourceLocation getEntityTexture(EntityAlice entity)
    {
        return ALICE_TEXTURES;
    }



    //public static final RenderAlice.Factory FACTORY = new RenderAlice.Factory();

//    public static class Factory implements IRenderFactory<RenderAlice> {
//
//        @Override
//        public Render<? super RenderAlice> createRenderFor(RenderManager manager) {
//            return new RenderAlice(manager);
//        }
//
//    }
}
