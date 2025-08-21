package com.alrex.parcool.api.client.gui;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.core.event.CancellableEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public abstract class ParCoolHUDEvent extends BaseEvent {
    public static class RenderEvent extends ParCoolHUDEvent implements CancellableEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onRender(event);
            }
        });

        public interface Callback {
            void onRender(RenderEvent event);
        }

        @Override
        public void sendEvent() {
            EVENT.invoker().onRender(this);
        }

        private final GuiGraphics graphics;
        private final DeltaTracker partialTick;

        public RenderEvent(GuiGraphics s, DeltaTracker partialTick) {
            this.graphics = s;
            this.partialTick = partialTick;
        }

        public GuiGraphics getGuiGraphics() {
            return graphics;
        }

        public DeltaTracker getDeltaTracker() {
            return partialTick;
        }

    }
}
