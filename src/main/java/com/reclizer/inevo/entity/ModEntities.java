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
 * 人活着哪有不疯的？硬撑罢了！
 * 人活着哪有不疯的？硬撑罢了！
 * 人活着哪有不疯的？硬撑罢了！
 * 人活着哪有不疯的？硬撑罢了！
 * 妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一
 * 拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈
 * 的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳
 * 把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！他奶
 * 奶的鸡蛋六舅的哈密瓜妹妹的大窝瓜爷爷的大鸡腿婶婶的大葡萄妈妈的黄瓜菜爸爸的大面包三舅姥爷的大李子二婶的桃子三叔的西瓜七舅姥爷的小荔枝二舅
 * 姥爷的火龙果姑姑的猕猴桃祖爷爷的车厘子祖姥爷的大菠萝祖奶奶的大榴莲二爷的小草莓他三婶姥姥的大白菜他哥哥的大面条妹妹的小油菜弟弟的西葫芦姐
 * 姐的大土豆姐夫的大青椒爷爷的大茄子嗯啊，杀杀杀！！好可怕杀杀杀杀杀杀上勾拳！下勾拳！左勾拳！右勾拳！扫堂腿！回旋踢！这是蜘蛛吃耳屎，这是
 * 龙卷风摧毁停车场！这是羚羊蹬，这是山羊跳！乌鸦坐飞机！老鼠走迷宫！大象踢腿！愤怒的章鱼！巨斧砍大树！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯
 * 狂！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯狂！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀！杀
 * ！杀！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯狂！彻底疯狂！
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
