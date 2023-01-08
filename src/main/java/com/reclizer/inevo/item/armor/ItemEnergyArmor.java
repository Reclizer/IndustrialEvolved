package com.reclizer.inevo.item.armor;

import com.reclizer.inevo.tools.IEnergizedItem;
import com.reclizer.inevo.item.ItemArmorBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemEnergyArmor extends ItemArmorBase implements IEnergizedItem {
    /**
     * @param name
     * @param material   “护甲材质”，Enum类型，必须借助EnumHelper获得自定义的实例，除非复用原版的护甲材质。
     * @param renderType 幻数，写 0 即可，对 Mod 加的护甲毫无作用
     * @param slot       这片护甲穿哪？头上？身上？腿上？脚上？Enum 类型。
     */
    public ItemEnergyArmor(String name, ArmorMaterial material, int renderType, EntityEquipmentSlot slot) {
        super(name, material, renderType, slot);
    }

    @Override
    public double getEnergy(ItemStack itemStack) {
        return 0;
    }

    @Override
    public void setEnergy(ItemStack itemStack, double amount) {

    }

    @Override
    public double getMaxEnergy(ItemStack itemStack) {
        return 0;
    }

    @Override
    public double getMaxTransfer(ItemStack itemStack) {
        return 0;
    }

    @Override
    public boolean canReceive(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean canSend(ItemStack itemStack) {
        return false;
    }
}
