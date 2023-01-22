package com.reclizer.inevo.item.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.init.ModCreativeTab;
import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.util.CommonFunctions;
import com.reclizer.inevo.util.IHasModel;
import com.reclizer.inevo.util.NBTStrDef.IENBTDef;
import com.reclizer.inevo.util.NBTStrDef.IENBTUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class ItemArmorBase extends ItemArmor implements IHasModel {
    /**
     * @param material �����ײ��ʡ���Enum���ͣ��������EnumHelper����Զ����ʵ�������Ǹ���ԭ��Ļ��ײ��ʡ�
     * @param renderType ������д 0 ���ɣ��� Mod �ӵĻ��׺�������
     * @param slot ��Ƭ���״��ģ�ͷ�ϣ����ϣ����ϣ����ϣ�Enum ���͡�
     */
    public ItemArmorBase(String name,ArmorMaterial material, int renderType, EntityEquipmentSlot slot) {
        super(material, renderType, slot);
        setUnlocalizedName(name+"_"+slot.getName());
        setRegistryName(name+"_"+slot.getName());
        setCreativeTab(ModCreativeTab.IE_MISC);

        ModItems.ITEMS.add(this);

    }

    @Override
    public void registerModels() {
        IndustrialEvolved.proxy.registerItemRenderer(this, 0, "inventory");
    }


}
