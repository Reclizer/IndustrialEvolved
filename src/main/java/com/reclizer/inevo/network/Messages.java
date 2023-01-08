package com.reclizer.inevo.network;


import com.reclizer.inevo.input.PacketToggleMode;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Messages {

    public static SimpleNetworkWrapper INSTANCE;

    private static int ID = 0;
    private static int nextID() {
        return ID++;
    }

    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);

        // Server side
        INSTANCE.registerMessage(PacketToggleMode.Handler.class, PacketToggleMode.class, nextID(), Side.SERVER);




        INSTANCE.registerMessage(PacketSyncPower.Handler.class, PacketSyncPower.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketSendSummoned.Handler.class, PacketSendSummoned.class, nextID(), Side.CLIENT);
    }
}
