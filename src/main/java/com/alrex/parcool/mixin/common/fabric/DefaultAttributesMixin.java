package com.alrex.parcool.mixin.common.fabric;

import com.alrex.parcool.common.handlers.AddAttributesHandler;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DefaultAttributes.class)
public abstract class DefaultAttributesMixin {
    @ModifyReturnValue(method = "getSupplier", at = @At("RETURN"))
    private static AttributeSupplier addCustomParCoolAttributes(AttributeSupplier original, @Local(argsOnly = true) EntityType<? extends LivingEntity> entityType) {
        if (entityType == EntityType.PLAYER) {
            return AddAttributesHandler.getPlayerAttributeSupplier(original);
        }

        return original;
    }
}
