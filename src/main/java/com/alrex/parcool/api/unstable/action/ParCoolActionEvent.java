package com.alrex.parcool.api.unstable.action;

import com.alrex.parcool.common.action.Action;
import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.core.event.CancellableEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public abstract class ParCoolActionEvent extends BaseEvent {
    private final Player player;
    private final Action action;

    public Player getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }

    public ParCoolActionEvent(Player player, Action action) {
        this.player = player;
        this.action = action;
    }

    public static class TryToStartEvent extends ParCoolActionEvent implements CancellableEvent {
        public TryToStartEvent(Player player, Action action) {
            super(player, action);
        }

        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onTryToStart(event);
            }
        });

        public interface Callback {
            void onTryToStart(TryToStartEvent event);
        }

        @Override
        public void sendEvent() {
            EVENT.invoker().onTryToStart(this);
        }
    }

    public static class TryToContinueEvent extends ParCoolActionEvent implements CancellableEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onTryToContinue(event);
            }
        });

        public interface Callback {
            void onTryToContinue(TryToContinueEvent event);
        }

        @Override
        public void sendEvent() {
            EVENT.invoker().onTryToContinue(this);
        }

        public TryToContinueEvent(Player player, Action action) {
            super(player, action);
        }
    }

    public static class StartEvent extends ParCoolActionEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onStart(event);
            }
        });

        public interface Callback {
            void onStart(StartEvent event);
        }

        @Override
        public void sendEvent() {
            EVENT.invoker().onStart(this);
        }

        public StartEvent(Player player, Action action) {
            super(player, action);
        }
    }

    public static class StopEvent extends ParCoolActionEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onStop(event);
            }
        });

        public interface Callback {
            void onStop(StopEvent event);
        }

        @Override
        public void sendEvent() {
            EVENT.invoker().onStop(this);
        }

        public StopEvent(Player player, Action action) {
            super(player, action);
        }
    }
}
