package com.reclizer.inevo.item;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.item.armor.adaptArmor.ItemAdaptArmor;
import com.reclizer.inevo.item.bauble.ItemAntigravityEquipment;
import com.reclizer.inevo.item.bauble.ItemHyperEngine;
import com.reclizer.inevo.item.weapon.ItemElectricBow;
import com.reclizer.inevo.item.weapon.ItemPhaseDevice;
import com.reclizer.inevo.item.weapon.ItemPhaseSword;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;
import java.util.List;



public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    //Basic
    //public static final Item PAPER_BLOOD = new ItemBase("paper_blood");
    //录入物品id，格式为前大写字母，后小写字母
    public static final Item DIRT_BALL = new ItemBase("dirt_ball");
    public static final Item INGOT_COPPER = new ItemBase("ingot_copper");
    public static final Item PHASE_SWORD = new ItemPhaseSword(12000,"phase_sword");//最后一项模仿钻石剑的钻石修复和耐久属性
    public static final Item ELECTRICBOW = new ItemElectricBow(12000,"electri_bow");
    public static final Item PHASEDEVICE =new ItemPhaseDevice(12000,"phase_device");
    public static final Item DE_BUG =new ItemDeBug("item_debug");
    



    //public static final Item ANTIGRAVITY =new ItemAntigravityEquipment(12000,"antigravity_equipment");
     public static final Item HYPER_ENGINE =new ItemHyperEngine("hyper_engine");


    public static final ItemArmor.ArmorMaterial moroonArmorMaterial = EnumHelper.addArmorMaterial(
            "gene",
            IndustrialEvolved.MODID+":gene",
            80,
            new int[] {6, 8, 10, 6},
            2,
            SoundEvents.ITEM_ARMOR_EQUIP_GOLD,
            15
    );


        //实例名称,盔甲材质名称前缀,
        // 盔甲耐久基数*13/15/16/11(靴子/护腿/胸甲/头盔 耐久),
        // 防御点数(靴子/护腿/胸甲/头盔 点数),
        // 伤害点数,
        // 佩戴盔甲声音,
        // 盔甲韧性

    public  static final ItemAdaptArmor ADAPT_HELMET=new ItemAdaptArmor(EntityEquipmentSlot.HEAD);
    public  static final ItemAdaptArmor ADAPT_CHESTPLATE=new ItemAdaptArmor(EntityEquipmentSlot.CHEST);
    public  static final ItemAdaptArmor ADAPT_LEGGINGS=new ItemAdaptArmor(EntityEquipmentSlot.LEGS);
    public  static final ItemAdaptArmor ADAPT_BOOTS=new ItemAdaptArmor(EntityEquipmentSlot.FEET);



	/*ingot_copper
	WOOD(0, 59, 2.0F, 0.0F, 15),
    STONE(1, 131, 4.0F, 1.0F, 5),
    IRON(2, 250, 6.0F, 2.0F, 14),
    DIAMOND(3, 1561, 8.0F, 3.0F, 10),
    GOLD(0, 32, 12.0F, 0.0F, 22);

    harvestLevel, maxUses, efficiency, damage, enchantability
	*/

    //Tool Material
//	public static final Item BLOOD_IRON_INGOT = new ItemBase("blood_iron_ingot");
//
//    public static final Item.ToolMaterial TOOL_MATERIAL_BLOOD =
//			EnumHelper.addToolMaterial("material_blood", 3, 512, 3.0F, 4F, 20).setRepairItem(new ItemStack( ModItems.BLOOD_IRON_INGOT));
//
//	public static final ItemKinshipSword KINSHIP_SWORD = new ItemKinshipSword("kinship_sword", TOOL_MATERIAL_BLOOD);

    //Armor
//    LEATHER("leather", 5, new int[]{1, 2, 3, 1}, 15, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F),
//    CHAIN("chainmail", 15, new int[]{1, 4, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F),
//    IRON("iron", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F),
//    GOLD("gold", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F),
//    DIAMOND("diamond", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.0F);
    //Note that if you want to set a mod thing as repair material, define them before the material, otherwise it will be empty.

//    public static final ItemArmor.ArmorMaterial moroonArmorMaterial = EnumHelper.addArmorMaterial(
//            "idlframewok:armor_moroon", "idlframewok:armor_moroon", 80, new int[] {3, 6, 8, 3}, 2, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 2)
//            .setRepairItem(new ItemStack(Items.QUARTZ));
//

    //Food
//	static PotionEffect eff = new PotionEffect(MobEffects.LEVITATION, TICK_PER_SECOND * 60, 0);
//	public static final ItemFoodBase FIGHT_BREAD = (ItemFoodBase) new ItemFoodBase("war_bread", 10, 10, false).
//			setPotionEffect(eff, 1.0f).
//			setAlwaysEdible();
//    public static final ItemFoodBase MEMORY_BREAD = new ItemFoodBase("memory_bread", 3, 0.6f, false).SetXP(10);





    //Armor
//	public static final ItemHelmSniper helmetSniper = (ItemHelmSniper) new ItemHelmSniper("helmet_sniper", moroonArmorMaterialSniper, 1, EntityEquipmentSlot.HEAD);
//
//	public static final ItemArmorBase[] MOR_GENERAL_ARMOR =
//			{        new ItemArmorBase("mor_armor_1", moroonArmorMaterial, 1, EntityEquipmentSlot.HEAD)
//					,new ItemArmorBase("mor_armor_2", moroonArmorMaterial, 1, EntityEquipmentSlot.CHEST)
//					,new ItemArmorBase("mor_armor_3", moroonArmorMaterial, 1, EntityEquipmentSlot.LEGS)
//					,new ItemArmorBase("mor_armor_4", moroonArmorMaterial, 1, EntityEquipmentSlot.FEET)
//			};

    //public static final ItemSkillDecodeItem skillDecodeItem = (ItemSkillDecodeItem) new ItemSkillDecodeItem("skill_decode_item").setRarity(EnumRarity.RARE);

    //Package Example
//	public static final ItemPackage RANDOM_SKILL = (ItemPackage) new ItemPackage("random_skill", new Item[]{
//			Items.BLAZE_ROD, Items.PAPER
//	}).setMaxStackSize(1);
}