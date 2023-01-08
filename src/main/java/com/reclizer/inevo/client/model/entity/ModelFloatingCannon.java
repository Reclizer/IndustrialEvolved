package com.reclizer.inevo.client.model.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFloatingCannon extends ModelBase {
    private final ModelRenderer Head;

    public ModelFloatingCannon() {
        textureWidth = 64;
        textureHeight = 64;

        Head = new ModelRenderer(this);
        Head.setRotationPoint(0.0F, 24.0F, 0.0F);
        setRotationAngle(Head, -1.5708F, 0.0F, 0.0F);
        Head.cubeList.add(new ModelBox(Head, 18, 0, -2.5F, -0.5F, -2.0F, 5, 1, 4, 0.0F, false));
        Head.cubeList.add(new ModelBox(Head, 0, 13, -2.5F, -0.5F, -7.5F, 5, 1, 10, -0.2F, false));
        Head.cubeList.add(new ModelBox(Head, 0, 0, -1.5F, -0.5F, -7.7F, 3, 1, 12, 0.1F, false));
        Head.cubeList.add(new ModelBox(Head, 19, 13, -0.5F, -1.0F, -8.0F, 1, 2, 11, 0.1F, false));
        Head.cubeList.add(new ModelBox(Head, 0, 6, -0.5F, -0.5F, 0.5F, 1, 1, 5, -0.1F, false));
        Head.cubeList.add(new ModelBox(Head, 0, 0, -0.5F, -0.5F, 0.0F, 1, 1, 5, 0.0F, false));
        Head.cubeList.add(new ModelBox(Head, 18, 5, -2.5F, -0.5F, -6.0F, 5, 1, 1, 0.2F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);
        Head.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
