package com.example.hack.mixin;

import com.example.hack.module.EspModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    // Forces the vanilla glow outline on entities the ESP module selects.
    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void hackclient$outline(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (EspModule.INSTANCE != null && EspModule.INSTANCE.shouldGlow(entity)) {
            cir.setReturnValue(true);
        }
    }
}
