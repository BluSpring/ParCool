package com.alrex.parcool.mixin.client.fabric;

import com.alrex.parcool.common.action.ActionProcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "tick", at = @At(value = "CONSTANT", args = "stringValue=gameRenderer"))
    private void fireRenderFramePost(CallbackInfo ci) {
        ActionProcessor.ClientActionProcessor.INSTANCE.onRenderTick();
    }
}
