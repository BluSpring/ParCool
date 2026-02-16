package com.alrex.parcool.extern;

import com.alrex.parcool.api.client.gui.ParCoolHUDEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;

public class AdditionalModsEventConsumer {
    public static class Client {
        public static void init() {
            ParCoolHUDEvent.RenderEvent.EVENT.register(Client::onHUDRender);
        }

        @Environment(EnvType.CLIENT)
        public static void onHUDRender(ParCoolHUDEvent.RenderEvent event) {
            var player = Minecraft.getInstance().player;
            if (player == null) return;
            if (AdditionalMods.isUsingExternalStamina()) {
                event.setCanceled(true);
            }
        }
    }
}
