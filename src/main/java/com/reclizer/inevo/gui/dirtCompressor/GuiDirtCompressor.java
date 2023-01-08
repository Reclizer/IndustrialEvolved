package com.reclizer.inevo.gui.dirtCompressor;


import com.reclizer.inevo.IndustrialEvolved;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiDirtCompressor  extends GuiContainer {

    private static final String TEXTURE_PATH = IndustrialEvolved.MODID + ":" + "textures/gui/container/dirt_compressor.png";
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(TEXTURE_PATH);
    private static final int BUTTON_UP = 0;
    private static final int BUTTON_DOWN = 1;

    public GuiDirtCompressor(EntityPlayer player, World world, int x, int y, int z)
    {
        super(new ContainerDirtCompressor(player,world,x,y,z));
        this.xSize = 190;
        this.ySize = 165;
    }

    @Override
    public void drawScreen(int mouseX,int mouseY,float partialTicks){
        super.drawDefaultBackground();
        super.drawScreen(mouseX,mouseY,partialTicks);
        super.renderHoveredToolTip(mouseX,mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //GUI图形位置
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        int barHeight=16;
        int barWidth=2+Math.round(((ContainerDirtCompressor)this.inventorySlots).getCompressorProgress()*0.35F);
        this.drawTexturedModalRect(offsetX+44,offsetY+59,0,208,barWidth,barHeight);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //GUI绘制图形
        //this.drawVerticalLine(30, 19, 36, 0xFF000000);//绘制垂直线条
        //this.drawHorizontalLine(8, 167, 43, 0xFF000000);//绘制水平线条

        String title = I18n.format("tile.dirt_compressor.name");//添加物品名字
        this.drawCenteredString(this.fontRenderer,title,this.xSize/2,6,0x00404040);//文字居中

        //ItemStack item = new ItemStack(ModBlocks.GRID_BLOCK_2);
        //this.itemRender.renderItemAndEffectIntoGUI(item, 8, 20);//物品图标渲染进gui中
    }

    @Override
    public void initGui()
    {
        super.initGui();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.buttonList.add(new GuiButton(BUTTON_UP, offsetX + 173, offsetY + 17, 15, 10, "")
        {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
            {
                if (this.visible)
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);

                    mc.getTextureManager().bindTexture(TEXTURE);
                    int x = mouseX - this.x, y = mouseY - this.y;

                    if (x >= 0 && y >= 0 && x < this.width && y < this.height)
                    {
                        this.drawTexturedModalRect(this.x, this.y, 1, 179, this.width, this.height);
                    }
                    else
                    {
                        this.drawTexturedModalRect(this.x, this.y, 1, 167, this.width, this.height);
                    }
                }
            }
        });
        this.buttonList.add(new GuiButton(BUTTON_DOWN, offsetX + 173, offsetY + 29, 15, 10, "")
        {
            @Override
            public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
            {
                if (this.visible)
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);

                    mc.getTextureManager().bindTexture(TEXTURE);
                    int x = mouseX - this.x, y = mouseY - this.y;

                    if (x >= 0 && y >= 0 && x < this.width && y < this.height)
                    {
                        this.drawTexturedModalRect(this.x, this.y, 20, 179, this.width, this.height);
                    }
                    else
                    {
                        this.drawTexturedModalRect(this.x, this.y, 20, 167, this.width, this.height);
                    }
                }
            }
        });
    }

}
