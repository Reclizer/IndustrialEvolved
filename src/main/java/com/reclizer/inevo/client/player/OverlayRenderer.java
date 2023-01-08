package com.reclizer.inevo.client.player;

import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.util.LangUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OverlayRenderer {
    public static OverlayRenderer instance = new OverlayRenderer();

    //private int summoned = 0;
    //private int summonedInfluence = 0;
    private int playerEnergy = 0;

    public void setSpaceEnergy(int playerSummoned) {
        //this.summoned = summoned;
//        this.summonedInfluence = manaInfluence;
        this.playerEnergy = playerSummoned;
    }


    @SubscribeEvent
    public void renderGameOverlayEvent(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }

        if (Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND).getItem() != ModItems.PHASEDEVICE) {
            return;
        }

        GlStateManager.disableLighting();

        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

        int x = 220;
        int y = 10;
        x = fontRenderer.drawString(LangUtils.localize("tooltip.space_energy"), x, y, 0xffffffff);
        x = fontRenderer.drawString("" + (playerEnergy), x, y, 0xffff0000);
//        x = fontRenderer.drawString("Summoned ", x, y, 0xffffffff);
//        x = fontRenderer.drawString("" + summoned, x, y, 0xffff0000);
//        x = fontRenderer.drawString("  Influence ", x, y, 0xffffffff);
//        x = fontRenderer.drawString("" + summonedInfluence, x, y, 0xffff0000);
//        y += 10;
//        x = 200;

    }
}
