package com.alrex.parcool.mixin.common.fabric;

import com.alrex.parcool.common.handlers.PlayerDamageHandler;
import com.alrex.parcool.common.handlers.PlayerJumpHandler;
import com.alrex.parcool.common.handlers.PlayerVisibilityHandler;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Definition(id = "hasImpulse", field = "Lnet/minecraft/world/entity/LivingEntity;hasImpulse:Z")
    @Expression("this.hasImpulse = ?")
    @Inject(method = "jumpFromGround", at = @At("MIXINEXTRAS:EXPRESSION"))
    private void handleJumpEvent(CallbackInfo ci) {
        PlayerJumpHandler.onJump((LivingEntity) (Object) this);
    }

    @ModifyReturnValue(method = "getVisibilityPercent", at = @At("RETURN"))
    private double checkEntityVisibilityEvent(double original) {
        return PlayerVisibilityHandler.onLivingVisibilityEvent((LivingEntity) (Object) this, original);
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void modifyFallDamageValueEvent(CallbackInfoReturnable<Boolean> cir, @Local(argsOnly = true) double distance, @Local(argsOnly = true) LocalFloatRef damageMultiplier) {
        float newMultiplier = PlayerDamageHandler.onFall((LivingEntity) (Object) this, distance, damageMultiplier.get());

        if (newMultiplier == 0f) {
            cir.setReturnValue(false);
            return;
        }

        damageMultiplier.set(newMultiplier);
    }
}
