package com.alrex.parcool.mixin.common;

import com.alrex.parcool.api.Attributes;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.utilities.fabric.ForcedPoseEntity;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements ForcedPoseEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> p_i48577_1_, Level p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
	}
    @Inject(method = "tryToStartFallFlying", at = @At("HEAD"), cancellable = true)
    public void onTryToStartFallFlying(CallbackInfoReturnable<Boolean> cir) {
        var player = (Player) (Object) this;
        Parkourability parkourability = Parkourability.get(player);
        if (parkourability != null && parkourability.getBehaviorEnforcer().cancelFallFlying()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    public void onJumpFromGround(CallbackInfo ci) {
        Parkourability parkourability = Parkourability.get((Player) (Object) this);
        if (parkourability == null) return;
        if (parkourability.getBehaviorEnforcer().cancelJump()) {
            ci.cancel();
        }
    }

    @Inject(method = "isStayingOnGroundSurface", at = @At("HEAD"), cancellable = true)
    public void onIsStayingOnGroundSurface(CallbackInfoReturnable<Boolean> cir) {
        Parkourability parkourability = Parkourability.get((Player) (Object) this);
        if (parkourability == null) return;
        if (parkourability.getBehaviorEnforcer().cancelDescendFromEdge()) {
            cir.setReturnValue(true);
        }
    }

    // Fabric stuff

    @ModifyReturnValue(method = "createAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder addParCoolAttributes(AttributeSupplier.Builder original) {
        Attributes.registerAll();

        return original
            .add(Attributes.MAX_STAMINA)
            .add(Attributes.STAMINA_RECOVERY);
    }

    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    public void parcool$forcePose(CallbackInfo ci) {
        if (this.parcool$forcedPose != null) {
            this.setPose(this.parcool$forcedPose);
            ci.cancel();
        }
    }

    @Unique
    private Pose parcool$forcedPose;

    @Override
    public Pose parcool$getForcedPose() {
        return this.parcool$forcedPose;
    }

    @Override
    public void parcool$setForcedPose(Pose pose) {
        this.parcool$forcedPose = pose;
    }
}
