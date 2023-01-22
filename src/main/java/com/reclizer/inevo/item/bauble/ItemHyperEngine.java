package com.reclizer.inevo.item.bauble;

import baubles.api.BaubleType;
import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.item.ItemBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHyperEngine extends ItemBase {
    public ItemHyperEngine(String name) {
        super(name);
        //this.addPropertyOverride(new ResourceLocation(IndustrialEvolved.MODID, "iridium_sword_state")
    }


//    @Override
//    public BaubleType getBaubleType(ItemStack itemStack) {
//        return BaubleType.BODY;
//    }

//    @SideOnly(Side.CLIENT)
//    @Override
//    public void registerModels() {
//        ModelHandler.registerItemAppendMeta(this, 2, LibItemNames.SLIME_BOTTLE);
//    }


}


