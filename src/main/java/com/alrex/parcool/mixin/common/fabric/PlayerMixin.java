package com.alrex.parcool.mixin.common.fabric;

import com.alrex.parcool.common.fabric.ForcedPoseEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements ForcedPoseEntity {
    @Unique private Pose parcool$forcedPose;

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public Pose parcool$getForcedPose() {
        return this.parcool$forcedPose;
    }

    @Override
    public void parcool$setForcedPose(Pose pose) {
        this.parcool$forcedPose = pose;
    }

    @Inject(method = "updatePlayerPose", at = @At("HEAD"), cancellable = true)
    private void updateForcedPose(CallbackInfo ci) {
        if (this.parcool$forcedPose != null) {
            this.setPose(this.parcool$forcedPose);
            ci.cancel();
        }
    }


}
