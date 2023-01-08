package com.reclizer.inevo.enchantment;

import com.reclizer.inevo.IndustrialEvolved;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = IndustrialEvolved.MODID)
public class EnchantmentManager {
    public static EfficientEnergyCost efficientEu = new EfficientEnergyCost();
    @SubscribeEvent
    public static void onEnchantmentInit(RegistryEvent.Register<Enchantment> event)
    {
        event.getRegistry().register(efficientEu);
    }
}
