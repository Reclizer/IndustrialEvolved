package com.reclizer.inevo.client.audio;

import com.reclizer.inevo.IndustrialEvolved;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;


@Mod.EventBusSubscriber
public class InevoSounds {
    private InevoSounds(){}

    private static final List<SoundEvent> sounds = new ArrayList<>();
    public static final SoundEvent ENTITY_LASER = createSound("entity.inevo_float.laser");
    //激光的声音 许可:CC-BY 作者:nsstudios 来源:耳聆网 https://www.ear0.com/sound/10124


    public static SoundEvent createSound(String name){
        return createSound(IndustrialEvolved.MODID, name);
    }

    public static SoundEvent createSound(String modID, String name){
        // All the setRegistryName methods delegate to this one, it doesn't matter which you use.
        SoundEvent sound = new SoundEvent(new ResourceLocation(modID, name)).setRegistryName(name);
        sounds.add(sound);
        return sound;
    }


    @SubscribeEvent
    public static void register(RegistryEvent.Register<SoundEvent> event){
        event.getRegistry().registerAll(sounds.toArray(new SoundEvent[0]));
    }
}
