package com.reclizer.inevo.others;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public interface ITileComponent {

    void tick();

    void read(NBTTagCompound nbtTags);

    void read(ByteBuf dataStream);

    void write(NBTTagCompound nbtTags);

    //void write(TileNetworkList data);

    void invalidate();
}
