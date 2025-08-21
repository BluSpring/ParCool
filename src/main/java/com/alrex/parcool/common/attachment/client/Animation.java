package com.alrex.parcool.common.attachment.client;

import com.alrex.parcool.api.unstable.animation.AnimationOption;
import com.alrex.parcool.api.unstable.animation.AnimationPart;
import com.alrex.parcool.api.unstable.animation.ParCoolAnimationInfoEvent;
import com.alrex.parcool.client.animation.Animator;
import com.alrex.parcool.client.animation.PassiveCustomAnimation;
import com.alrex.parcool.client.animation.PlayerModelRotator;
import com.alrex.parcool.client.animation.PlayerModelTransformer;
import com.alrex.parcool.common.attachment.ClientAttachments;
import com.alrex.parcool.common.attachment.common.Parkourability;
import com.alrex.parcool.config.ParCoolConfig;
import io.github.fabricators_of_create.porting_lib.client_events.event.client.ViewportEvent;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Animation {

	public static Animation get(Player player) {
		return player.getAttachedOrCreate(ClientAttachments.ANIMATION);
	}

	private Animator animator = null;
    private AnimationOption option = new AnimationOption();
	private final PassiveCustomAnimation passiveAnimation = new PassiveCustomAnimation();

	public void setAnimator(Animator animator) {
		if (!ParCoolConfig.Client.Booleans.EnableAnimation.get()) return;
		if (!ParCoolConfig.Client.getInstance().canAnimate(animator.getClass()).get()) return;
		this.animator = animator;
	}

	public boolean animatePre(Player player, PlayerModelTransformer modelTransformer) {
		Parkourability parkourability = Parkourability.get(player);
        if (animator != null && animator.shouldRemoved(player, parkourability)) animator = null;
        if (animator == null) return false;
        modelTransformer.setOption(option);
        if (option.isAnimationCanceled()) return false;
		return animator.animatePre(player, parkourability, modelTransformer);
	}

	public void animatePost(Player player, PlayerModelTransformer modelTransformer) {
		Parkourability parkourability = Parkourability.get(player);
		if (animator == null) {
			passiveAnimation.animate(player, parkourability, modelTransformer);
			return;
		}
        if (option.isAnimationCanceled()) return;
		animator.animatePost(player, parkourability, modelTransformer);
	}

    public boolean rotatePre(AbstractClientPlayer player, PlayerModelRotator rotator) {
        Parkourability parkourability = Parkourability.get(player);
        if (animator != null && animator.shouldRemoved(player, parkourability)) animator = null;
        if (animator == null) return false;
        if (option.isAnimationCanceled() || option.isCanceled(AnimationPart.ROTATION)) return false;
        return animator.rotatePre(player, parkourability, rotator);
    }

    public void rotatePost(AbstractClientPlayer player, PlayerModelRotator rotator) {
		Parkourability parkourability = Parkourability.get(player);
		if (animator == null) {
			passiveAnimation.rotate(player, parkourability, rotator);
			return;
		}
        if (option.isAnimationCanceled() || option.isCanceled(AnimationPart.ROTATION)) return;
        animator.rotatePost(player, parkourability, rotator);
	}

    public void cameraSetup(ViewportEvent.ComputeCameraAngles event, LocalPlayer player, Parkourability parkourability) {
		if (animator == null) return;
		if (player.isLocalPlayer()
				&& Minecraft.getInstance().options.getCameraType().isFirstPerson()
				&& !ParCoolConfig.Client.Booleans.EnableFPVAnimation.get()
		) return;
        if (animator.shouldRemoved(player, parkourability)) {
            animator = null;
            return;
        }
        if (option.isCanceled(AnimationPart.CAMERA)) return;
		animator.onCameraSetUp(event, player, parkourability);
	}

    public void tick(AbstractClientPlayer player, Parkourability parkourability) {
		passiveAnimation.tick(player, parkourability);
		if (animator != null) {
            animator.tick(player);
		}
        {
            ParCoolAnimationInfoEvent animationEvent = new ParCoolAnimationInfoEvent(player, animator);
            animationEvent.sendEvent();
            option = animationEvent.getOption();
        }
	}

	public void onRenderTick(DeltaTracker tracker, Player player, Parkourability parkourability) {
		if (animator != null) {
			animator.onRenderTick(tracker, player, parkourability);
		}
	}

	public boolean hasAnimator() {
		return animator != null;
	}

	public void removeAnimator() {
		animator = null;
	}
}
