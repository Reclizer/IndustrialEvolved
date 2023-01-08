package com.reclizer.inevo.item.armor;

import com.reclizer.inevo.tools.IEnergizedItem;
import com.reclizer.inevo.item.ItemArmorBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class ItemEnergyArmor extends ItemArmorBase implements IEnergizedItem {
    /**
     * @param name
     * @param material   �����ײ��ʡ���Enum���ͣ��������EnumHelper����Զ����ʵ�������Ǹ���ԭ��Ļ��ײ��ʡ�
     * @param renderType ������д 0 ���ɣ��� Mod �ӵĻ��׺�������
     * @param slot       ��Ƭ���״��ģ�ͷ�ϣ����ϣ����ϣ����ϣ�Enum ���͡�
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
