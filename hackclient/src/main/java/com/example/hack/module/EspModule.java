package com.example.hack.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.glfw.GLFW;

/** Entity ESP using the vanilla glow outline (visible through walls). */
public class EspModule extends Module {
    public static EspModule INSTANCE;

    public boolean players = true;
    public boolean hostiles = true;
    public boolean animals = false;
    public boolean items = false;

    public EspModule() {
        super("ESP", GLFW.GLFW_KEY_R);
        INSTANCE = this;
    }

    public boolean shouldGlow(Entity e) {
        if (!isEnabled()) return false;
        if (e == MinecraftClient.getInstance().player) return false;
        if (e instanceof PlayerEntity) return players;
        if (e instanceof HostileEntity) return hostiles;
        if (e instanceof ItemEntity) return items;
        if (e instanceof LivingEntity) return animals;
        return false;
    }
}
