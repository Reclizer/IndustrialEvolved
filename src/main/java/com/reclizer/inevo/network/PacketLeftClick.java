package com.reclizer.inevo.network;

import com.reclizer.inevo.item.ItemDeBug;
import com.reclizer.inevo.item.ModItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketLeftClick implements IMessage {
    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketLeftClick, IMessage> {

        @Override
        public IMessage onMessage(PacketLeftClick message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            player.mcServer.addScheduledTask(() -> ((ItemDeBug) ModItems.DE_BUG).trySpawnBurst(player));
            //player.server.addScheduledTask(() -> ((ItemDeBug) ModItems.DE_BUG).trySpawnBurst(player));
            return null;
        }
    }

}
