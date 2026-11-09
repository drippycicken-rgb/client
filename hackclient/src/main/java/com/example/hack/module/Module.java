package com.example.hack.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public abstract class Module {
    public final String name;
    public final int defaultKey; // GLFW key code, -1 for none
    private boolean enabled;

    protected Module(String name, int defaultKey) {
        this.name = name;
        this.defaultKey = defaultKey;
    }

    public boolean isEnabled() { return enabled; }

    public void toggle() {
        enabled = !enabled;
        if (enabled) onEnable(); else onDisable();
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player != null) {
            mc.player.sendMessage(Text.literal("[Hack] " + name + (enabled ? " ON" : " OFF")), true);
        }
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick(MinecraftClient mc) {}
}
