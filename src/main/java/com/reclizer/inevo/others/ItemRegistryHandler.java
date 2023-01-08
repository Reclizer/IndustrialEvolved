package com.reclizer.inevo.others;

//@Mod.EventBusSubscriber
public class ItemRegistryHandler {
    /*
    public static final ItemDirtBall DIRT_BALL = new ItemDirtBall();
    //public static final ItemBlock ITEM_COMPRESSED_DIRT = new ItemBlock(BlockRegistryHandler.BLOCK_COMPRESSED_DIRT);
    public static final ItemBlock ITEM_COMPRESSED_DIRT = withRegistryName(new ItemBlock(BlockRegistryHandler.BLOCK_COMPRESSED_DIRT));

    private static ItemBlock withRegistryName(ItemBlock item){
        //在物品注册列表中获取物品注册id
        item.setRegistryName(item.getBlock().getRegistryName());
        return item;
    }
    @SubscribeEvent
    public static void onRegistry(Register<Item> event){
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.register(DIRT_BALL);
        registry.register(withRegistryName(ITEM_COMPRESSED_DIRT));
    }
    //注册压缩泥土块物品
    //ITEM_COMPRESSED_DIRT.setRegistryName(ITEM_COMPRESSED_DIRT.getBlock().getRegistryName());
    //registry.register(ITEM_COMPRESSED_DIRT);

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item){
        ModelResourceLocation modelResourceLocation= new ModelResourceLocation(item.getRegistryName(),"inventory");
        ModelLoader.setCustomModelResourceLocation(item,0,modelResourceLocation);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)//作用于客户端
    public static void onMode1Registry(ModelRegistryEvent event){
        registerModel(DIRT_BALL);
        registerModel(ITEM_COMPRESSED_DIRT);
        /*
        ModelLoader.setCustomModelResourceLocation(DIRT_BALL, 0,
                new ModelResourceLocation(DIRT_BALL.getRegistryName(),"inventory"));

        ModelLoader.setCustomModelResourceLocation(ITEM_COMPRESSED_DIRT, 0,
                new ModelResourceLocation(ITEM_COMPRESSED_DIRT.getRegistryName(),"inventory"));


    }*/



}
