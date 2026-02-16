package com.alrex.parcool.api.unstable.action;

import com.alrex.parcool.common.action.Action;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

public class ParCoolActionEvent {
    private final Player player;
    private final Action action;

    private boolean isCanceled = false;

    public boolean isCanceled() {
        return this.isCanceled;
    }

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }

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

    @Deprecated
    public static class TryToStartEvent extends ParCoolActionEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onTryToStart(event);
            }
        });

        public interface Callback {
            void onTryToStart(TryToStartEvent event);
        }

        public TryToStartEvent sendEvent() {
            EVENT.invoker().onTryToStart(this);
            return this;
        }

        public TryToStartEvent(Player player, Action action) {
            super(player, action);
        }
    }

    @Deprecated
    public static class TryToContinueEvent extends ParCoolActionEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onTryToContinue(event);
            }
        });

        public interface Callback {
            void onTryToContinue(TryToContinueEvent event);
        }

        public TryToContinueEvent sendEvent() {
            EVENT.invoker().onTryToContinue(this);
            return this;
        }

        public TryToContinueEvent(Player player, Action action) {
            super(player, action);
        }
    }

    @Deprecated
    public static class StartEvent extends ParCoolActionEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onStart(event);
            }
        });

        public interface Callback {
            void onStart(StartEvent event);
        }

        public void sendEvent() {
            EVENT.invoker().onStart(this);
        }

        public StartEvent(Player player, Action action) {
            super(player, action);
        }
    }

    @Deprecated
    public static class StopEvent extends ParCoolActionEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onStop(event);
            }
        });

        public interface Callback {
            void onStop(StopEvent event);
        }

        public void sendEvent() {
            EVENT.invoker().onStop(this);
        }

        public StopEvent(Player player, Action action) {
            super(player, action);
        }
    }
    // ======

    public static class TryToStart extends ParCoolActionEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onTryToStart(event);
            }
        });

        public interface Callback {
            void onTryToStart(TryToStart event);
        }

        public TryToStart sendEvent() {
            EVENT.invoker().onTryToStart(this);
            return this;
        }

        public TryToStart(Player player, Action action) {
            super(player, action);
        }
    }

    public static class TryToContinue extends ParCoolActionEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onTryToContinue(event);
            }
        });

        public interface Callback {
            void onTryToContinue(TryToContinue event);
        }

        public TryToContinue sendEvent() {
            EVENT.invoker().onTryToContinue(this);
            return this;
        }

        public TryToContinue(Player player, Action action) {
            super(player, action);
        }
    }

    public static class Start extends ParCoolActionEvent {
        private Start(Player player, Action action) {
            super(player, action);
        }

        public static class Pre extends Start {
            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.onPreStart(event);
                }
            });

            public interface Callback {
                void onPreStart(Pre event);
            }

            public void sendEvent() {
                EVENT.invoker().onPreStart(this);
            }

            public Pre(Player player, Action action) {
                super(player, action);
            }
        }

        public static class Post extends Start {
            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.onPostStart(event);
                }
            });

            public interface Callback {
                void onPostStart(Post event);
            }

            public void sendEvent() {
                EVENT.invoker().onPostStart(this);
            }

            public Post(Player player, Action action) {
                super(player, action);
            }
        }
    }

    public static class Finish extends ParCoolActionEvent {
        private Finish(Player player, Action action) {
            super(player, action);
        }

        public static class Pre extends Finish {
            public static final Event<Pre.Callback> EVENT = EventFactory.createArrayBacked(Pre.Callback.class, callbacks -> event -> {
                for (Pre.Callback callback : callbacks) {
                    callback.onPreFinish(event);
                }
            });

            public void sendEvent() {
                EVENT.invoker().onPreFinish(this);
            }

            public interface Callback {
                void onPreFinish(Pre event);
            }

            public Pre(Player player, Action action) {
                super(player, action);
            }
        }

        public static class Post extends Finish {
            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.onPostFinish(event);
                }
            });

            public interface Callback {
                void onPostFinish(Post event);
            }

            public void sendEvent() {
                EVENT.invoker().onPostFinish(this);
            }

            public Post(Player player, Action action) {
                super(player, action);
            }
        }
    }

    public static class Tick extends ParCoolActionEvent {
        private Tick(Player player, Action action) {
            super(player, action);
        }

        public static class Pre extends Tick {
            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.onPreTick(event);
                }
            });

            public interface Callback {
                void onPreTick(Pre event);
            }

            public void sendEvent() {
                EVENT.invoker().onPreTick(this);
            }

            public Pre(Player player, Action action) {
                super(player, action);
            }
        }

        public static class Post extends Tick {
            public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
                for (Callback callback : callbacks) {
                    callback.onPostTick(event);
                }
            });

            public interface Callback {
                void onPostTick(Post event);
            }

            public void sendEvent() {
                EVENT.invoker().onPostTick(this);
            }

            public Post(Player player, Action action) {
                super(player, action);
            }
        }
    }

}
