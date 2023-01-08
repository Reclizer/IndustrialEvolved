package com.reclizer.inevo.item.bauble;

import baubles.api.BaubleType;
import com.reclizer.inevo.player.PlayerProperties;
import com.reclizer.inevo.player.PlayerEnergy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAntigravityEquipment extends EnergyBaubleTemplate{
    public ItemAntigravityEquipment(double maxElectricity, String name) {
        super(maxElectricity, name);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.BODY;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if(player instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) player;
            //boolean flying = p.capabilities.isFlying;
            //p.capabilities.allowFlying = true;
//            World world =p.world;
//            if(!world.isRemote){
//                PlayerSummoned playerSummoned = PlayerProperties.getPlayerSummoned(p);
//                playerSummoned.setMaxSummoned(playerSummoned.getMaxSummoned()+4);
//
//            }

//            boolean wasSprting = ItemNBTHelper.getBoolean(stack, TAG_IS_SPRINTING, false);
//            boolean isSprinting = p.isSprinting();
//            if(isSprinting != wasSprting)
//                ItemNBTHelper.setBoolean(stack, TAG_IS_SPRINTING, isSprinting);

//            int time = ItemNBTHelper.getInt(stack, TAG_TIME_LEFT, MAX_FLY_TIME);
//            int newTime = time;
//            Vector3 look = new Vector3(p.getLookVec()).multiply(1, 0, 1).normalize();
//
//            if(flying) {
//                if(time > 0 && !ItemNBTHelper.getBoolean(stack, TAG_INFINITE_FLIGHT, false))
//                    newTime--;
//                final int maxCd = 80;
//                int cooldown = ItemNBTHelper.getInt(stack, TAG_DASH_COOLDOWN, 0);
//                if(!wasSprting && isSprinting && cooldown == 0) {
//                    p.motionX += look.x;
//                    p.motionZ += look.z;
//                    p.world.playSound(null, p.posX, p.posY, p.posZ, ModSounds.dash, SoundCategory.PLAYERS, 1F, 1F);
//                    ItemNBTHelper.setInt(stack, TAG_DASH_COOLDOWN, maxCd);
//                } else if(cooldown > 0) {
//                    if(maxCd - cooldown < 2)
//                        player.moveRelative(0F, 0F, 1F, 5F);
//                    else if(maxCd - cooldown < 10)
//                        player.setSprinting(false);
//                    ItemNBTHelper.setInt(stack, TAG_DASH_COOLDOWN, cooldown - 2);
//                    if(player instanceof EntityPlayerMP)
//                        BotaniaAPI.internalHandler.sendBaubleUpdatePacket((EntityPlayerMP) player, 4);
//                }
//            } else if(!flying) {
//                boolean doGlide = player.isSneaking() && !player.onGround && player.fallDistance >= 2F;
//                if(time < MAX_FLY_TIME && player.ticksExisted % (doGlide ? 6 : 2) == 0)
//                    newTime++;
//
//                if(doGlide) {
//                    player.motionY = Math.max(-0.15F, player.motionY);
//                    float mul = 0.6F;
//                    player.motionX = look.x * mul;
//                    player.motionZ = look.z * mul;
//                    player.fallDistance = 2F;
//                }
//            }
//
//            ItemNBTHelper.setBoolean(stack, TAG_FLYING, flying);
//            if(newTime != time)
//                ItemNBTHelper.setInt(stack, TAG_TIME_LEFT, newTime);
        }
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        if(player instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) player;
            //boolean flying = p.capabilities.isFlying;
            //p.capabilities.allowFlying = true;
            World world =p.world;
            if(!world.isRemote){
                PlayerEnergy playerEnergy = PlayerProperties.getPlayerSummoned(p);
                playerEnergy.setMaxSpaceEnergy(playerEnergy.getMaxSpaceEnergy()+4);

            }
        }
    }
    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
        if(player instanceof EntityPlayer) {
            EntityPlayer p = (EntityPlayer) player;
            //boolean flying = p.capabilities.isFlying;
            //p.capabilities.allowFlying = true;
            World world =p.world;
            if(!world.isRemote){
                PlayerEnergy playerEnergy = PlayerProperties.getPlayerSummoned(p);
                playerEnergy.setMaxSpaceEnergy(playerEnergy.getMaxSpaceEnergy()-4);

            }
        }
    }
}
