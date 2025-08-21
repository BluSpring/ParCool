package com.alrex.parcool.common.handlers;

import com.alrex.parcool.server.limitation.Limitations;
import io.github.fabricators_of_create.porting_lib.entity.events.player.PlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class LoginLogoutHandler {
    public static void init() {
        PlayerEvents.PlayerLoggedOutEvent.EVENT.register(LoginLogoutHandler::onLogoutInServer);
    }

    public static void onLogoutInServer(PlayerEvents.PlayerLoggedOutEvent event) {
        Player player = event.getEntity();
        if (player instanceof ServerPlayer) {
            Limitations.unload(player.getUUID());
        }
    }
}
