package com.reclizer.inevo.player;

import com.reclizer.inevo.network.Messages;
import com.reclizer.inevo.network.PacketSendSummoned;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class WorldEnergy extends WorldSavedData {

    private static final String NAME = "InevoSpaceEnergyData";

    private int ticker = 10;
    private int tickTime=50;

    public WorldEnergy() {
        super(NAME);
    }

    public WorldEnergy(String name) {
        super(name);
    }

    public static WorldEnergy get(World world) {
        MapStorage storage = world.getPerWorldStorage();
        WorldEnergy instance = (WorldEnergy) storage.getOrLoadData(WorldEnergy.class, NAME);

        if (instance == null) {
            instance = new WorldEnergy();
            storage.setData(NAME, instance);
        }
        return instance;
    }


    public void tick(World world) {
        ticker--;
        tickTime--;
        if(tickTime<0){
            growSpaceEnergy(world);
            tickTime=50;
        }
        if (ticker > 0) {
            return;
        }
        ticker = 10;
        //growMana(world);
        sendSpaceEnergy(world);
    }

    private void sendSpaceEnergy(World world) {
        for (EntityPlayer player : world.playerEntities) {

            PlayerEnergy playerEnergy = PlayerProperties.getPlayerEnergy(player);
            Messages.INSTANCE.sendTo(new PacketSendSummoned(playerEnergy.getSpaceEnergy()), (EntityPlayerMP) player);
        }
    }

    private void growSpaceEnergy(World world) {
        for (EntityPlayer player : world.playerEntities) {
            PlayerEnergy playerEnergy = PlayerProperties.getPlayerEnergy(player);
            playerEnergy.setSpaceEnergy(playerEnergy.getSpaceEnergy()<playerEnergy.getMaxSpaceEnergy() ? playerEnergy.getSpaceEnergy()+1: playerEnergy.getSpaceEnergy());
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
