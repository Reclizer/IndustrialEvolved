package com.reclizer.inevo.item.armor.adaptArmor;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.client.model.armor.ModelArmorAdapt;
import com.reclizer.inevo.item.ItemArmorBase;
import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.item.armor.ArmorUtils;
import com.reclizer.inevo.util.Reference;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

import static com.reclizer.inevo.potion.PotionRegistryHandler.*;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemAdaptArmor extends ItemArmorBase {
    /**
     * @param name
     * @param material   “护甲材质”，Enum类型，必须借助EnumHelper获               得自定义的实例，除非复用原版的护甲材质。
     * @param renderType 幻数，写 0 即可，对 Mod 加的护甲毫无作用
     * @param slot       这片护甲穿哪？头上？身上？腿上？脚上？Enum 类型。
     */
    public static final String name="adaptable_armor";
    private int healTick=0;
    public static int damageTick=0;
    public static String damage_Type;
    public static HashMap<String,Integer> damageTypeHash = new HashMap<String,Integer>();
    protected Map<EntityEquipmentSlot, ModelBiped> models = null;
    //String name, ArmorMaterial material, int renderType, EntityEquipmentSlot slot

    public ItemAdaptArmor(EntityEquipmentSlot slot) {
        super(name, ModItems.moroonArmorMaterial,0, slot);

    }




    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        // 为了纹理贴图！
        return getArmorTextureAfterInk(stack, slot);
    }
    //public static  final String resourcesId=IndustrialEvolved.MODID+":textures/model/armor_manasteel.png";
    public static  final String resourcesId=IndustrialEvolved.MODID+":textures/model/armor_adapt.png";

    public String getArmorTextureAfterInk(ItemStack stack, EntityEquipmentSlot slot) {
        //return slot == EntityEquipmentSlot.LEGS ? LibResources.MODEL_MANASTEEL_1 : LibResources.MODEL_MANASTEEL_0;
        //return LibResources.MODEL_MANASTEEL_NEW;
        return  resourcesId;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped original) {
        if(true) {
            ModelBiped model = getArmorModelForSlot(entityLiving, itemStack, armorSlot);
            if(model == null)
                model = provideArmorModelForSlot(itemStack, armorSlot);

            if(model != null) {
                model.setModelAttributes(original);
                return model;
            }
        }

        return super.getArmorModel(entityLiving, itemStack, armorSlot, original);
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModelForSlot(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot) {
        if(models == null)
            models = new EnumMap<>(EntityEquipmentSlot.class);

        return models.get(slot);
    }

    @SideOnly(Side.CLIENT)
    public ModelBiped provideArmorModelForSlot(ItemStack stack, EntityEquipmentSlot slot) {
        models.put(slot, new ModelArmorAdapt(slot));
        return models.get(slot);
    }









    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack item) {
        // 穿在身上的时候的每时每刻都会调用的方法，可以用来追加药水效果什么的

        if(!world.isRemote) {
            if (this.armorType == EntityEquipmentSlot.HEAD) {

            } else if (this.armorType == EntityEquipmentSlot.CHEST) {

            } else if (this.armorType == EntityEquipmentSlot.LEGS) {

            } else if (this.armorType == EntityEquipmentSlot.FEET) {

            }

            if (ArmorUtils.fullEquipped(player) && player.getActivePotionEffect(GENE_LOCK_POTION) == null) {
                List<PotionEffect> effects = Lists.newArrayList(player.getActivePotionEffects());

                healTick++;
                if (healTick >= 100) {
                    player.setHealth(Math.min(player.getMaxHealth(), player.getHealth() + player.getHealth()/100));
                    for(PotionEffect potion : Collections2.filter(effects, potion -> potion.getPotion().isBadEffect())) {
                        player.removePotionEffect(potion.getPotion());
                    }
                    healTick = 0;
                }

            }
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> attrib = super.getAttributeModifiers(slot, stack);
        UUID uuid = new UUID((getUnlocalizedName() + slot.toString()).hashCode(), 0);
        //boolean night = ItemNBTHelper.getBoolean(stack, TAG_DAY, false);
        if (slot == this.armorType) {
            attrib.put(SharedMonsterAttributes.MAX_HEALTH.getName(), new AttributeModifier(uuid, "AdaptArmor modifier ",0.25F,  1));
            attrib.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(uuid, "AdaptArmor modifier ",0.25F,  1));
            attrib.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(uuid, "AdaptArmor modifier ",0.05F,  1));
        }
        return attrib;
    }

    @SubscribeEvent
    public static void onArmorHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote&&evt.getEntity() instanceof EntityPlayer) {
            EntityPlayer hurtOne = (EntityPlayer) evt.getEntityLiving();
            if(ArmorUtils.fullEquipped(hurtOne)&& hurtOne.getActivePotionEffect(OVERLOAD_PROTECT_POTION) == null){

                if(evt.getAmount()<hurtOne.getMaxHealth()/2){

                    evt.setAmount(evt.getAmount()/(hurtOne.getMaxHealth()/35));

                }else if(evt.getAmount()>=hurtOne.getHealth()/2){

                    evt.setAmount(evt.getAmount()/10);
                    hurtOne.addPotionEffect(new PotionEffect(OVERLOAD_PROTECT_POTION, 20 * 10, 0));

                }
            }
        }
    }

    @SubscribeEvent
    public static void onAdaptHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote&&evt.getEntity() instanceof EntityPlayer) {
            EntityPlayer hurtOne = (EntityPlayer) evt.getEntityLiving();
            if(ArmorUtils.fullEquipped(hurtOne)){
                DamageSource damageSource = evt.getSource();
                hurtOne.addPotionEffect(new PotionEffect(ADAPT_POTION, 20 * 30, 0));

                if(!damageTypeHash.isEmpty()){

                        if(damageTypeHash.containsKey(damageSource.getDamageType())){
                            damageTypeHash.replace(damageSource.getDamageType(),damageTypeHash.get(damageSource.getDamageType())+1);
                        }else {
                            damageTypeHash.putIfAbsent(damageSource.getDamageType(),1);
                        }

                }else {
                    damageTypeHash.put(damageSource.getDamageType(),1);
                }
            }
        }
    }


    @SubscribeEvent
    public static void damageReduction (LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote&&evt.getEntity() instanceof EntityPlayer) {
            EntityPlayer hurtOne = (EntityPlayer) evt.getEntityLiving();
            if(ArmorUtils.fullEquipped(hurtOne)){
                DamageSource damageSource = evt.getSource();
                if(!damageTypeHash.isEmpty()&& !"outOfWorld".equals(damageSource.getDamageType())
                &&hurtOne.getActivePotionEffect(OVERLOAD_PROTECT_POTION) == null){
                    if(damageTypeHash.containsKey(damageSource.getDamageType())){
                        int k=damageTypeHash.get(damageSource.getDamageType());
                        for(String key :damageTypeHash.keySet()){
                            if(damageTypeHash.get(key)>k){
                                return;
                            }else {
                                       if(k>=3&&k<6){
                                            evt.setAmount(evt.getAmount()/2);
                                        }
                                        if(k>=6&&k<9){
                                            evt.setAmount(evt.getAmount()/4);
                                        }
                                        if(k>=9){
                                            evt.setAmount(evt.getAmount()/10);
                                        }
                            }
                        }

                    }
                }
            }
        }
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCreatureDeath(LivingDeathEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        //EntityLivingBase hurtOne = evt.getEntityLiving();
        //EntityPlayerMP playerMP = (EntityPlayerMP) evt.getEntityLiving();
        if(!world.isRemote&&evt.getEntity() instanceof EntityPlayer) {
            EntityPlayer hurtOne = (EntityPlayer) evt.getEntityLiving();
            DamageSource damageSource = evt.getSource();
            //if(!world.isRemote) {
//            if (evt.isCanceled()) {
//                return;
//            }

            if (ArmorUtils.fullEquipped(hurtOne) && !"outOfWorld".equals(damageSource.getDamageType())) {
                evt.setCanceled(true);
                hurtOne.setHealth(1f);
                damageTypeHash.clear();
                hurtOne.addPotionEffect(new PotionEffect(GENE_LOCK_POTION, 20 * 5, 0));
            }
        }
        //}
    }


    @SubscribeEvent
    public static void onPotionRemoveEvent(PotionEvent.PotionExpiryEvent event){
        EntityLivingBase hurtOne = event.getEntityLiving();
        if(event.getPotionEffect().getPotion()!=null&&event.getPotionEffect().getPotion() == ADAPT_POTION){
            if (!hurtOne.isDead && !hurtOne.world.isRemote)
            {
                damageTypeHash.clear();
                return;
            }

        }
    }
}
