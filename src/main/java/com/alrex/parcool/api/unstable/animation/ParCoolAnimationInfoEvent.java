package com.alrex.parcool.api.unstable.animation;

import com.alrex.parcool.client.animation.Animator;
import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.player.AbstractClientPlayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ParCoolAnimationInfoEvent extends BaseEvent {
    public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
        for (Callback callback : callbacks) {
            callback.onAnimationInfo(event);
        }
    });

    @Override
    public void sendEvent() {
        EVENT.invoker().onAnimationInfo(this);
    }

    public interface Callback {
        void onAnimationInfo(ParCoolAnimationInfoEvent event);
    }

    private final AbstractClientPlayer player;
    private final Animator animator;
    private final AnimationOption option;

    public ParCoolAnimationInfoEvent(
            AbstractClientPlayer player,
            Animator animator
    ) {
        this.animator = animator;
        this.player = player;
        option = new AnimationOption();
    }

    public AbstractClientPlayer getPlayer() {
        return player;
    }

    public Animator getAnimator() {
        return animator;
    }

    public AnimationOption getOption() {
        return option;
    }
}
