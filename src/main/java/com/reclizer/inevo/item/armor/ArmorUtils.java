package com.reclizer.inevo.item.armor;

import com.reclizer.inevo.item.ModItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ArmorUtils {


    public static boolean fullEquipped(EntityPlayer player) {
        NonNullList<ItemStack> armor = player.inventory.armorInventory;
        return armor.get(3).getItem() == ModItems.ADAPT_HELMET && armor.get(2).getItem() == ModItems.ADAPT_CHESTPLATE && armor.get(1).getItem() == ModItems.ADAPT_LEGGINGS && armor.get(0).getItem() == ModItems.ADAPT_BOOTS;
    }

}
