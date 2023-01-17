package com.reclizer.inevo.entity;

import com.reclizer.inevo.IndustrialEvolved;
import com.reclizer.inevo.client.render.*;
import com.reclizer.inevo.entity.bullet.EntityBullet;
import com.reclizer.inevo.entity.bullet.EntitySwordLight;
import com.reclizer.inevo.entity.construct.EntityPhasePortal;
import com.reclizer.inevo.entity.creature.EntityAlice;
import com.reclizer.inevo.entity.creature.EntityFloatingCannon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * �˻������в���ģ�Ӳ�Ű��ˣ�
 * �˻������в���ģ�Ӳ�Ű��ˣ�
 * �˻������в���ģ�Ӳ�Ű��ˣ�
 * �˻������в���ģ�Ӳ�Ű��ˣ�
 * ��ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һ
 * ȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���
 * �ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ
 * �ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣���ģ��̲��ˣ�һȭ�ѵ���򱬣�����
 * �̵ļ������˵Ĺ��ܹ����õĴ��ѹ�үү�Ĵ��������Ĵ���������Ļƹϲ˰ְֵĴ����������ү�Ĵ����Ӷ�������������������߾���ү��С��֦����
 * ��ү�Ļ������ùõ�⨺�����үү�ĳ���������ү�Ĵ��������̵Ĵ�������ү��С��ݮ���������ѵĴ�ײ������Ĵ��������õ�С�Ͳ˵ܵܵ�����«��
 * ��Ĵ��������Ĵ��ཷүү�Ĵ������Ű���ɱɱɱ�����ÿ���ɱɱɱɱɱɱ�Ϲ�ȭ���¹�ȭ����ȭ���ҹ�ȭ��ɨ���ȣ������ߣ�����֩��Զ�ʺ������
 * �����ݻ�ͣ��������������ţ�����ɽ��������ѻ���ɻ����������Թ����������ȣ���ŭ�����㣡�޸������������׷�񣡳��׷�񣡳��׷�񣡳��׷�
 * �񣡳��׷�񣡳��׷�񣡳��׷�񣡳��׷�񣡳��׷��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ��ɱ
 * ��ɱ�����׷�񣡳��׷�񣡳��׷�񣡳��׷�񣡳��׷�񣡳��׷�񣡳��׷�񣡳��׷�񣡳��׷�񣡳��׷��
 */

public class ModEntities {

    private static int ENTITY_NEXT_ID = 1;
    public static void init() {

        EntityRegistry.registerModEntity(new ResourceLocation(IndustrialEvolved.MODID, "inevo_alice"), EntityAlice.class, "inevo_alice", ENTITY_NEXT_ID++,
                IndustrialEvolved.instance, 64, 3, true, 0x222222, 0x555555);
        EntityRegistry.registerModEntity(new ResourceLocation(IndustrialEvolved.MODID, "inevo_bullet"), EntityBullet.class, "inevo_bullet", ENTITY_NEXT_ID++,
                IndustrialEvolved.instance, 64, 1, false);
        EntityRegistry.registerModEntity(new ResourceLocation(IndustrialEvolved.MODID, "inevo_float"), EntityFloatingCannon.class, "inevo_float", ENTITY_NEXT_ID++,
                IndustrialEvolved.instance, 64, 3, true, 0x222222, 0x555555);
        EntityRegistry.registerModEntity(new ResourceLocation(IndustrialEvolved.MODID, "inevo_phase_portal"), EntityPhasePortal.class, "inevo_phase_portal", ENTITY_NEXT_ID++,
                IndustrialEvolved.instance, 64, 3, true, 0x222222, 0x555555);
        EntityRegistry.registerModEntity(new ResourceLocation(IndustrialEvolved.MODID, "inevo_sword_light"), EntitySwordLight.class, "inevo_sword_light", ENTITY_NEXT_ID++,
                IndustrialEvolved.instance, 64, 1, false);


    }

@SideOnly(Side.CLIENT)
    public static void initModels(){
        RenderingRegistry.registerEntityRenderingHandler(EntityAlice.class, RenderAlice::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, RenderBullet.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntityFloatingCannon.class, RenderFloatingCannon::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPhasePortal.class, RenderPhasePortal.FACTORY);
        RenderingRegistry.registerEntityRenderingHandler(EntitySwordLight.class, RenderSwordLight.FACTORY);
    }
}
