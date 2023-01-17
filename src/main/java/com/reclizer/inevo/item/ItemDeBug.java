package com.reclizer.inevo.item;

import baubles.common.network.PacketHandler;
import com.reclizer.inevo.entity.bullet.EntityBullet;
import com.reclizer.inevo.entity.bullet.EntitySwordLight;
import com.reclizer.inevo.entity.construct.EntityPhasePortal;
import com.reclizer.inevo.entity.creature.EntityFloatingCannon;
import com.reclizer.inevo.init.ModCreativeTab;
import com.reclizer.inevo.network.Messages;
import com.reclizer.inevo.network.PacketLeftClick;
import com.reclizer.inevo.network.PacketToggleMode;
import com.reclizer.inevo.player.PlayerEnergy;
import com.reclizer.inevo.player.PlayerProperties;
import com.reclizer.inevo.potion.PotionRegistryHandler;
import com.reclizer.inevo.tools.RayTraceTools;
import com.reclizer.inevo.tools.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemDeBug extends ItemBase{
    public ItemDeBug(String name) {
        super(name);
        setMaxStackSize(1);
        setCreativeTab(ModCreativeTab.IE_MISC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if(!world.isRemote){

        }
        return new ActionResult<>(EnumActionResult.FAIL, item);
    }


    @SubscribeEvent
    public void leftClick(PlayerInteractEvent.LeftClickEmpty evt) {
        if (!evt.getItemStack().isEmpty()
                && evt.getItemStack().getItem() == this) {
            Messages.INSTANCE.sendToServer(new PacketLeftClick());

        }
    }

    @SubscribeEvent
    public void attackEntity(AttackEntityEvent evt) {
        if (!evt.getEntityPlayer().world.isRemote) {
            trySpawnBurst(evt.getEntityPlayer());
        }
    }

    public void trySpawnBurst(EntityPlayer player){
        if (!player.getHeldItemMainhand().isEmpty()
                && player.getHeldItemMainhand().getItem() == this
                && player.getCooledAttackStrength(0) == 1) {
            //player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 50, 0));
            World world =player.world;
            shootBullet(world,player);
        }
    }

    private void shootBullet(World world, EntityPlayer player) {
        world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        int i=20;
        RayTraceTools.Beam beam = new RayTraceTools.Beam(world, player, i);
        spawnBullet(player, beam);
    }

    private void spawnBullet(EntityPlayer player, RayTraceTools.Beam beam) {
        Vec3d start = beam.getStart();
        Vec3d end =beam.getEnd();
        Vec3d lookVec = beam.getLookVec();
        double accelX = lookVec.x * 1.0D;
        double accelY = lookVec.y * 1.0D;
        double accelZ = lookVec.z * 1.0D;

        EntitySwordLight laser = new EntitySwordLight(player.getEntityWorld(), player, accelX, accelY, accelZ);
        laser.posX = start.x;
        laser.posY = start.y;
        laser.posZ = start.z;
        laser.posSpawnX = start.x;
        laser.posSpawnY = start.y;
        laser.posSpawnZ = start.z;

        player.getEntityWorld().spawnEntity(laser);

    }
}
