package com.reclizer.inevo.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event)
    {
        Entity entity =event.getEntity();
        if(entity instanceof EntityPlayer)
        {
            String message ="welcome to FMLTutor,"+entity.getName()+"!";
            TextComponentString test =new TextComponentString(message);
            entity.sendMessage(test);
        }
    }
}
