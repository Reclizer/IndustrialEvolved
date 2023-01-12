package com.reclizer.inevo.client.render;

import com.reclizer.inevo.client.model.entity.ModelFloatingCannon;
import com.reclizer.inevo.entity.creature.EntityFloatingCannon;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
@SideOnly(Side.CLIENT)
public class RenderFloatingCannon extends RenderLiving<EntityFloatingCannon> {
    private static final ResourceLocation FLOAT_TEXTURES = new ResourceLocation("inevo:textures/entity/floating_cannon.png");


    public RenderFloatingCannon(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelFloatingCannon(), 0.1F);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityFloatingCannon entity) {
        return FLOAT_TEXTURES;
    }
}
