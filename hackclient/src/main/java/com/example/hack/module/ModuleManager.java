package com.example.hack.module;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleManager {
    public final List<Module> modules = new ArrayList<>();
    private final Map<Module, Boolean> keyDown = new HashMap<>();

    public ModuleManager() {
        modules.add(new EspModule());
        modules.add(new BaseFinderModule());
        modules.add(new AutoSprintModule());
        modules.add(new FullbrightModule());
    }

    public Module get(String name) {
        for (Module m : modules) if (m.name.equalsIgnoreCase(name)) return m;
        return null;
    }

    public void tick(MinecraftClient mc) {
        if (mc.player == null || mc.world == null) return;
        long window = mc.getWindow().getHandle();

        for (Module m : modules) {
            // Keybinds only fire when no screen (chat, inventory) is open.
            if (m.defaultKey != -1 && mc.currentScreen == null) {
                boolean down = GLFW.glfwGetKey(window, m.defaultKey) == GLFW.GLFW_PRESS;
                boolean was = keyDown.getOrDefault(m, false);
                if (down && !was) m.toggle();
                keyDown.put(m, down);
            }
            if (m.isEnabled()) m.onTick(mc);
        }
    }
}
