package com.reclizer.inevo.network;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.client.player.OverlayRenderer;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSendSummoned implements IMessage {
    //private int summoned;
    //private int influence;
    private int playerSummoned;

    @Override
    public void fromBytes(ByteBuf buf) {
        playerSummoned = buf.readInt();
        //influence = buf.readInt();
        //playerSummoned = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(playerSummoned);
        //buf.writeInt(influence);
        //buf.writeInt(playerSummoned);
    }

    // You need this constructor!
    public PacketSendSummoned() {
    }

    public PacketSendSummoned(int playerSummoned) {
        this.playerSummoned = playerSummoned;
        //this.influence = influence;
        //this.playerSummoned = playersummoned;
    }

    public static class Handler implements IMessageHandler<PacketSendSummoned, IMessage> {
        @Override
        public IMessage onMessage(PacketSendSummoned message, MessageContext ctx) {
            IndustrialEvolved.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }

        private void handle(PacketSendSummoned message, MessageContext ctx) {
            OverlayRenderer.instance.setSummoned(message.playerSummoned);
        }
    }
}
