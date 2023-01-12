package com.reclizer.inevo.item;

import com.reclizer.inevo.entity.construct.EntityPhasePortal;
import com.reclizer.inevo.entity.creature.EntityFloatingCannon;
import com.reclizer.inevo.init.ModCreativeTab;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.player.PlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemDeBug extends ItemBase{
    public ItemDeBug(String name) {
        super(name);
        setMaxStackSize(1);
        setCreativeTab(ModCreativeTab.IE_MISC);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

                if(!world.isRemote){
                EntityPhasePortal floatingCannon = new EntityPhasePortal(world);
                floatingCannon.setPosition(player.posX, player.posY+3, player.posZ);
                floatingCannon.setMaster(player);
                world.spawnEntity(floatingCannon);
        }
        return new ActionResult<>(EnumActionResult.FAIL, item);
    }
}
