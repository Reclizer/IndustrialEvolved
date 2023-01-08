package com.reclizer.inevo.init;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.blocks.ModBlocks;
import com.reclizer.inevo.gui.ModGuiElementLoader;
import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class RegistryHandler {
    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onBlockRegister(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
    }
/*
    @SubscribeEvent
    public static void onEnchantmentRegister(RegistryEvent.Register<Enchantment> event)
    {
        ModEnchantmentInit.BeforeRegister();
        event.getRegistry().registerAll(ModEnchantmentInit.ENCHANTMENT_LIST.toArray(new Enchantment[0]));

//		for (Enchantment enchantment : Enchantment.REGISTRY) {
//			IdlFramework.Log("registered enchantments: %s", enchantment.getName());
//		}
    }
 */

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event)
    {
        for(Item item : ModItems.ITEMS)
        {
            if (item instanceof IHasModel)
            {
                ((IHasModel)item).registerModels();
            }
        }

        for(Block block : ModBlocks.BLOCKS)
        {
            if (block instanceof IHasModel)
            {
                ((IHasModel)block).registerModels();
            }
        }

        //RenderHandler.registerEntityRenders();
    }

    public static void initRegistries(){
        NetworkRegistry.INSTANCE.registerGuiHandler(IndustrialEvolved.instance, new ModGuiElementLoader());
    }
/*
    public static void preInitRegistries(FMLPreInitializationEvent event)
    {
        //GameRegistry.registerWorldGenerator(new ModWorldGenOld(), 100);
        //GameRegistry.registerWorldGenerator(new ModWorldGenNew(), 120);

        InitBiome.registerBiomes();
        InitDimension.registerDimensions();

        ModEntityInit.registerEntities();
    }

 */

    public static void postInitReg()
    {
        //WorldType TYPE_ONE = new WorldTypeOne();
    }
/*
    public static void initRegistries(FMLInitializationEvent event)
    {
        ModSoundHandler.soundRegister();
    }

 */

    public static void serverRegistries(FMLServerStartingEvent event)
    {
        //event.registerServerCommand(new CommandDimTeleport());
    }
}
