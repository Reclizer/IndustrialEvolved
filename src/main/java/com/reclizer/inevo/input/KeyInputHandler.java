package com.reclizer.inevo.input;

import com.reclizer.inevo.net.Messages;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeyInputHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.PhaseSwordMode.isPressed()) {
            Messages.INSTANCE.sendToServer(new PacketToggleMode());
        }
    }
}
