package com.reclizer.inevo.player;

import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class SummonedTickHandler {
    public static SummonedTickHandler instance = new SummonedTickHandler();

    @SubscribeEvent
    public void onWorldTick(TickEvent.WorldTickEvent evt) {
        if (evt.phase == TickEvent.Phase.START) {
            return;
        }
        World world = evt.world;
        WorldSummoned.get(world).tick(world);
    }
}
