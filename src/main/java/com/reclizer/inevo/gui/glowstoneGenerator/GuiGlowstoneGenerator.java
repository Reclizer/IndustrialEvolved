package com.reclizer.inevo.gui.glowstoneGenerator;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.blocks.tileEntity.TileEntityGlowstoneGenerator;
import com.reclizer.inevo.gui.dirtCompressor.ContainerDirtCompressor;
import ibxm.Player;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiGlowstoneGenerator extends GuiContainer {
    private static final String TEXTURE_PATH = IndustrialEvolved.MODID + ":" + "textures/gui/container/glowstone_generator.png";
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(TEXTURE_PATH);
    private  final InventoryPlayer player;
    private  final TileEntityGlowstoneGenerator tileentity;


    public GuiGlowstoneGenerator(InventoryPlayer player, TileEntityGlowstoneGenerator tileentity)
    {
        super(new ContainerGlowstoneGenerator(player,tileentity));
        this.player=player;
        this.tileentity=tileentity;
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String title = I18n.format("tile.glowstone_generator.name");//添加物品名字
        this.drawCenteredString(this.fontRenderer,title,this.xSize/2,6,0x00404040);//文字居中

        //String tileName=this.tileentity.getDisplayName().getUnformattedText();
        //this.fontRenderer.drawString(tileName,(this.xSize/2-this.fontRenderer.getStringWidth(tileName)/2)-5,6,4210752);

        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(),7,this.ySize-96+2,4210752);
        this.fontRenderer.drawString(Integer.toString(this.tileentity.getEnergyStored()),115,72,4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f,1.0f,1.0f,1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft,this.guiTop,0,0,this.xSize,this.ySize);

        int l=this.getCookProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft+113,this.guiTop+32,176,14,l+1,16);
        int k= this.getEnergyStoredScaled(75);
        this.drawTexturedModalRect(this.guiLeft+152,this.guiTop+7,176,32,16,76-k);
    }

    private int getEnergyStoredScaled(int pixels){
        int i=this.tileentity.getEnergyStored();
        int j=this.tileentity.getMaxEnergyStored();
        return i!=0&&j!=0?i*pixels/j:0;
    }

    private int getCookProgressScaled(int pixels){
        int i=this.tileentity.cooktime;
        return i!=0?i*pixels/25:0;
    }






}
