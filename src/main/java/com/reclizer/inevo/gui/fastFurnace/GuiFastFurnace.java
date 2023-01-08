package com.reclizer.inevo.gui.fastFurnace;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.blocks.tileEntity.TileEntityEnergyStorage;
import com.reclizer.inevo.blocks.tileEntity.TileEntityFastFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;

public class GuiFastFurnace extends GuiContainer {
    private static final String TEXTURE_PATH = IndustrialEvolved.MODID + ":" + "textures/gui/container/fast_furnace.png";
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(TEXTURE_PATH);

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;
    private TileEntityFastFurnace furnace;

    public GuiFastFurnace( TileEntityFastFurnace tileentity,ContainerFastFurnace container)
    {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        furnace=tileentity;
    }


//    @Override
//    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
//    {
//
//        this.fontRenderer.drawString(Integer.toString(furnace.getEnergy()),90,52,4210752);
//    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        mc.getTextureManager().bindTexture(TEXTURE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int energy=furnace.getClientEnergy();
        drawEnergyBar(energy);

        if(furnace.getClientProgress()>0){
            int percentage=100-furnace.getClientProgress()*100/TileEntityFastFurnace.MAX_PROGRESS;
            drawString(mc.fontRenderer,"Progress:"+percentage+"%",guiLeft+10,guiTop+50,0xffffff);
        }

        }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);

        if (mouseX > guiLeft + 10 && mouseX < guiLeft + 112 && mouseY > guiTop + 5 && mouseY < guiTop + 15) {
            drawHoveringText(Collections.singletonList("Energy: " + furnace.getClientEnergy()), mouseX, mouseY, fontRenderer);
        }

    }


//    private int getEnergyStoredScaled(int pixels){
//        int i=furnace.getEnergy();
//        int j=1000;
//        return i!=0&&j!=0?i*pixels/j:0;
//    }

    private void drawEnergyBar(int energy) {
        drawRect(guiLeft + 10, guiTop + 5, guiLeft + 112, guiTop + 15, 0xff555555);
        int percentage = energy * 100 / TileEntityFastFurnace.MAX_POWER;
        for (int i = 0 ; i < percentage ; i++) {
            drawVerticalLine(guiLeft + 10 + 1 + i, guiTop + 5, guiTop + 14, i % 2 == 0 ? 0xffff0000 : 0xff000000);
        }
    }




}
