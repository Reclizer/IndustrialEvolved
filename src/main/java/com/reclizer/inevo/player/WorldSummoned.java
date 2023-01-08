package com.reclizer.inevo.player;

import com.reclizer.inevo.net.Messages;
import com.reclizer.inevo.net.PacketSendSummoned;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import java.util.Map;

public class WorldSummoned extends WorldSavedData {

    private static final String NAME = "InevoSummonedData";

    private int ticker = 10;
    private int tickTime=600;

    public WorldSummoned() {
        super(NAME);
    }

    public WorldSummoned(String name) {
        super(name);
    }

    public static WorldSummoned get(World world) {
        MapStorage storage = world.getPerWorldStorage();
        WorldSummoned instance = (WorldSummoned) storage.getOrLoadData(WorldSummoned.class, NAME);

        if (instance == null) {
            instance = new WorldSummoned();
            storage.setData(NAME, instance);
        }
        return instance;
    }


    public void tick(World world) {
        ticker--;
        tickTime--;
        if(tickTime<0){
            reduceSummoned(world);
            tickTime=600;
        }
        if (ticker > 0) {
            return;
        }
        ticker = 10;
        //growMana(world);
        sendSummoned(world);
    }

    private void sendSummoned(World world) {
        for (EntityPlayer player : world.playerEntities) {

            PlayerSummoned playerSummoned = PlayerProperties.getPlayerSummoned(player);
            Messages.INSTANCE.sendTo(new PacketSendSummoned(playerSummoned.getSummoned()), (EntityPlayerMP) player);
        }
    }

    private void reduceSummoned(World world) {
        for (EntityPlayer player : world.playerEntities) {
            PlayerSummoned playerSummoned = PlayerProperties.getPlayerSummoned(player);
            playerSummoned.setSummoned(playerSummoned.getSummoned()>0 ? playerSummoned.getSummoned()-1:playerSummoned.getSummoned());
        }
    }

//    private void growMana(World world) {
//        for (Map.Entry<ChunkPos, ManaSphere> entry : spheres.entrySet()) {
//            ManaSphere sphere = entry.getValue();
//            if (sphere.getRadius() > 0) {
//                if (world.isBlockLoaded(sphere.getCenter())) {
//                    float currentMana = sphere.getCurrentMana();
//                    currentMana += .01f;
//                    if (currentMana >= 5) {
//                        currentMana = 5;
//                    }
//                    sphere.setCurrentMana(currentMana);
//                    markDirty();
//                }
//            }
//        }
//    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return null;
    }
}
