package com.reclizer.inevo.util;


import com.reclizer.inevo.entity.creature.EntityAlice;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public final class AllyDesignationSystem {
    private AllyDesignationSystem(){} // No instances!

    public static boolean isValidTarget(Entity attacker, Entity target){

        // Owned entities inherit their owner's allies
        if(attacker instanceof IEntityOwnable && !isValidTarget(((IEntityOwnable)attacker).getOwner(), target)) return false;

        // Always return false if the target is null
        if(target == null) return false;

        // Always return true if the attacker is null - this must be after the target null check!
        if(attacker == null) return true;

        // Tests whether the target is the attacker
        if(target == attacker) return false;

        // Tests whether the target is a creature that was summoned/tamed (or is otherwise owned) by the attacker
        if(target instanceof IEntityOwnable && ((IEntityOwnable)target).getOwner() == attacker){
            return false;
        }


        if(target instanceof EntityAlice){
            return false;
        }

        // I really shouldn't need to do this, but fake players seem to break stuff...
        if(target instanceof FakePlayer) return false;

        return true;
    }
}
