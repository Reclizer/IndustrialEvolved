package com.reclizer.inevo.input;

import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

// wandMode = new KeyBinding("key.wandmode", KeyConflictContext.IN_GAME, Keyboard.KEY_M, "key.categories.mymod");
//        ClientRegistry.registerKeyBinding(wandMode);

@SideOnly(Side.CLIENT)
public class KeyBindings {
    public static KeyBinding PhaseSwordMode;

    public static void init(){
        PhaseSwordMode= new KeyBinding("key.phase_sword_mode", KeyConflictContext.IN_GAME, Keyboard.KEY_M, "key.categories.inevo");
        ClientRegistry.registerKeyBinding(PhaseSwordMode);
    }
}
