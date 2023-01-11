package com.reclizer.inevo.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class PlayerProperties {
    @CapabilityInject(PlayerEnergy.class)
    public static Capability<PlayerEnergy> PLAYER_SPACEENERGY;

    public static PlayerEnergy getPlayerEnergy(EntityPlayer player) {
        return player.getCapability(PLAYER_SPACEENERGY, null);
    }
}
