package com.alrex.parcool.api.client.gui;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;

public class ParCoolHUDEvent  {
    public static class RenderEvent extends ParCoolHUDEvent {
        public static final Event<Callback> EVENT = EventFactory.createArrayBacked(Callback.class, callbacks -> event -> {
            for (Callback callback : callbacks) {
                callback.onRender(event);
            }
        });

        public interface Callback {
            void onRender(RenderEvent event);
        }

        private final GuiGraphics graphics;
        private final DeltaTracker partialTick;

        private boolean isCanceled;

        public RenderEvent(GuiGraphics s, DeltaTracker partialTick) {
            this.graphics = s;
            this.partialTick = partialTick;
        }

        public boolean isCanceled() {
            return isCanceled;
        }

        public void setCanceled(boolean cancelled) {
            this.isCanceled = cancelled;
        }

        public GuiGraphics getGuiGraphics() {
            return graphics;
        }

        public DeltaTracker getDeltaTracker() {
            return partialTick;
        }

    }
}
