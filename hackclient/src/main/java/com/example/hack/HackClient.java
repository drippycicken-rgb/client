package com.example.hack;

import com.example.hack.module.EspModule;
import com.example.hack.module.Module;
import com.example.hack.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class HackClient implements ClientModInitializer {
    public static ModuleManager modules;

    @Override
    public void onInitializeClient() {
        modules = new ModuleManager();
        ClientTickEvents.END_CLIENT_TICK.register(modules::tick);

        // Dot commands: .modules | .toggle <name> | .esp <players|hostiles|animals|items>
        ClientSendMessageEvents.ALLOW_CHAT.register(msg -> {
            if (!msg.startsWith(".")) return true;
            handle(msg.substring(1).trim().split("\\s+"));
            return false;
        });
    }

    private void handle(String[] a) {
        MinecraftClient mc = MinecraftClient.getInstance();
        switch (a[0].toLowerCase()) {
            case "modules" -> {
                StringBuilder sb = new StringBuilder("Modules: ");
                for (Module m : modules.modules)
                    sb.append(m.name).append(m.isEnabled() ? "(on) " : "(off) ");
                say(mc, sb.toString());
            }
            case "toggle" -> {
                Module m = a.length > 1 ? modules.get(a[1]) : null;
                if (m == null) say(mc, "Unknown module. Try .modules"); else m.toggle();
            }
            case "esp" -> {
                EspModule e = EspModule.INSTANCE;
                String what = a.length > 1 ? a[1].toLowerCase() : "";
                switch (what) {
                    case "players" -> { e.players = !e.players; say(mc, "ESP players: " + e.players); }
                    case "hostiles" -> { e.hostiles = !e.hostiles; say(mc, "ESP hostiles: " + e.hostiles); }
                    case "animals" -> { e.animals = !e.animals; say(mc, "ESP animals: " + e.animals); }
                    case "items" -> { e.items = !e.items; say(mc, "ESP items: " + e.items); }
                    default -> say(mc, "Usage: .esp <players|hostiles|animals|items>");
                }
            }
            default -> say(mc, "Commands: .modules, .toggle <name>, .esp <type>");
        }
    }

    private void say(MinecraftClient mc, String s) {
        if (mc.player != null) mc.player.sendMessage(Text.literal("[Hack] " + s), false);
    }
}
