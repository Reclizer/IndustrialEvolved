package com.reclizer.inevo.entity.ai;

import com.reclizer.inevo.entity.construct.EntityEnergyConstruct;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIMasterHurtTarget extends EntityAITarget {
    EntityEnergyConstruct construct;
    EntityLivingBase attacker;
    private int timestamp;

    public EntityAIMasterHurtTarget(EntityEnergyConstruct construct)
    {
        super(construct,false);

        this.construct = construct;
        this.setMutexBits(1);
    }

    public boolean shouldExecute()
    {
        if (!this.construct.hasMaster())
        {
            return false;
        }
        else
        {
            EntityLivingBase entitylivingbase = this.construct.getMaster();

            if (entitylivingbase == null)
            {
                return false;
            }
            else
            {
                this.attacker = entitylivingbase.getLastAttackedEntity();
                int i = entitylivingbase.getLastAttackedEntityTime();

                return i != this.timestamp && this.isSuitableTarget(this.attacker, false)
                        && this.construct.shouldAttackEntity(this.attacker, entitylivingbase);
            }
        }
    }

    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.attacker);
        EntityLivingBase entitylivingbase = this.construct.getMaster();

        if (entitylivingbase != null)
        {
            this.timestamp = entitylivingbase.getLastAttackedEntityTime();
        }

        super.startExecuting();
    }

}
