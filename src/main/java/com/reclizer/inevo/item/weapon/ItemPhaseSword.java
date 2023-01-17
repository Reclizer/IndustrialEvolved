package com.reclizer.inevo.item.weapon;

import com.google.common.collect.Multimap;
import com.reclizer.inevo.entity.bullet.EntityBullet;
import com.reclizer.inevo.item.ItemEnergyBase;
import com.reclizer.inevo.item.ModItems;
import com.reclizer.inevo.item.weapon.mode.PhaseSwordMode;
import com.reclizer.inevo.potion.PotionRegistryHandler;
import com.reclizer.inevo.tools.RayTraceTools;
import com.reclizer.inevo.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class ItemPhaseSword extends ItemEnergyBase {

//    public ItemPhaseSword(String name, ToolMaterial material) {
//        super(name, material);
//        //成员函数


//    }
    private static final List<String> damageNegations = new ArrayList<>();
    private int ticksRight;
    private int ticksDefense;
    private final float attackDamage;
    public static boolean defense = false;

    public ItemPhaseSword(double maxElectricity, String name) {
        super(maxElectricity, name);
        //CommonFunctions.addToEventBus(this);//IDF框架写法
        //MinecraftForge.EVENT_BUS.register(this);//原本的写法


        damageNegations.add(DamageSource.DROWN.damageType);
        damageNegations.add(DamageSource.FALL.damageType);
        damageNegations.add(DamageSource.LAVA.damageType);
        damageNegations.add(DamageSource.IN_FIRE.damageType);
        damageNegations.add(DamageSource.ON_FIRE.damageType);
        damageNegations.add(DamageSource.IN_WALL.damageType);
        damageNegations.add(DamageSource.STARVE.damageType);
        damageNegations.add(DamageSource.OUT_OF_WORLD.damageType);

        this.attackDamage = 400.0F;
        setFull3D();
    }


    @Override
    public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag flag) {
        super.addInformation(stack, player, list, flag);
        PhaseSwordMode mode = getMode(stack);
        list.add("Mode: " + mode.name());
    }

    private PhaseSwordMode getMode(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return PhaseSwordMode.PHASE;
        }
        return PhaseSwordMode.values()[stack.getTagCompound().getInteger("mode")];
    }



    public void toggleMode(EntityPlayer player, ItemStack stack) {
        PhaseSwordMode mode = getMode(stack);
        if (mode == PhaseSwordMode.PHASE) {
            mode = PhaseSwordMode.DEFENSE;
        } else {
            mode = PhaseSwordMode.PHASE;
        }
        player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "Switched to " + mode.name()), false);
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound().setInteger("mode", mode.ordinal());
    }



    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
        ItemStack itemStackIn = player.getHeldItem(hand);
        if (!world.isRemote) {
            switch (getMode(player.getHeldItem(hand))) {
                case PHASE:
                    //phaseBullet(world, player);
                    player.setActiveHand(hand);
                    break;
                case DEFENSE:
                    //levitateEntity(world, player);
                    //player.setHealth(player.getHealth()+2);
                    player.setActiveHand(hand);

                    break;
            }
            //return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
            return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }

        return super.onItemRightClick(world, player, hand);
    }


    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase living, int count) {

        if(!(living instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) living;
        World world = player.getEntityWorld();

        if(count != getMaxItemUseDuration(stack)&&!world.isRemote){
            //BlockPos pos = new BlockPos(player.posX,player.posY-0.2,player.posZ);
            switch (getMode(stack)){
                case PHASE:

                    if(ticksRight<100){
                        ticksRight++;
                    }

                    break;
                case DEFENSE:
                    defense=true;
                    if(ticksDefense<=40){
                        ticksDefense++;
                    }

                    if(ticksDefense%10==0){
                        player.setAbsorptionAmount(player.getAbsorptionAmount()+1);
                    }



                    double playerPosX = player.getPositionEyes(1.0f).x;
                    double playerPosY = player.getPositionEyes(1.0f).y;
                    double playerPosZ = player.getPositionEyes(1.0f).z;

                    double radius =10;
                    List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(playerPosX - radius, playerPosY - radius, playerPosZ - radius, playerPosX + radius, playerPosY + radius, playerPosZ + radius),
                            e -> (e instanceof EntityLiving || e instanceof IProjectile));

                    for(Entity entity : entities) {
                        if(entity instanceof EntityPlayer)
                            continue;
                        double distance = entity.getDistance((double) playerPosX, (double) playerPosY, (double) playerPosZ);
                        if(distance < radius && distance != 0) {
                            if(isBlacklistedEntity(entity)) {
                                continue;
                            }
                            // the multiplier is based on a set rate added to an inverse
                            // proportion to the distance.
                            // we raise the distance to 1 if it's less than one, or it becomes a
                            // crazy multiplier we don't want/need.
                            if(distance < 1D)
                                distance = 2D;
                            double knockbackMultiplier = 1D + (1D / distance);

                            // we also need a reduction coefficient because the above force is
                            // WAY TOO MUCH to apply every tick.
                            double reductionCoefficient = 0.05D;

                            // the resultant vector between the two 3d coordinates is the
                            // difference of each coordinate pair
                            // note that we do not add 0.5 to the y coord, if we wanted to be
                            // SUPER accurate, we would be using
                            // the entity height offset to find its "center of mass"
                            Vec3d angleOfAttack = new Vec3d(entity.posX - (playerPosX + 0.5D), entity.posY - playerPosY, entity.posZ - (playerPosZ + 0.5D));

                            // we use the resultant vector to determine the force to apply.
                            double xForce = angleOfAttack.x * knockbackMultiplier * reductionCoefficient;
                            double yForce = angleOfAttack.y * knockbackMultiplier * reductionCoefficient;
                            double zForce = angleOfAttack.z * knockbackMultiplier * reductionCoefficient;
                            entity.motionX += xForce;
                            entity.motionY += yForce;
                            entity.motionZ += zForce;
                        }}
                    //player.setHealth(player.getHealth()+2);
                    break;
            }
        }
    }

    private boolean isBlacklistedEntity(Entity entity) {
        if (EntityList.getKey(entity) == null) {
            return false;
        }

        String entityName = EntityList.getKey(entity).toString();
        return isBlacklistedLivingEntity(entity, entityName);
    }

    private boolean isBlacklistedProjectile(Entity entity, String entityName) {
        return entity instanceof IProjectile;
    }

    private boolean isBlacklistedLivingEntity(Entity entity, String entityName) {
        return entity instanceof EntityLiving;
    }


    @Override
    public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityLivingBase entityLiving, int itemUseCount) {
        if(!(entityLiving instanceof EntityPlayer)) return;
        if (!world.isRemote) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            switch (getMode(itemstack)){
                case PHASE:
                    phaseBullet(world, player,ticksRight);
                    player.addPotionEffect(new PotionEffect(PotionRegistryHandler.PHASE_POTION_II, 20, 0));
                    ticksRight=0;
                    //player.getCooldownTracker().setCooldown(this, 60);
                    player.getCooldownTracker().setCooldown(this, 60);
                    break;
                case DEFENSE:
                    defense=false;
                    player.setAbsorptionAmount(0);
                    ticksDefense=0;
                    //player.setHealth(player.getHealth()+2);
                    player.getCooldownTracker().setCooldown(this, 60);
                    break;
            }
        }





    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(ItemStack itemstack) {
        return EnumAction.BOW;
    }




    @Override
    public int getMaxItemUseDuration(ItemStack itemstack) {
        return 72000;
    }




//========================================================================

    public float getAttackDamage()
{
    return this.attackDamage;
}

    @Override
    public boolean canHarvestBlock(IBlockState blockIn)
{
    return blockIn.getBlock() == Blocks.WEB;
}

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        setEnergy(stack, getEnergy(stack) - 100);
        return true;
    }
    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Block block = state.getBlock();

        if (block == Blocks.WEB)
        {
            return 15.0F;
        }
        else
        {
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    //=================================================================================================================
    /*



    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        // 播放右键的声音
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        //world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_PORTAL_TRIGGER, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {

            phaseBullet(world, player);
            //player.addPotionEffect(new PotionEffect(PotionRegistryHandler.PHASE_POTION, 20*10, 0));

            //生成雪球实体
//            EntityPhasePearl entityenderpearl = new EntityPhasePearl(world, player);
//            entityenderpearl.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
//            world.spawnEntity(entityenderpearl);
            //player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20*10, 5));
            //player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20*10, 4));

            //生成雪球实体
//            EntitySnowball snowball = new EntitySnowball(world, player);
//            snowball.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
//            world.spawnEntity(snowball);



        }
        return new ActionResult<>(EnumActionResult.SUCCESS, item);
    }

     */


    private void phaseBullet(World world, EntityPlayer player,int ticks) {
        //PlayerMana playerMana = PlayerProperties.getPlayerMana(player);
        //if (playerMana.getMana() >= .5f) {
            world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0f, 1.0f);
            //playerMana.setMana(playerMana.getMana() - .5f);
            int i=ticks;
            RayTraceTools.Beam beam = new RayTraceTools.Beam(world, player, i);
            spawnBullet(player, beam);
            player.setPositionAndUpdate(beam.getEnd().x,beam.getEnd().y,beam.getEnd().z);

        //}
    }

    private void spawnBullet(EntityPlayer player, RayTraceTools.Beam beam) {
        Vec3d start = beam.getStart();
        Vec3d end =beam.getEnd();
        Vec3d lookVec = beam.getLookVec();
        double accelX = lookVec.x * 1.0D;
        double accelY = lookVec.y * 1.0D;
        double accelZ = lookVec.z * 1.0D;

        EntityBullet laser = new EntityBullet(player.getEntityWorld(), player, accelX, accelY, accelZ);
        laser.posX = start.x;
        laser.posY = start.y;
        laser.posZ = start.z;

        player.getEntityWorld().spawnEntity(laser);

    }































    //=================================================================================================================
    //订阅事件
    @SubscribeEvent
    public static void onSwordKB(LivingKnockBackEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote&&evt.getEntity() instanceof EntityPlayer&&defense) {
            EntityPlayer hurtOne = (EntityPlayer) evt.getEntityLiving();
            if(hurtOne.getHeldItemMainhand().getItem().equals(ModItems.PHASE_SWORD)){
                evt.setCanceled(true);
            }



        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onSwordHurt(LivingHurtEvent evt) {
        World world = evt.getEntity().getEntityWorld();
        if (!world.isRemote&&evt.getEntity() instanceof EntityPlayer) {
            EntityPlayer hurtOne = (EntityPlayer) evt.getEntityLiving();
            EntityLivingBase attacker = (EntityLivingBase) evt.getSource().getTrueSource();


            BlockPos pos = new BlockPos(hurtOne.posX,hurtOne.posY-0.2,hurtOne.posZ);
            if(attacker!=null&&hurtOne.getHeldItemMainhand().getItem().equals(ModItems.PHASE_SWORD)){
                BlockPos posAtt = new BlockPos(attacker.posX,attacker.posY-0.2,attacker.posZ);
                //if(hurtOne.getHeldItemMainhand()==){}
                DamageSource damageSource = evt.getSource();
                //damageSource.damageType;
                float damage=evt.getAmount()/2;
                if(defense&&!damageNegations.contains(evt.getSource().damageType))
                {
                    //evt.setAmount(0);
                    evt.setAmount(evt.getAmount()*2/3);
                    if(pos.distanceSq(posAtt)<=4D){
                        //attacker.attackEntityFrom(DamageSource.causePlayerDamage(hurtOne), 10.0F);
                        attacker.attackEntityFrom(DamageSource.causePlayerDamage(hurtOne), damage);
                        //attacker.set
                    }



                }
            }




        }
    }

/*
    @SubscribeEvent
    //监听生物受伤事件”LivingHurtEvent event“
    public void onAttack(LivingHurtEvent event) {
        //判断世界是服务端还是客户端
        World world = event.getEntity().world;
        //服务端 !world.isRemote（不是被遥控的）
        if (!world.isRemote) {
            //设定受伤者H：事件的当事人(怪)
            EntityLivingBase hurtOne = event.getEntityLiving();
            //设定攻击者A：事件伤害的真正来源（玩家）
            EntityLivingBase attacker = (EntityLivingBase) event.getSource().getTrueSource();
            //如果确实存在攻击者A。并且主手拿的这把剑
            if (attacker != null && attacker.getHeldItemMainhand().getItem() == this) {
                //治疗1=半颗心
                attacker.heal(1f);
                //给怪物上buff
                hurtOne.addPotionEffect(new PotionEffect(MobEffects.WITHER,20*6,2));//原版应用buff写法（20tick*6=6s,2(3级)）
                //attacker.addPotionEffect(new PotionEffect(MobEffects.SPEED,20*6,2));
                //attacker.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST,20*6,2));
                //EntityUtil.ApplyBuff(hurtOne, MobEffects.WITHER,2,3f);
                //EntityUtil.ApplyBuff(attacker, MobEffects.SPEED, 2, 10f);
                //EntityUtil.ApplyBuff(attacker, MobEffects.JUMP_BOOST, 2, 10f);
                //暴击事件
                //玩家的概率值.数值越小概率越低
                if (attacker.getRNG().nextFloat() < 0.1f) {
                    //event.setAmount() 新伤害值
                    //伤害翻倍
                    event.setAmount(event.getAmount() * 20);
                    attacker.addPotionEffect(new PotionEffect(MobEffects.SPEED,20*2,5));
                    //EntityUtil.ApplyBuff(attacker, MobEffects.SPEED, 8, 1f);
                    //EntityUtil.ApplyBuff(hurtOne, MobEffects.WEAKNESS, 4, 2f);
                    //EntityUtil.ApplyBuff(hurtOne, MobEffects.SLOWNESS, 4, 5f);
                    //hurtOne.addPotionEffect(new PotionEffect(ModPotions.DAWN,20*6,2));//原版应用buff写法（20tick*6=6s,2(3级)）
                    //伤害+4
                    //event.setAmount(event.getAmount()+4);
                    //
                }
            }
        }
    }

    */
}
