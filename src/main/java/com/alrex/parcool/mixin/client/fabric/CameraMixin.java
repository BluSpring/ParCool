package com.alrex.parcool.mixin.client.fabric;

import com.alrex.parcool.client.fabric.CameraAngles;
import com.alrex.parcool.client.fabric.CameraExtension;
import com.alrex.parcool.common.action.ActionProcessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Camera.class)
public abstract class CameraMixin implements CameraExtension {
    @Unique private float parcool$roll;

    @Override
    public void parcool$setRoll(float roll) {
        this.parcool$roll = roll;
    }

    @WrapOperation(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setRotation(FF)V"))
    private void setupRotationFromEvent(Camera instance, float yRot, float xRot, Operation<Void> original, @Local(argsOnly = true) float partialTick) {
        var cameraAngles = new CameraAngles(instance, partialTick, yRot, xRot, 0);
        ActionProcessor.ClientActionProcessor.INSTANCE.onViewRender(cameraAngles);

        original.call(instance, cameraAngles.getYaw(), cameraAngles.getPitch());
        ((CameraExtension) instance).parcool$setRoll(cameraAngles.getRoll());
    }

    @ModifyArg(method = "setRotation", at = @At(value = "INVOKE", target = "Lorg/joml/Quaternionf;rotationYXZ(FFF)Lorg/joml/Quaternionf;"), index = 2)
    private float useCustomRoll(float angleY) {
        return angleY + (-this.parcool$roll * Mth.DEG_TO_RAD);
    }
}
