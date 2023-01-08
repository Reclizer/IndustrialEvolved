package com.reclizer.inevo.potion;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class PotionRegistryHandler {
    public static final PhasePotion PHASE_POTION = new PhasePotion();
    public static final PhasePotionT PHASE_POTION_II = new PhasePotionT();
    public static final GeneLockPotion GENE_LOCK_POTION = new GeneLockPotion();
    public static final OverloadProtectPotion OVERLOAD_PROTECT_POTION = new OverloadProtectPotion();
    public static final AdaptPotion ADAPT_POTION = new AdaptPotion();
    @SubscribeEvent
    public static void onPotionRegistration(RegistryEvent.Register<Potion> event) {
        //event.getRegistry().registerAll(new PhasePotion());
        IForgeRegistry<Potion> registry= event.getRegistry();
        registry.register(PHASE_POTION);
        IForgeRegistry<Potion> registry1= event.getRegistry();
        registry1.register(PHASE_POTION_II);
        IForgeRegistry<Potion> registry2= event.getRegistry();
        registry2.register(GENE_LOCK_POTION);
        IForgeRegistry<Potion> registry3= event.getRegistry();
        registry3.register(OVERLOAD_PROTECT_POTION);
        IForgeRegistry<Potion> registry4= event.getRegistry();
        registry4.register(ADAPT_POTION);
    }
}
