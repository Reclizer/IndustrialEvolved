package com.reclizer.inevo.network;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.tools.IEnergyContainer;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncPower implements IMessage {
private int energy;

    @Override
    public void fromBytes(ByteBuf buf){
    energy=buf.readInt();
}

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(energy);
    }

    public PacketSyncPower(){

    }

    public PacketSyncPower(int energy){
        this.energy=energy;
    }

    public static class Handler implements IMessageHandler<PacketSyncPower,IMessage>{
        @Override
        public IMessage onMessage(PacketSyncPower message, MessageContext ctx){
            IndustrialEvolved.proxy.addScheduledTaskClient(()->handle(message,ctx));
            return  null;
        }

        private void handle (PacketSyncPower message,MessageContext ctx){
            EntityPlayer player=IndustrialEvolved.proxy.getClientPlayer();

            if(player.openContainer instanceof IEnergyContainer){
                ((IEnergyContainer) player.openContainer).syncPower(message.energy);
            }
        }
    }
}
