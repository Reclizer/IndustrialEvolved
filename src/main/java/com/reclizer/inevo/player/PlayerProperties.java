package com.reclizer.inevo.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class PlayerProperties {
    @CapabilityInject(PlayerSummoned.class)
    public static Capability<PlayerSummoned> PLAYER_SUMMONED;

    public static PlayerSummoned getPlayerSummoned(EntityPlayer player) {
        return player.getCapability(PLAYER_SUMMONED, null);
    }
}
