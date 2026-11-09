package com.example.hack.module;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class AutoSprintModule extends Module {
    public AutoSprintModule() { super("AutoSprint", GLFW.GLFW_KEY_V); }

    @Override
    public void onTick(MinecraftClient mc) {
        if (mc.options.forwardKey.isPressed() && !mc.player.isSneaking()) {
            mc.options.sprintKey.setPressed(true);
        }
    }

    @Override
    public void onDisable() {
        MinecraftClient.getInstance().options.sprintKey.setPressed(false);
    }
}
